/*
 * This Java source file was generated by the Gradle "init" task.
 */
package rita.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;

import rita.*;

public class LexiconTests {
	
	// TODO: use Util.opts() instead of creating Maps

	@Test
	public void testHasWord() {
		assertTrue(RiTa.hasWord("random"));
	}

	@Test
	public void randomWord() {
		String result;
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("pos", "xxx");
		assertThrows(RiTaException.class, () -> RiTa.randomWord(hm));

		hm.clear();
		result = RiTa.randomWord(hm);
		assertTrue(result.length() > 0);

		hm.put("pos", "nn");
		result = RiTa.randomWord(hm);
		assertTrue(result.length() > 0);

		hm.clear();
		hm.put("pos", "nns");
		result = RiTa.randomWord(hm);
		assertTrue(result.length() > 0);

		hm.clear();
		hm.put("pos", "n");
		result = RiTa.randomWord(hm);
		assertTrue(result.length() > 0);

		hm.clear();
		hm.put("pos", "v");
		result = RiTa.randomWord(hm);
		assertTrue(result.length() > 0);

		hm.clear();
		hm.put("numSyllables", 3);
		result = RiTa.randomWord(hm);
		assertTrue(result.length() > 0);

		hm.clear();
		hm.put("numSyllables", 5);
		result = RiTa.randomWord(hm);
		assertTrue(result.length() > 0);

	}

	@Test
	public void testAugmentedLexicon() {
		Lexicon lexicon = RiTa._lexicon();
		lexicon.dict.put("deg", new String[] { "d-eh1-g", "nn" });
		lexicon.dict.put("wadly", new String[] { "w-ae1-d l-iy", "rb" });

		assertTrue(RiTa.hasWord("run"));
		assertTrue(RiTa.hasWord("walk"));
		assertTrue(RiTa.hasWord("deg"));
		assertTrue(RiTa.hasWord("wadly"));
		assertTrue(RiTa.isAlliteration("wadly", "welcome"));

		// remove two added entries
		lexicon.dict.remove("deg");
		lexicon.dict.remove("wadly");
	}

	@Test
	public void testCustomLexicon() {
		Lexicon lexicon = RiTa._lexicon();
		Map<String, String[]> orig = lexicon.dict;
		Map<String, String[]> data = new HashMap<String, String[]>();
		data.put("dog", new String[] { "d-ao1-g", "nn" });
		data.put("cat", new String[] { "k-ae1-t", "nn" });
		data.put("happily", new String[] { "hh-ae1 p-ah l-iy", "rb" });
		data.put("walk", new String[] { "w-ao1-k", "vb vbp nn" });
		data.put("welcome", new String[] { "w-eh1-l k-ah-m", "jj nn vb vbp" });
		data.put("sadly", new String[] { "s-ae1-d l-iy", "rb" });

		for (Map.Entry<String, String[]> entry : data.entrySet())
			lexicon.dict.put(entry.getKey(), entry.getValue());

		assertTrue(!RiTa.hasWord("run"));
		assertTrue(RiTa.hasWord("walk"));
		assertTrue(RiTa.isAlliteration("walk", "welcome"));
		lexicon.dict = orig;
	}

	@Test
	public void testRandomWordNNS() {
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("pos", "nns");

		for (int i = 0; i < 20; i++) {
			String result = RiTa.randomWord(hm);
			if (!Inflector.isPlural(result)) {
				// For now, just warn here as there are too many edge cases (see #521)
				System.err.println("Pluralize/Singularize problem: randomWord(nns) was " + result +
						" (" + "isPlural=" + Inflector.isPlural(result) + "), singularized is " + RiTa.singularize(result)
						+ ")");
			}
			// TODO: occasional problem here, examples: beaux

			//No vbg, No -ness, -ism
			String pos = RiTa._lexicon()._posData(result); //private
			assertTrue(pos.length() > 0);
			assertTrue(pos.indexOf("vbg") < 0);
			assertTrue(!result.endsWith("ness"));
			assertTrue(!result.endsWith("isms"));
		}

	}

