package rita;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Tagger {
	public static final String[] ADJ = { "jj", "jjr", "jjs" };
	public static final String[] ADV = { "rb", "rbr", "rbs", "rp" };
	public static final String[] NOUNS = { "nn", "nns", "nnp", "nnps" };
	public static final String[] VERBS = { "vb", "vbd", "vbg", "vbn", "vbp", "vbz" };

	static String[] MODALS = Util.MODALS;

	static Lexicon lexicon = RiTa._lexicon();

	private Tagger() {} // static class
	
	public static boolean isAdjective(String word) {
		return checkType(word, ADJ);
	}

	public static boolean isAdverb(String word) {

		return checkType(word, ADV);
	}

	public static boolean isNoun(String word) {
		boolean result = checkType(word, NOUNS);
		if (!result) {
			String singular = RiTa.singularize(word);
			if (singular != word) {
				result = checkType(singular, NOUNS);
			}
		}
		return result;
	}

	public static boolean isVerb(String word) {
		return checkType(word, VERBS);
	}

	public static boolean isVerbTag(String tag) {
		return Arrays.asList(VERBS).contains(tag);
	}

	public static boolean isNounTag(String tag) {
		return Arrays.asList(NOUNS).contains(tag);
	}

	public static boolean isAdverbTag(String tag) {
		return Arrays.asList(ADV).contains(tag);
	}

	public static boolean isAdjTag(String tag) {
		return Arrays.asList(ADJ).contains(tag);
	}

	public static String tagInline(String words, boolean useSimpleTags) {
		/*
		 * if (words == null || words.length() == 0 ) return "";
		 * 
		 * if (words.length() != tags.length) throw new
		 * RiTaException("Tagger: invalid state");
		 * 
		 * delimiter = delimiter || '/';
		 * 
		 * String sb = ""; for (int i = 0; i < words.length(); i++) {
		 * 
		 * sb += words[i]; if (!RiTa.isPunctuation(words[i])) { sb += delimiter +
		 * tags[i]; } sb += ' '; }
		 * 
		 * return sb.trim();
		 * 
		 */

		return null;
	}

	private static String[] derivePosData(String word) {
		/*
		 * Try for a verb or noun inflection VBD Verb, past tense VBG Verb, gerund or
		 * present participle VBN Verb, past participle VBP Verb, non-3rd person
		 * singular present VBZ Verb, 3rd person singular present NNS Noun, plural
		 */
		List<String> pos;

		if (word.endsWith("ies")) { // 3rd-person sing. present (satisfies, falsifies)
			String check = word.substring(0, word.length() - 3) + "y";
			pos = Arrays.asList(lexicon._posArr(check));
			if (pos.contains("vb")) {
				String[] result = { "vbz" };
				return result;
			}

		} else if (word.endsWith("s")) { // plural noun or vbz

			List<String> result = new ArrayList<String>();

			// remove suffix (s) and test (eg 'hates', 'cakes')
			_checkPluralNounOrVerb(word.substring(0, word.length() - 1), result);

			if (word.endsWith("es")) {

				// remove suffix (es) and test (eg 'repossesses')
				_checkPluralNounOrVerb(word.substring(0, word.length() - 2), result);

				// singularize and test (eg 'thieves')
				_checkPluralNounOrVerb(RiTa.singularize(word), result);
			}

			if (result.size() > 0) return result.toArray(new String[0]);
		} else if (word.endsWith("ed")) { // simple past or past participle
			pos = Arrays.asList(lexicon._posArr(word.substring(0, word.length() - 1)));
			if (pos.size() < 1) pos = Arrays.asList(lexicon._posArr(word.substring(0, word.length() - 2)));
			if (Arrays.asList(pos).contains("vb")) { // What is the warning here ?
				String[] result = { "vbd", "vbn" };
				return result; // hate-> hated || row->rowed
			}
		} else if (word.endsWith("ing")) {
			String stem = word.substring(0, word.length() - 3);
			if (stem.length() > 0) {
				pos = Arrays.asList(lexicon._posArr(stem));
				if (pos.contains("vb")) {
					String[] result = { "vbg" };
					return result; // assenting
				} else {
					pos = Arrays.asList(lexicon._posArr(stem + 'e')); // hate
					if (pos.contains("vb")) {
						String[] result = { "vbg" };
						return result; // hating
					}
				}
			}
		}

		String[] result = new String[1];

		// Check if this could be a plural noun form
		if (_isLikelyPlural(word)) {
			result[0] = "nns";
			return result;
		}

		if (word == "the" || word == "a") {
			result[0] = "dt";
			return result;
		}

		// Give up with a best guess
		if (word.endsWith("ly")) {
			result[0] = "rb";
		} else {
			if (word.endsWith("s")) {
				result[0] = "nns";
			} else {
				result[0] = "nn";
			}
		}

		return result;
	}

	private static String[] posOptions(String word) {
		String[] posdata = lexicon._posArr(word); // fail if no lexicon
		System.out.println("data : " + Arrays.toString(posdata));
		if (posdata.length == 0) posdata = derivePosData(word);
		return posdata;

	}

	public static String[] tag(String words, boolean useSimpleTags) {
		if (words == null || words.length() < 1) return new String[0];
		return tag(Tokenizer.tokenize(words), useSimpleTags);
	}

	public static String[] tag(String[] wordsArr, boolean useSimpleTags) {
		if (wordsArr == null || wordsArr.length == 0) return new String[0];

		String[][] choices2d = new String[wordsArr.length][];
		String[] result = new String[wordsArr.length];

		for (int i = 0; i < wordsArr.length; i++) {
			String word = wordsArr[i];

			if (word.length() < 1) {
				result[i] = "";
				continue;
			}

			if (word.length() == 1) {

				result[i] = _handleSingleLetter(word);
				continue;
			} else {
				choices2d[i] = posOptions(word); // all options
				System.out.println(word + " " + choices2d[i].length);
				result[i] = choices2d[i][0]; // first option
			}
		}

		// Adjust pos according to transformation rules
		String[] tags = _applyContext(wordsArr, result, choices2d);
		System.out.println("choices2d : " + choices2d);
		System.out.println(tags.length);
		if (useSimpleTags) {
			for (int i = 0; i < tags.length; i++) {
				if (Arrays.asList(NOUNS).contains(tags[i]))
					tags[i] = "n";
				else if (Arrays.asList(VERBS).contains(tags[i]))
					tags[i] = "v";
				else if (Arrays.asList(ADJ).contains(tags[i]))
					tags[i] = "a";
				else if (Arrays.asList(ADV).contains(tags[i]))
					tags[i] = "r";
				else
					tags[i] = "-"; // default: other
			}
			System.out.println("simple: " + Arrays.toString(tags));
		}
		System.out.println("Tags : " + Arrays.toString(tags));
		return ((tags == null) ? new String[] { } : tags);
	}

	private static String[] _applyContext(String[] words, String[] result, String[][] choices2d) {

		// console.log("ac(" + words + "," + result + "," + choices2d + ")");

		// Apply transformations
		for (int i = 0, l = words.length; i < l; i++) {

			String word = words[i];
			String tag = result[i];
			String[] resultSA = result;
			// transform 1a: DT, {VBD | VBP | VB} --> DT, NN
			if (i > 0 && (resultSA[i - 1] == "dt")) {

				if (tag.startsWith("vb")) {
					tag = "nn";

					// transform 7: if a word has been categorized as a
					// common noun and it ends with "s", then set its type to plural common noun
					// (NNS)

					if (word.matches("^.*[^s]s$")) {
						if (!Arrays.asList(MODALS).contains(word)) {
							tag = "nns";
						}
					}

					_logCustom("1a", word, tag);
				}

				// transform 1b: DT, {RB | RBR | RBS} --> DT, {JJ |
				// JJR | JJS}
				else if (tag.startsWith("rb")) {

					tag = (tag.length() > 2) ? "jj" + tag.charAt(2) : "jj";
					_logCustom("1b", word, tag);
				}
			}

			// transform 2: convert a noun to a number (cd) if it is
			// all digits and/or a decimal "."
			if (tag.startsWith("n") && choices2d[i].length != 0) {
				if (isNumeric(word)) {
					tag = "cd";
				} // mods: dch (add choice check above) <---- ? >
			}

			// transform 3: convert a noun to a past participle if
			// word ends with "ed" and (following any nn or prp?)
			if (i > 0 && tag.startsWith("n") && word.endsWith("ed") && !word.endsWith("eed")
					&& resultSA[i - 1].matches("^(nn|prp)$")) {
				tag = "vbn";
			}

			// transform 4: convert any type to adverb if it ends in "ly";
			if (word.endsWith("ly")) {
				tag = "rb";
			}

			// transform 5: convert a common noun (NN or NNS) to a
			// adjective if it ends with "al", special-case for mammal
			if (tag.startsWith("nn") && word.endsWith("al") && !word.equals("mammal")) {
				tag = "jj";
			}

			// transform 6: convert a noun to a verb if the
			// preceeding word is modal
			if (i > 0 && tag.startsWith("nn") && resultSA[i - 1].startsWith("md")) {
				tag = "vb";
			}

			// transform 8: convert a common noun to a present
			// participle verb (i.e., a gerund)
			if (tag.startsWith("nn") && word.endsWith("ing")) {

				// DH: fixed here -- add check on choices2d for any verb: eg. // "morning"
				if (hasTag(choices2d[i], "vb")) {
					tag = "vbg";
					_logCustom("8", word, tag);
				}
			}

			// transform 9(dch): convert plural nouns (which are also 3sg-verbs) to
			// 3sg-verbs when following a singular noun (the dog dances, Dave dances, he
			// dances)
			if (i > 0 && tag.equals("nns") && hasTag(choices2d[i], "vbz") && resultSA[i - 1].matches("^(nn|prp|nnp)$")) {
				tag = "vbz";
				_logCustom("9", word, tag);
			}

			// transform 10(dch): convert common nouns to proper
			// nouns when they start w' a capital

			if (tag.startsWith("nn") && (word.charAt(0) == Character.toUpperCase(word.charAt(0)))) {
				// if it is not at the start of a sentence or it is the only word
				// or when it is at the start of a sentence but can't be found in the dictionary
				if (i != 0 || words.length == 1 || (i == 0 && !_lexHas("nn", RiTa.singularize(word).toLowerCase()))) {
					tag = tag.endsWith("s") ? "nnps" : "nnp";
					_logCustom("10", word, tag);
				}
			}

			// transform 11(dch): convert plural nouns (which are
			// also 3sg-verbs) to 3sg-verbs when followed by adverb
			if (i < result.length - 1 && tag.equals("nns") && resultSA[i + 1].startsWith("rb") &&
					hasTag(choices2d[i], "vbz")) {
				tag = "vbz";
				_logCustom("11", word, tag);
			}

			// transform 12(dch): convert plural nouns which have an entry for their base
			// form to vbz
			if (tag == "nns") {

				// is preceded by one of the following
				String[] options = new String[] { "nn", "prp", "cc", "nnp" };
				List<String> list = Arrays.asList(options);
				if (i > 0 && list.contains(resultSA[i - 1])) {
					// if word is ends with s or es and is "nns" and has a vb
					if (_lexHas("vb", RiTa.singularize(word))) {
						tag = "vbz";
						_logCustom("12", word, tag);
					}
				} // if only word and not in lexicon
				else if (words.length == 1 && choices2d[i].length == 0) {
					// if the stem of a single word could be both nn and vb, return nns
					// only return vbz when the stem is vb but not nn
					if (!_lexHas("nn", RiTa.singularize(word)) && _lexHas("vb", RiTa.singularize(word))) {
						tag = "vbz";
						_logCustom("12", word, tag);
					}

				}
			}

			// transform 13(cqx): convert a vb/ potential vb to vbp when following nns
			// (Elephants dance, they dance)
			if (tag.equals("vb") || (tag.equals("nn") && hasTag(choices2d[i], "vb"))) {
				if (i > 0 && resultSA[i - 1].matches("^(nns|nnps|prp)$")) {
					tag = "vbp";
					_logCustom("13", word, tag);
				}
			}

			resultSA[i] = tag;
		}

		return result;

	}

	private static void _logCustom(String i, String frm, String to) {
		System.out.println("\n  Custom(" + i + ") tagged '" + frm + "' -> '" + to + "'\n\n");
	}

	private static boolean hasTag(String[] choices, String tag) {
		// if (!Array.isArray(choices)) return false;
		String choiceStr = String.join("", choices);
		return (choiceStr.indexOf(tag) > -1);
	}

	private static boolean _lexHas(String pos, String word) {
		String[] tags = lexicon._posArr(word);
		if (tags.length < 1) return false;
		for (int j = 0; j < tags.length; j++) {
			if (pos.equals(tags[j])) return true;
			if (pos.equals("n") && isNounTag(tags[j]) ||
					pos.equals("v") && isVerbTag(tags[j]) ||
					pos.equals("r") && isAdverbTag(tags[j]) ||
					pos.equals("a") && isAdjTag(tags[j])) {
				return true;
			}
		}
		return false;
	}

	private static String _handleSingleLetter(String c) {
		String result = c;

		if (c.equals("a") || c.equals("A"))
			result = "dt";
		else if (c.equals("I"))
			result = "prp";
		else if (isNumeric(c))
			result = "cd";

		return result;
	}

	private static void _checkPluralNounOrVerb(String stem, List<String> result) {
		List<String> pos = Arrays.asList(lexicon._posArr(stem));
		if (pos.size() > 0) {
			if (pos.contains("nn")) result.add("nns"); // ?? any case
			if (pos.contains("vb")) result.add("vbz");
		}
		return;
	}

	private static boolean _isLikelyPlural(String word) {
		// Check for plural noun with singularizer and stemmer
		return Stemmer._checkPluralNoLex(word) || _lexHas("n", RiTa.singularize(word));
	}

	private static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	private static boolean checkType(String word, String[] tagArray) {

		if (word == null || word.length() == 0) return false;

		if (word.indexOf(" ") < 0) {

			List<String> psa = Arrays.asList(lexicon._posArr(word));

			if (psa.size() == 0) {
				if (RiTa.LEX_WARN) { // TODO what is size() <= 1000 ??
					Logger logger = Logger.getLogger(Tagger.class.getName());
					logger.warning(Boolean.toString(RiTa.LEX_WARN));
					RiTa.LEX_WARN = false; // only once
				}
				List<String> posT = Arrays.asList(tag(word, false));
				if (posT.size() > 0) psa.addAll(posT);
			}
			List<String> finalType = new ArrayList<String>();
			for (String item : psa) {
				if (Arrays.asList(tagArray).contains(item)) {
					finalType.add(item);
				}
			}

			// psa.forEach(p -> Arrays.asList(tagArray).stream().filter(p1 -> p.indexOf(p1)
			// > 0).forEach(list::add));
			return finalType.size() > 0;
		}

		throw new RiTaException("checkType() expects single word, found: '" + word + "'");
	}
}
