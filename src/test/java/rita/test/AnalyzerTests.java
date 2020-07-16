/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package rita.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import rita.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class AnalyzerTests {

	@Test
	public void testSingularize() {

		String[] tests = {
				"media", "medium",
				"millennia", "millennium",
				"consortia", "consortium",
				"concerti", "concerto",
				"septa", "septum",
				"termini", "terminus",
				"larvae", "larva",
				"vertebrae", "vertebra",
				"memorabilia", "memorabilium",
				"hooves", "hoof",
				"thieves", "thief",
				"rabbis", "rabbi",
				"flu", "flu",
				"safaris", "safari",
				"sheaves", "sheaf",
				"uses", "use",
				"pinches", "pinch",
				"catharses", "catharsis",
				"hankies", "hanky"
		};
		for (int i = 0; i < tests.length; i += 2) {
			// System.out.println("p: " + RiTa.singularize(tests[i]) + " s: " + tests[i +
			// 1]);
			assertEquals(RiTa.singularize(tests[i]), tests[i + 1]);
		}

		assertEquals(RiTa.singularize("pleae"), "pleae"); // special-cased in code
		assertEquals(RiTa.singularize("whizzes"), "whiz");
		assertEquals(RiTa.singularize("selves"), "self");
		assertEquals(RiTa.singularize("bookshelves"), "bookshelf");
		assertEquals(RiTa.singularize("wheezes"), "wheeze");
		assertEquals(RiTa.singularize("diagnoses"), "diagnosis");

		assertEquals("minutia", RiTa.singularize("minutia"));
		assertEquals("blonde", RiTa.singularize("blondes"));
		assertEquals("eye", RiTa.singularize("eyes"));
		assertEquals(RiTa.singularize("swine"), "swine");
		assertEquals(RiTa.singularize("cognoscenti"), "cognoscenti");
		assertEquals(RiTa.singularize("bonsai"), "bonsai");
		assertEquals(RiTa.singularize("taxis"), "taxi");
		assertEquals(RiTa.singularize("chiefs"), "chief");
		assertEquals(RiTa.singularize("monarchs"), "monarch");
		assertEquals(RiTa.singularize("lochs"), "loch");
		assertEquals(RiTa.singularize("stomachs"), "stomach");

		assertEquals(RiTa.singularize("Chinese"), "Chinese");

		assertEquals(RiTa.singularize("people"), "person");
		assertEquals(RiTa.singularize("monies"), "money");
		assertEquals(RiTa.singularize("vertebrae"), "vertebra");
		assertEquals(RiTa.singularize("humans"), "human");
		assertEquals(RiTa.singularize("germans"), "german");
		assertEquals(RiTa.singularize("romans"), "roman");

		assertEquals(RiTa.singularize("memoranda"), "memorandum");
		assertEquals(RiTa.singularize("data"), "datum");
		assertEquals(RiTa.singularize("appendices"), "appendix");
		assertEquals(RiTa.singularize("theses"), "thesis");
		assertEquals(RiTa.singularize("alumni"), "alumnus");

		assertEquals(RiTa.singularize("solos"), "solo");
		assertEquals(RiTa.singularize("music"), "music");

		assertEquals(RiTa.singularize("oxen"), "ox");
		assertEquals(RiTa.singularize("solos"), "solo");
		assertEquals(RiTa.singularize("music"), "music");
		assertEquals(RiTa.singularize("money"), "money");
		assertEquals(RiTa.singularize("beef"), "beef");

		assertEquals(RiTa.singularize("tobacco"), "tobacco");
		assertEquals(RiTa.singularize("cargo"), "cargo");
		assertEquals(RiTa.singularize("golf"), "golf");
		assertEquals(RiTa.singularize("grief"), "grief");

		assertEquals(RiTa.singularize("cakes"), "cake");

		assertEquals("dog", RiTa.singularize("dogs"));
		assertEquals("foot", RiTa.singularize("feet"));
		assertEquals("tooth", RiTa.singularize("teeth"));
		assertEquals("kiss", RiTa.singularize("kisses"));
		assertEquals("child", RiTa.singularize("children"));
		assertEquals("randomword", RiTa.singularize("randomwords"));
		assertEquals("deer", RiTa.singularize("deer"));
		assertEquals("sheep", RiTa.singularize("sheep"));
		assertEquals("shrimp", RiTa.singularize("shrimps"));

		assertEquals(RiTa.singularize("tomatoes"), "tomato");
		assertEquals(RiTa.singularize("photos"), "photo");

		assertEquals(RiTa.singularize("toes"), "toe");

		assertEquals(RiTa.singularize("series"), "series");
		assertEquals(RiTa.singularize("oxen"), "ox");
		assertEquals(RiTa.singularize("men"), "man");
		assertEquals(RiTa.singularize("mice"), "mouse");
		assertEquals(RiTa.singularize("lice"), "louse");
		assertEquals(RiTa.singularize("children"), "child");

		assertEquals(RiTa.singularize("gases"), "gas");
		assertEquals(RiTa.singularize("buses"), "bus");
		assertEquals(RiTa.singularize("happiness"), "happiness");

		assertEquals(RiTa.singularize("crises"), "crisis");
		assertEquals(RiTa.singularize("theses"), "thesis");
		assertEquals(RiTa.singularize("apotheses"), "apothesis");
		assertEquals(RiTa.singularize("stimuli"), "stimulus");
		assertEquals(RiTa.singularize("alumni"), "alumnus");
		assertEquals(RiTa.singularize("corpora"), "corpus");

		assertEquals("man", RiTa.singularize("men"));
		assertEquals("woman", RiTa.singularize("women"));
		assertEquals("congressman", RiTa.singularize("congressmen"));
		assertEquals("alderman", RiTa.singularize("aldermen"));
		assertEquals("freshman", RiTa.singularize("freshmen"));
		assertEquals("fireman", RiTa.singularize("firemen"));
		assertEquals("grandchild", RiTa.singularize("grandchildren"));
		assertEquals("menu", RiTa.singularize("menus"));
		assertEquals("guru", RiTa.singularize("gurus"));

		assertEquals("", RiTa.singularize(""));
		assertEquals("hardness", RiTa.singularize("hardness"));
		assertEquals("shortness", RiTa.singularize("shortness"));
		assertEquals("dreariness", RiTa.singularize("dreariness"));
		assertEquals("unwillingness", RiTa.singularize("unwillingness"));
		assertEquals("deer", RiTa.singularize("deer"));
		assertEquals("fish", RiTa.singularize("fish"));
		assertEquals("ooze", RiTa.singularize("ooze"));

		assertEquals("ooze", RiTa.singularize("ooze"));
		assertEquals("enterprise", RiTa.singularize("enterprises"));
		assertEquals("treatise", RiTa.singularize("treatises"));
		assertEquals("house", RiTa.singularize("houses"));
		assertEquals("chemise", RiTa.singularize("chemises"));

		assertEquals("aquatics", RiTa.singularize("aquatics"));
		assertEquals("mechanics", RiTa.singularize("mechanics"));
		assertEquals("quarter", RiTa.singularize("quarters"));

	}

	@Test
	public void testPluralize() {
		String[] tests = {
				"media", "medium",
				"millennia", "millennium",
				"consortia", "consortium",
				"concerti", "concerto",
				"septa", "septum",
				"termini", "terminus",
				"larvae", "larva",
				"vertebrae", "vertebra",
				"memorabilia", "memorabilium",
				"sheafs", "sheaf",
				"spoofs", "spoof",
				"proofs", "proof",
				"roofs", "roof",
				"disbeliefs", "disbelief",
				"indices", "index",
				"accomplices", "accomplice"
		};
		for (int i = 0; i < tests.length; i += 2) {
			//System.out.println("singular: " + tests[i]);
			//System.out.println("plural: " + RiTa.pluralize(tests[i + 1]));
			assertEquals(tests[i], RiTa.pluralize(tests[i + 1]));
		}

		// uncountable
		tests = new String[] {
				"turf", "macaroni", "spaghetti", "potpourri", "electrolysis"
		};
		for (int i = 0; i < tests.length; i++) {
			assertEquals(tests[i], RiTa.pluralize(tests[i]));
		}

		assertEquals("blondes", RiTa.pluralize("blonde"));
		assertEquals("eyes", RiTa.pluralize("eye"));
		assertEquals("blondes", RiTa.pluralize("blond"));

		assertEquals("dogs", RiTa.pluralize("dog"));
		assertEquals("feet", RiTa.pluralize("foot"));
		assertEquals("men", RiTa.pluralize("man"));

		assertEquals("beautifuls", RiTa.pluralize("beautiful"));
		assertEquals("teeth", RiTa.pluralize("tooth"));
		assertEquals("cakes", RiTa.pluralize("cake"));
		assertEquals("kisses", RiTa.pluralize("kiss"));
		assertEquals("children", RiTa.pluralize("child"));

		assertEquals("randomwords", RiTa.pluralize("randomword"));
		assertEquals("lice", RiTa.pluralize("louse"));

		assertEquals("sheep", RiTa.pluralize("sheep"));
		assertEquals("shrimps", RiTa.pluralize("shrimp"));
		assertEquals("series", RiTa.pluralize("series"));
		assertEquals("mice", RiTa.pluralize("mouse"));

		assertEquals("", RiTa.pluralize(""));

		assertEquals(RiTa.pluralize("tomato"), "tomatoes");
		assertEquals(RiTa.pluralize("toe"), "toes");

		assertEquals(RiTa.pluralize("deer"), "deer");
		assertEquals(RiTa.pluralize("ox"), "oxen");

		assertEquals(RiTa.pluralize("tobacco"), "tobacco");
		assertEquals(RiTa.pluralize("cargo"), "cargo");
		assertEquals(RiTa.pluralize("golf"), "golf");
		assertEquals(RiTa.pluralize("grief"), "grief");
		assertEquals(RiTa.pluralize("wildlife"), "wildlife");
		assertEquals(RiTa.pluralize("taxi"), "taxis");
		assertEquals(RiTa.pluralize("Chinese"), "Chinese");
		assertEquals(RiTa.pluralize("bonsai"), "bonsai");

		assertEquals(RiTa.pluralize("whiz"), "whizzes");
		assertEquals(RiTa.pluralize("prognosis"), "prognoses");
		assertEquals(RiTa.pluralize("gas"), "gases");
		assertEquals(RiTa.pluralize("bus"), "buses");

		assertEquals("crises", RiTa.pluralize("crisis"));
		assertEquals("theses", RiTa.pluralize("thesis"));
		assertEquals("apotheses", RiTa.pluralize("apothesis"));
		assertEquals("stimuli", RiTa.pluralize("stimulus"));
		assertEquals("alumni", RiTa.pluralize("alumnus"));
		assertEquals("corpora", RiTa.pluralize("corpus"));
		assertEquals("menus", RiTa.pluralize("menu"));

		assertEquals("hardness", RiTa.pluralize("hardness"));
		assertEquals("shortness", RiTa.pluralize("shortness"));
		assertEquals("dreariness", RiTa.pluralize("dreariness"));
		assertEquals("unwillingness", RiTa.pluralize("unwillingness"));
		assertEquals("deer", RiTa.pluralize("deer"));
		assertEquals("fish", RiTa.pluralize("fish"));
		assertEquals("moose", RiTa.pluralize("moose"));

		assertEquals("aquatics", RiTa.pluralize("aquatics"));
		assertEquals("mechanics", RiTa.pluralize("mechanics"));
	}

	@Test
	public void testAnalyze() {

		// analyze()

		Map<String, String> hm = new HashMap<String, String>();

		hm.put("tokens", "");
		hm.put("pos", "");
		hm.put("stresses", "");
		hm.put("phonemes", "");
		hm.put("syllables", "");

		// hm = Collections.unmodifiableMap(hm);
		//System.out.println(RiTa.analyze(""));
		assertEquals(RiTa.analyze(""), hm);

		Map<String, String> feats;
		feats = RiTa.analyze("clothes");
		assertEquals(feats.get("pos"), "nns");
		assertEquals(feats.get("tokens"), "clothes");
		assertEquals(feats.get("syllables"), "k-l-ow-dh-z");

		feats = RiTa.analyze("the clothes");
		assertEquals(feats.get("pos"), "dt nns");
		assertEquals(feats.get("tokens"), "the clothes");
		assertEquals(feats.get("syllables"), "dh-ah k-l-ow-dh-z");

		feats = RiTa.analyze("chevrolet");
		assertEquals(feats.get("tokens"), "chevrolet");
		assertEquals(feats.get("syllables"), "sh-eh-v/r-ow/l-ey");

		// analyze(lts)

		feats = RiTa.analyze("cloze");
		assertEquals(feats.get("pos"), "nn");
		assertEquals(feats.get("tokens"), "cloze");
		assertEquals(feats.get("syllables"), "k-l-ow-z");

	}

	@Test
	public void testStresses() {

		String result, answer;

		result = RiTa.stresses("");
		answer = "";
		assertEquals(result, answer);

		result = RiTa.stresses("The emperor had no clothes on");
		answer = "0 1/0/0 1 1 1 1";
		assertEquals(result, answer);

		result = RiTa.stresses("The emperor had no clothes on.");
		answer = "0 1/0/0 1 1 1 1 .";
		assertEquals(result, answer);

		result = RiTa.stresses("The emperor had no clothes on. The King is fat.");
		answer = "0 1/0/0 1 1 1 1 . 0 1 1 1 .";
		assertEquals(result, answer);

		result = RiTa.stresses("to preSENT, to exPORT, to deCIDE, to beGIN");
		answer = "1 1/0 , 1 1/0 , 1 0/1 , 1 0/1";
		assertEquals(result, answer);

		result = RiTa.stresses("to present, to export, to decide, to begin");
		answer = "1 1/0 , 1 1/0 , 1 0/1 , 1 0/1";
		assertEquals(result, answer);

		result = RiTa.stresses("The dog ran faster than the other dog.  But the other dog was prettier.");
		answer = "0 1 1 1/0 1 0 1/0 1 . 1 0 1/0 1 1 1/0/0 .";
		assertEquals(result, answer);

		assertEquals(RiTa.stresses("chevrolet"), "0/0/1");
		assertEquals(RiTa.stresses("women"), "1/0");
		assertEquals(RiTa.stresses("genuine"), "1/0/0");

	}

	@Test
	public void testPhonemes() {

		String result, answer;

		result = RiTa.phonemes("");
		answer = "";
		assertEquals(result, answer);

		result = RiTa.phonemes("The");
		answer = "dh-ah";
		assertEquals(result, answer);

		result = RiTa.phonemes("said");
		answer = "s-eh-d";
		assertEquals(result, answer);

		result = RiTa.phonemes("The.");
		answer = "dh-ah .";
		assertEquals(result, answer);

		result = RiTa.phonemes("The boy jumped over the wild dog.");
		answer = "dh-ah b-oy jh-ah-m-p-t ow-v-er dh-ah w-ay-l-d d-ao-g .";
		assertEquals(result, answer);

		result = RiTa.phonemes("The boy ran to the store.");
		answer = "dh-ah b-oy r-ae-n t-uw dh-ah s-t-ao-r .";
		assertEquals(result, answer);

		result = RiTa.phonemes("The dog ran faster than the other dog.  But the other dog was prettier.");
		answer = "dh-ah d-ao-g r-ae-n f-ae-s-t-er dh-ae-n dh-ah ah-dh-er d-ao-g . b-ah-t dh-ah ah-dh-er d-ao-g w-aa-z p-r-ih-t-iy-er .";
		assertEquals(result, answer);

		result = RiTa.phonemes("flowers");
		answer = "f-l-aw-er-z";
		assertEquals(result, answer);

		result = RiTa.phonemes("quiche");
		answer = "k-iy-sh";
		assertEquals(result, answer);

		result = RiTa.phonemes("mice");
		answer = "m-ay-s";
		assertEquals(result, answer);

		assertEquals(RiTa.phonemes("chevrolet"), "sh-eh-v-r-ow-l-ey");
		assertEquals(RiTa.phonemes("women"), "w-ih-m-eh-n");
		assertEquals(RiTa.phonemes("genuine"), "jh-eh-n-y-uw-w-ah-n");

	}

	@Test
	public void testSyllables() {
		// syllables()

		assertEquals(RiTa.syllables("clothes"), "k-l-ow-dh-z");

		assertEquals(RiTa.syllables(""), "");
		assertEquals(RiTa.syllables("chevrolet"), "sh-eh-v/r-ow/l-ey");

		assertEquals(RiTa.syllables("women"), "w-ih/m-eh-n");
		assertEquals(RiTa.syllables("genuine"), "jh-eh-n/y-uw/w-ah-n");

		String input, expected;

		input = "The emperor had no clothes on.";
		expected = "dh-ah eh-m/p-er/er hh-ae-d n-ow k-l-ow-dh-z aa-n .";
		assertEquals(RiTa.syllables(input), expected);

		input = "The dog ran faster than the other dog. But the other dog was prettier.";
		expected = "dh-ah d-ao-g r-ae-n f-ae/s-t-er dh-ae-n dh-ah ah/dh-er d-ao-g . b-ah-t dh-ah ah/dh-er d-ao-g w-aa-z p-r-ih/t-iy/er .";
		assertEquals(RiTa.syllables(input), expected);

		input = "The dog ran faster than the other dog. But the other dog was prettier.";
		expected = "dh-ah d-ao-g r-ae-n f-ae/s-t-er dh-ae-n dh-ah ah/dh-er d-ao-g . b-ah-t dh-ah ah/dh-er d-ao-g w-aa-z p-r-ih/t-iy/er .";
		assertEquals(RiTa.syllables(input), expected);

		input = "The emperor had no clothes on.";
		expected = "dh-ah eh-m/p-er/er hh-ae-d n-ow k-l-ow-dh-z aa-n .";
		assertEquals(RiTa.syllables(input), expected);

		// syllables(lts)
		RiTa.SILENCE_LTS = true; // TODO : not implemented yet?
		assertEquals(RiTa.syllables("The Laggin Dragon"), "dh-ah l-ae/g-ih-n d-r-ae/g-ah-n");
		RiTa.SILENCE_LTS = false;

	}
}