	@Test
	public void testRandomWordPos() {
		String[] pos = { "nn", "jj", "jjr", "wp" };
		Map<String, Object> hm = new HashMap<String, Object>();

		for (int j = 0; j < pos.length; j++) {
			for (int i = 0; i < 5; i++) {
				hm.clear();
				hm.put("pos", pos[j]);
				String result = RiTa.randomWord(hm);
				String best = RiTa._lexicon()._bestPos(result);// private
				//System.out.println(result+": "+pos[j]+" ?= "+best);
				assertEquals(pos[j], best);
			}
		}

	}

	@Test
	public void testRandomWordSyls() {
		int i = 0;
		String result = "";
		String syllables = "";
		int num = 0;
		Map<String, Object> hm = new HashMap<String, Object>();

		hm.put("numSyllables", 3);
		for (i = 0; i < 10; i++) {
			result = RiTa.randomWord(hm);
			syllables = RiTa.syllables(result);
			num = syllables.split(RiTa.SYLLABLE_BOUNDARY).length;
			assertTrue(result.length() > 0);
			assertTrue(num == 3); // "3 syllables: "
		}

		hm.clear();
		hm.put("numSyllables", 5);
		for (i = 0; i < 10; i++) {
			result = RiTa.randomWord(hm);
			syllables = RiTa.syllables(result);
			num = syllables.split(RiTa.SYLLABLE_BOUNDARY).length;
			assertTrue(result.length() > 0);
			assertTrue(num == 5); // "5 syllables: "
		}

	}

	@Test
	public void testSearchWithoutOpts() {
		assertTrue(RiTa.search().length > 20000);
	}

	@Test
	public void testSearchWithLetters() {
		String[] results = {
				"elephant",
				"elephantine",
				"phantom",
				"sycophantic",
				"triumphant",
				"triumphantly"
		};
		assertArrayEquals(RiTa.search("phant"), results);
		//assertArrayEquals(RiTa.search("/phant/"), results);
	}

	@Test
	public void testSearchWithPhones() {

		String[] res1 = RiTa.search("f-ah-n-t", Util.opts("type", "phones", "limit", 5));
		System.out.println(Arrays.asList(res1));
		assertArrayEquals(res1, new String[] {
				"elephant",
				"infant",
				"infantile",
				"infantry",
				"oftentimes"
		});

		String[] res2 = RiTa.search("/f-a[eh]-n-t/", Util.opts("type", "phones", "limit", 10));
		System.out.println(Arrays.asList(res2));
		assertArrayEquals(res2, new String[] {
				"elephant",
				"elephantine",
				"fantasia",
				"fantasize",
				"fantastic",
				"fantastically",
				"fantasy",
				"infant",
				"infantile",
				"infantry"
		});
	}

