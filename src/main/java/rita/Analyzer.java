package rita;

import java.util.HashMap;
import java.util.Map;

public class Analyzer {

	public static final String SLASH = "/";
	public static final String DELIM = "-";

	public Map<String, String> analyze(String text) {
		return this.analyze(text, null);
	}

	public Map<String, String> analyze(String text, Map<String, Object> opts) {
		String[] words = Tokenizer.tokenize(text);
		String[] tags = Tagger.tag(text, opts); // don't fail if no lexicon

		String phones = "", stresses = "", syllables = "";
		for (int i = 0; i < words.length; i++) {
			String[] data = this.analyzeWord(words[i], opts);
			//{ phones, stresses, syllables }
			phones += data[0];
			stresses += data[1];
			syllables += data[2];
		}

		Map<String, String> features = new HashMap<>();
		features.put("phones", phones.trim());
		features.put("stresses", stresses.trim());
		features.put("syllables", syllables.trim());
		features.put("pos", String.join(" ", tags));
		features.put("tokens", String.join(" ", words));

		return features;
	}

	public String[] analyzeWord(String word) {
		return this.analyzeWord(word, null);
	}

	public String[] analyzeWord(String word, Map<String, Object> opts) {

		boolean useRaw = false; // Util.boolOpt("rawPhones", opts);
		String rawPhones = RiTa.lexicon().rawPhones(word, true);

		// TODO: add cache

		// if its a plural, check the singular
		if (rawPhones == null && word.endsWith("s")) {

			String sing = RiTa.singularize(word);
			rawPhones = RiTa.lexicon().rawPhones(sing, true);
			if (rawPhones != null) rawPhones += "-z"; // add 's' phone
		}

		// TODO: what about verb forms here??

		// now use the lts engine if needed
		if (rawPhones == null) {

			String[] ltsPhones = RiTa.lts.computePhones(word);
			if (ltsPhones != null && ltsPhones.length > 0) {

				if (!RiTa.SILENT && !RiTa.SILENCE_LTS && word.matches("[a-zA-Z]+")) {
					System.out.println("[RiTa] Used LTS-rules for '" + word + "'");
				}
				rawPhones = Util.syllabifyPhones(ltsPhones);
			}
			else {
				// phones = word;
				rawPhones = word;
				useRaw = true;
			}
		}

		String stresses = "";
		String sp = rawPhones.replaceAll("[0-2]", "").replaceAll(" ", DELIM) + " ";
		String phones = sp.equals("dh ") ? "dh-ah " : sp; // special case
		String ss = rawPhones.replaceAll(" +", SLASH).replaceAll("1", "") + " ";
		String syllables = ss.equals("dh ") ? "dh-ah " : ss;

		// compute the stresses
		if (!useRaw) {
			String[] stressyls = rawPhones.split(" ");
			for (int j = 0; j < stressyls.length; j++) {
				if (/*stressyls[j] == null ||*/stressyls[j].length() == 0) continue;
				stresses += (stressyls[j].indexOf(RiTa.STRESS) > -1)
						? RiTa.STRESS
						: RiTa.NOSTRESS;
				if (j < stressyls.length - 1) stresses += SLASH;
			}
		}
		else {
			stresses += word;
		}

		if (!stresses.endsWith(" ")) stresses += " "; // why?

		return new String[] { phones, stresses, syllables };
	}

	public static void main(String[] args) {
		System.out.println(Tagger.tag("dog")[0]);
	}

}