	@Test
	public void testSearchWithPos() {
		String[] res;
		Map<String, Object> hm = new HashMap<String, Object>();
//		hm.put("type", "stresses");
//		hm.put("limit", 5);
//		hm.put("pos", "n");
//		assertArrayEquals(RiTa.search("010", hm),
//				new String[] { "abalone", "abandonment", "abatement", "abbreviation", "abdomen" });
//
//		hm.put("numSyllables", 3);
//		assertArrayEquals(RiTa.search("010", hm),
//				new String[] { "abatement", "abdomen", "abduction", "abeyance", "abortion" });
//
//		hm.clear();
//		hm.put("type", "phones");
//		hm.put("limit", 3);
//		hm.put("pos", "n");
//		assertArrayEquals(RiTa.search("f-ah-n-t", hm), new String[] { "elephant", "infant", "infantry" });
//
//		hm.put("numSyllables", 2);
//		assertArrayEquals(RiTa.search("f-ah-n-t", hm), new String[] { "infant" });
//
//		hm.clear();
//		hm.put("type", "phones");
//		hm.put("limit", 5);
//		hm.put("pos", "v");
//		assertArrayEquals(RiTa.search("/f-a[eh]-n-t/", hm), new String[] { "fantasize" });

		hm.clear();
		hm.put("type", "stresses");
		hm.put("limit", 5);
		hm.put("pos", "nns");
		res = RiTa.search("010", hm);
		console.log(res);
		assertArrayEquals(res,
				new String[] { "abalone", "abandonments", "abatements", "abbreviations", "abdomens" });

		
if (1==1)return;

		hm.put("numSyllables", 3);
		assertArrayEquals(RiTa.search("010", hm),
				new String[] { "abatements", "abdomens", "abductions", "abeyances", "abortions" });

		hm.clear();
		hm.put("type", "phones");
		hm.put("limit", 3);
		hm.put("pos", "nns");
		assertArrayEquals(RiTa.search("f-ah-n-t", hm), new String[] { "elephants", "infants", "infantries" });

		hm.clear();
		hm.put("type", "phones");
		hm.put("limit", 5);
		hm.put("pos", "vb");
		assertArrayEquals(RiTa.search("/f-a[eh]-n-t/", hm), new String[] { "fantasize" });
	}

	@Test
	public void testSearchWithStress() {
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("type", "stresses");
		hm.put("limit", 5);

		assertArrayEquals(RiTa.search("0/1/0/0/0/0", hm), new String[] {
				"accountability",
				"anticipatory",
				"appreciatively",
				"authoritarianism",
				"colonialism" });

		assertArrayEquals(RiTa.search("010000", hm), new String[] {
				"accountability",
				"anticipatory",
				"appreciatively",
				"authoritarianism",
				"colonialism" });

		hm.put("maxLength", 11);

		assertArrayEquals(RiTa.search("010000", hm), new String[] {
				"colonialism",
				"imperialism",
				"materialism" });

		hm.clear();
		hm.put("type", "stresses");
		hm.put("limit", 5);
		hm.put("minLength", 12);

		assertArrayEquals(RiTa.search("010000", hm), new String[] {
				"accountability",
				"anticipatory",
				"appreciatively",
				"authoritarianism",
				"conciliatory" });

	}

	@Test
	public void testRandomWordPosSyls() {
		// function fail(result, epos) {
		// let test = result.endsWith('es') ? result.substring(-2) : result;
		// let ent = RiTa.lexicon()[test];
		// return ('(' + epos + ') Fail: ' + result + ': expected ' + epos + ', got ' +
		// (ent ? ent[1] : 'null'));
		// }

		Map<String, Object> hm = new HashMap<String, Object>();
		String result, syllables;
		RiTa.SILENCE_LTS = true;

		for (int j = 0; j < 1; j++) {

			for (int i = 0; i < 5; i++) {
				hm.put("pos", "vbz");
				hm.put("numSyllables", 3);
				result = RiTa.randomWord(hm);
				assertTrue(result.length() > 0);
				syllables = RiTa.syllables(result);
				assertEquals(syllables.split(RiTa.SYLLABLE_BOUNDARY).length, 3);
				assertTrue(RiTa.isVerb(result));

				hm.clear();
				hm.put("pos", "n");
				hm.put("numSyllables", 1);
				result = RiTa.randomWord(hm);
				assertTrue(result.length() > 0);
				syllables = RiTa.syllables(result);
				assertEquals(syllables.split(RiTa.SYLLABLE_BOUNDARY).length, 1);
				assertTrue(RiTa.isNoun(result));

				hm.clear();
				hm.put("pos", "nns");
				hm.put("numSyllables", 1);
				result = RiTa.randomWord(hm);
				assertTrue(result.length() > 0);
				syllables = RiTa.syllables(result);
				assertEquals(syllables.split(RiTa.SYLLABLE_BOUNDARY).length, 1);
				assertTrue(RiTa.isNoun(result));

				hm.clear();
				hm.put("pos", "nns");
				hm.put("numSyllables", 5);
				result = RiTa.randomWord(hm);
				assertTrue(result.length() > 0);
				syllables = RiTa.syllables(result);
				assertEquals(syllables.split(RiTa.SYLLABLE_BOUNDARY).length, 5);
				assertTrue(RiTa.isNoun(result));

			}
		}

		RiTa.SILENCE_LTS = false;

	}

	@Test
	public void testToPhoneArray() {

		String[] result = RiTa._lexicon().toPhoneArray(RiTa._lexicon()._rawPhones("tornado", false));
		String[] ans = { "t", "ao", "r", "n", "ey", "d", "ow" };
		assertArrayEquals(result, ans);

	}

	@Test
	public void testAlliterationsNumSyllables() {
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("minLength", 1);
		hm.put("numSyllables", 7);

		String[] result = RiTa.alliterations("cat", hm);
		assertArrayEquals(result, new String[] { "electrocardiogram", "electromechanical", "telecommunications" });

		for (int i = 0; i < result.length; i++) {
			assertTrue(RiTa.isAlliteration(result[i], "cat"));
		}

	}

	@Test
	public void testAlliterationsPos() {
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("minLength", 1);
		hm.put("numSyllables", 7);
		hm.put("pos", "n");

		String[] result = RiTa.alliterations("cat", hm);

		assertArrayEquals(result, new String[] { "electrocardiogram", "telecommunications" });

		for (int i = 0; i < result.length; i++) {
			assertTrue(RiTa.isAlliteration(result[i], "cat"));
		}

		hm.clear();
		hm.put("minLength", 14);
		hm.put("pos", "v");

		assertArrayEquals(RiTa.alliterations("dog", hm), new String[] { "disenfranchise" });

		hm.clear();
		hm.put("minLength", 13);
		hm.put("pos", "rb");

		assertArrayEquals(RiTa.alliterations("dog", hm), new String[] {
				"coincidentally",
				"conditionally",
				"confidentially",
				"contradictorily",
				"devastatingly",
				"expeditiously",
				"paradoxically",
				"predominantly",
				"traditionally",
				"unconditionally",
				"unpredictably" });

		hm.clear();
		hm.put("minLength", 14);
		hm.put("pos", "nns");

		assertArrayEquals(RiTa.alliterations("freedom", hm), new String[] {
				"featherbeddings",
				"fundamentalists",
				"malfunctionings",
				"pharmaceuticals",
				"photosyntheses",
				"reconfigurations",
				"sophistications" });

	}

	@Test
	public void testAlliterations() {

		String[] result;

		// TODO: make sure we have LTS cases in here

		result = RiTa.alliterations("");
		assertTrue(result.length < 1);

		result = RiTa.alliterations("#$%^&*");
		assertTrue(result.length < 1);

		result = RiTa.alliterations("umbrella");
		assertTrue(result.length < 1);

		result = RiTa.alliterations("cat");
		assertTrue(result.length > 2000);
		for (int i = 0; i < result.length; i++) {
			assertTrue(RiTa.isAlliteration(result[i], "cat"));
		}

		result = RiTa.alliterations("dog");
		assertTrue(result.length > 1000);
		for (int i = 0; i < result.length; i++) {
			assertTrue(RiTa.isAlliteration(result[i], "dog"));
		}

		Map<String, Object> hm = new HashMap<String, Object>();

		hm.put("matchMinLength", 15);

		result = RiTa.alliterations("dog", hm);
		assertTrue(result.length > 0 && result.length < 5); // , "got length=" + result.length
		for (int i = 0; i < result.length; i++) {
			assertTrue(RiTa.isAlliteration(result[i], "dog")); // , "FAIL1: " + result[i]
		}

		hm.clear();
		hm.put("matchMinLength", 16);

		result = RiTa.alliterations("cat", hm);
		assertTrue(result.length > 0 && result.length < 15);
		for (int i = 0; i < result.length; i++) {
			assertTrue(RiTa.isAlliteration(result[i], "cat"));// , "FAIL2: " + result[i]
		}

		result = RiTa.alliterations("khatt", hm);
		assertTrue(result.length > 0 && result.length < 15);
		for (int i = 0; i < result.length; i++) {
			assertTrue(RiTa.isAlliteration(result[i], "cat"));// , "FAIL2: " + result[i]
		}

	}

	public static boolean contains(String[] arr, String item) {
		for (String n : arr) {
			if (item == n) {
				return true;
			}
		}
		return false;
	}

	@Test
	public void testRhymes() {

		assertTrue(Arrays.asList(RiTa.rhymes("cat")).contains("hat"));
		assertTrue(Arrays.asList(RiTa.rhymes("yellow")).contains("mellow"));
		assertTrue(Arrays.asList(RiTa.rhymes("toy")).contains("boy"));
		assertTrue(Arrays.asList(RiTa.rhymes("sieve")).contains("give"));

		assertTrue(Arrays.asList(RiTa.rhymes("tense")).contains("sense"));
		assertTrue(Arrays.asList(RiTa.rhymes("crab")).contains("drab"));
		assertTrue(Arrays.asList(RiTa.rhymes("shore")).contains("more"));

		assertTrue(Arrays.asList(RiTa.rhymes("mouse")).contains("house"));

		assertTrue(Arrays.asList(RiTa.rhymes("weight")).contains("eight"));
		//		System.out.println(Arrays.asList(RiTa.rhymes("eight")));
		assertTrue(Arrays.asList(RiTa.rhymes("eight")).contains("weight"));

		assertTrue(!Arrays.asList(RiTa.rhymes("apple")).contains("polo"));
		assertTrue(!Arrays.asList(RiTa.rhymes("this")).contains("these"));

		assertTrue(!Arrays.asList(RiTa.rhymes("hose")).contains("house"));
		assertTrue(!Arrays.asList(RiTa.rhymes("sieve")).contains("mellow"));
		assertTrue(!Arrays.asList(RiTa.rhymes("swag")).contains("grab"));

	}

	@Test
	public void testRhymesPos() {
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("pos", "v");

		assertTrue(!Arrays.asList(RiTa.rhymes("cat", hm)).contains("hat"));
		assertTrue(Arrays.asList(RiTa.rhymes("tense", hm)).contains("sense"));
		assertTrue(!Arrays.asList(RiTa.rhymes("shore", hm)).contains("more"));
		assertTrue(!Arrays.asList(RiTa.rhymes("apple")).contains("polo"));
		assertTrue(!Arrays.asList(RiTa.rhymes("this")).contains("these"));
		assertTrue(!Arrays.asList(RiTa.rhymes("hose")).contains("house"));
		assertTrue(!Arrays.asList(RiTa.rhymes("sieve")).contains("mellow"));
		assertTrue(!Arrays.asList(RiTa.rhymes("swag")).contains("grab"));

		hm.clear();
		hm.put("pos", "a");
		assertTrue(Arrays.asList(RiTa.rhymes("yellow", hm)).contains("mellow"));

		hm.clear();
		hm.put("pos", "n");
		assertTrue(Arrays.asList(RiTa.rhymes("toy", hm)).contains("boy"));
		assertTrue(!Arrays.asList(RiTa.rhymes("sieve", hm)).contains("give"));
		assertTrue(!Arrays.asList(RiTa.rhymes("crab", hm)).contains("drab"));

		hm.clear();
		hm.put("pos", "nn");
		assertTrue(Arrays.asList(RiTa.rhymes("mouse", hm)).contains("house"));
		assertTrue(Arrays.asList(RiTa.rhymes("eight", hm)).contains("weight"));

		hm.clear();
		hm.put("pos", "vb");
		assertTrue(Arrays.asList(RiTa.rhymes("weight", hm)).contains("eight"));

	}

	@Test
	public void testRhymesNumSyllables() {
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("numSyllables", 1);
		assertTrue(Arrays.asList(RiTa.rhymes("cat", hm)).contains("hat"));

		hm.clear();
		hm.put("numSyllables", 2);
		assertTrue(!Arrays.asList(RiTa.rhymes("cat", hm)).contains("hat"));
		assertTrue(Arrays.asList(RiTa.rhymes("yellow", hm)).contains("mellow"));

		hm.clear();
		hm.put("numSyllables", 3);
		assertTrue(!Arrays.asList(RiTa.rhymes("cat", hm)).contains("hat"));
		assertTrue(!Arrays.asList(RiTa.rhymes("yellow", hm)).contains("mellow"));
	}

	@Test
	public void testRhymesWordLength() {
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("minLength", 4);
		assertTrue(!Arrays.asList(RiTa.rhymes("cat", hm)).contains("hat"));

		hm.clear();
		hm.put("maxLength", 2);
		assertTrue(!Arrays.asList(RiTa.rhymes("cat", hm)).contains("hat"));
	}

	@Test
	public void testSpellsLike() {
		String[] result;

		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("minLength", 6);
		hm.put("maxLength", 6);

		result = RiTa.spellsLike("banana", hm);
		assertArrayEquals(result, new String[] { "cabana" });

		hm.clear();

		result = RiTa.spellsLike("");
		assertArrayEquals(result, new String[] { });

		result = RiTa.spellsLike("banana", hm);
		assertArrayEquals(result, new String[] { "banal", "bonanza", "cabana", "manna" });

		hm.put("minLength", 6);
		hm.put("maxLength", 6);
		result = RiTa.spellsLike("banana", hm);
		assertArrayEquals(result, new String[] { "cabana" });

		hm.clear();
		hm.put("minDistance", 1);
		assertArrayEquals(result, new String[] { "banal", "bonanza", "cabana", "manna" });

		result = RiTa.spellsLike("tornado");
		assertArrayEquals(result, new String[] { "torpedo" });

		result = RiTa.spellsLike("ice");
		assertArrayEquals(result, new String[] { "ace", "dice", "iced", "icy", "ire", "nice", "rice", "vice" });

		hm.clear();
		hm.put("minAllowedDistance", 1);
		result = RiTa.spellsLike("ice", hm);
		assertArrayEquals(result, new String[] { "ace", "dice", "iced", "icy", "ire", "nice", "rice", "vice" });

		hm.clear();
		hm.put("minAllowedDistance", 2);
		hm.put("minLength", 3);
		hm.put("maxLength", 3);
		result = RiTa.spellsLike("ice", hm);
		assertTrue(result.length > 10);

		hm.clear();
		hm.put("minLength", 3);
		hm.put("maxLength", 3);
		hm.put("minAllowedDistance", 0);
		result = RiTa.spellsLike("ice", hm);
		assertArrayEquals(result, new String[] { "ace", "icy", "ire" });

		hm.clear();
		hm.put("minLength", 3);
		hm.put("maxLength", 3);
		result = RiTa.spellsLike("ice", hm);
		assertArrayEquals(result, new String[] { "ace", "icy", "ire" });

		hm.put("pos", "n");
		result = RiTa.spellsLike("ice", hm);
		assertArrayEquals(result, new String[] { "ace", "ire" });

		hm.clear();
		hm.put("minLength", 4);
		hm.put("maxLength", 4);
		hm.put("pos", "v");
		hm.put("limit", 5);
		result = RiTa.spellsLike("ice", hm);
		assertArrayEquals(result, new String[] { "ache", "bide", "bite", "cite", "dine" });

		hm.clear();
		hm.put("minLength", 4);
		hm.put("maxLength", 4);
		hm.put("pos", "nns");
		hm.put("limit", 5);
		result = RiTa.spellsLike("ice", hm);
		assertArrayEquals(result, new String[] { "aches", "acres", "aides", "apices", "axes" });

		result = RiTa.spellsLike("123", hm);
		assertTrue(result.length > 400);

	}

	@Test
	public void testSoundsLike() {
		String[] result, answer;
		Map<String, Object> hm = new HashMap<String, Object>();

		result = RiTa.soundsLike("tornado");
		assertArrayEquals(result, new String[] { "torpedo" });

		result = RiTa.soundsLike("try");
		answer = new String[] { "cry", "dry", "fry", "pry", "rye", "tie", "tray",
				"tree", "tribe", "tried", "tripe", "trite", "true", "wry" };
		assertArrayEquals(result, answer);

		hm.put("minAllowedDistance", 2);
		result = RiTa.soundsLike("try", hm);
		assertTrue(result.length > answer.length); // more

		result = RiTa.soundsLike("happy");
		answer = new String[] { "happier", "hippie" };
		assertArrayEquals(result, answer);

		result = RiTa.soundsLike("happy", hm);
		assertTrue(result.length > answer.length); // more

		result = RiTa.soundsLike("cat");
		answer = new String[] { "bat", "cab", "cache", "calf", "calve", "can",
				"can\"t", "cap", "capped", "cash", "cashed", "cast", "caste", "catch",
				"catty", "caught", "chat", "coat", "cot", "curt", "cut", "fat", "hat", "kit",
				"kite", "mat", "matt", "matte", "pat", "rat", "sat", "tat", "that", "vat" };
		assertArrayEquals(result, answer);

		hm.clear();
		hm.put("limit", 5);
		result = RiTa.soundsLike("cat", hm);
		answer = new String[] { "abashed", "abate", "abbey", "abbot", "abet" };
		assertArrayEquals(result, answer);

		hm.clear();
		hm.put("minLength", 2);
		hm.put("maxLength", 4);
		result = RiTa.soundsLike("cat", hm);
		answer = new String[] { "at", "bat", "cab", "calf", "can", "cap", "cash", "cast", "chat", "coat", "cot", "curt",
				"cut", "fat", "hat", "kit", "kite", "mat", "matt", "pat", "rat", "sat", "tat", "that", "vat" };
		assertArrayEquals(result, answer);

		hm.clear();
		hm.put("minLength", 4);
		hm.put("maxLength", 5);
		hm.put("pos", "jj");
		hm.put("limit", 8);
		result = RiTa.soundsLike("cat", hm);
		answer = new String[] { "acute", "aged", "airy", "alert", "arty", "awed", "awry", "azure" };

		hm.clear();
		hm.put("minLength", 2);
		assertTrue(result.length > answer.length); // more

	}

	@Test
	public void testSoundsLikeMatchSpelling() {
		String[] result, answer;
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("matchSpelling", true);

		result = RiTa.soundsLike("try", hm);
		answer = new String[] { "cry", "dry", "fry", "pry", "tray", "wry" };
		assertArrayEquals(result, answer);

		hm.put("maxLength", 3);
		result = RiTa.soundsLike("try", hm);
		answer = new String[] { "cry", "dry", "fry", "pry", "wry" };
		assertArrayEquals(result, answer);

		hm.clear();
		hm.put("matchSpelling", true);
		hm.put("minLength", 4);
		result = RiTa.soundsLike("try", hm);
		answer = new String[] { "tray" };
		assertArrayEquals(result, answer);

		hm.clear();
		hm.put("matchSpelling", true);
		hm.put("limit", 3);
		result = RiTa.soundsLike("try", hm);
		answer = new String[] { "cry", "dry", "fry" };
		assertArrayEquals(result, answer);

		hm.clear();
		hm.put("matchSpelling", true);
		result = RiTa.soundsLike("daddy", hm);
		answer = new String[] { "dandy", "paddy" };
		assertArrayEquals(result, answer);

		result = RiTa.soundsLike("banana", hm);
		answer = new String[] { "bonanza" };
		assertArrayEquals(result, answer);
	}

	@Test
	public void testIsRhyme() {

		assertTrue(!RiTa.isRhyme("apple", "polo"));
		assertTrue(!RiTa.isRhyme("this", "these"));

		assertTrue(RiTa.isRhyme("cat", "hat"));
		assertTrue(RiTa.isRhyme("yellow", "mellow"));
		assertTrue(RiTa.isRhyme("toy", "boy"));

		assertTrue(RiTa.isRhyme("solo", "tomorrow"));
		assertTrue(RiTa.isRhyme("tense", "sense"));
		assertTrue(RiTa.isRhyme("crab", "drab"));
		assertTrue(RiTa.isRhyme("shore", "more"));
		assertTrue(!RiTa.isRhyme("hose", "house"));
		assertTrue(!RiTa.isRhyme("sieve", "mellow"));

		assertTrue(RiTa.isRhyme("mouse", "house")); // why??
		// assertTrue(!RiTa.isRhyme("solo", "yoyo"));
		// assertTrue(!RiTa.isRhyme("yoyo", "jojo")); -> Known Issues

		assertTrue(RiTa.isRhyme("yo", "bro"));
		assertTrue(!RiTa.isRhyme("swag", "grab"));
		assertTrue(!RiTa.isRhyme("", ""));

		assertTrue(RiTa.isRhyme("weight", "eight"));
		assertTrue(RiTa.isRhyme("eight", "weight"));

		assertTrue(RiTa.isRhyme("sieve", "give"));

	}

	@Test
	public void testIsAlliteration() {

		assertTrue(RiTa.isAlliteration("knife", "gnat")); // gnat=lts
		assertTrue(RiTa.isAlliteration("knife", "naughty"));

		assertTrue(RiTa.isAlliteration("sally", "silly"));
		assertTrue(RiTa.isAlliteration("sea", "seven"));
		assertTrue(RiTa.isAlliteration("silly", "seven"));
		assertTrue(RiTa.isAlliteration("sea", "sally"));

		assertTrue(RiTa.isAlliteration("big", "bad"));
		assertTrue(RiTa.isAlliteration("bad", "big")); // swap position

		assertTrue(RiTa.isAlliteration("BIG", "bad")); // CAPITAL LETTERS
		assertTrue(RiTa.isAlliteration("big", "BAD")); // CAPITAL LETTERS
		assertTrue(RiTa.isAlliteration("BIG", "BAD")); // CAPITAL LETTERS

		// False
		assertTrue(!RiTa.isAlliteration("", ""));
		assertTrue(!RiTa.isAlliteration("wind", "withdraw"));
		assertTrue(!RiTa.isAlliteration("solo", "tomorrow"));
		assertTrue(!RiTa.isAlliteration("solo", "yoyo"));
		assertTrue(!RiTa.isAlliteration("yoyo", "jojo"));
		assertTrue(!RiTa.isAlliteration("cat", "access"));

		assertTrue(RiTa.isAlliteration("unsung", "sine"));
		assertTrue(RiTa.isAlliteration("job", "gene"));
		assertTrue(RiTa.isAlliteration("jeans", "gentle"));

		assertTrue(RiTa.isAlliteration("abet", "better"));
		assertTrue(RiTa.isAlliteration("never", "knight"));
		assertTrue(RiTa.isAlliteration("knight", "navel"));
		assertTrue(RiTa.isAlliteration("cat", "kitchen"));

		// not counting assonance
		assertTrue(!RiTa.isAlliteration("octopus", "oblong"));
		assertTrue(!RiTa.isAlliteration("omen", "open"));
		assertTrue(!RiTa.isAlliteration("amicable", "atmosphere"));

		assertTrue(RiTa.isAlliteration("this", "these"));
		assertTrue(RiTa.isAlliteration("psychology", "cholera"));
		assertTrue(RiTa.isAlliteration("consult", "sultan"));
		assertTrue(RiTa.isAlliteration("monsoon", "super"));

	}

}
