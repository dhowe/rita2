import rita.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

Markov markov;
Map<String, List<String>> dictionary = new HashMap <String, List<String>>();

String[] files = { "wittgenstein.txt", "kafka.txt" };
int x = 160, y = 240;
String[] vocabulary;

String line = "click to (re)generate!";
String lineInPos = "";
String newline = "";

void setup()
{
  size(500, 500);
  fill(0);

  // create a markov model from file[0]
  markov = new Markov(4);
  markov.addText(join(loadStrings(files[0]), ""));

  // load the vocabulary from file[1]
  vocabulary = loadStrings(files[1]);
  dictionary = generateDictionary(vocabulary);
}

void draw()
{
  background(250);
  text(line, x, y, 400, 400);
  text(lineInPos, x, 200, 400, 400);
  text(newline, x, 300, 400, 400);
}

void mouseClicked()
{
  if (markov.size() <= 0) return;
  x = y = 50;

  String[] lines = markov.generate(5);
  String[] linesInPos = linesInPos(lines);
  String[] convertedLines = PosToText(lines, linesInPos, dictionary);

  line = join(lines, " ");
  lineInPos = join(linesInPos, " ");
  newline = join(convertedLines, " ");
}

String[] linesInPos(String[] lines) {

  String[] linesInPos = new String[lines.length];
  for (int i = 0; i < lines.length; i++) {
    linesInPos[i] = "";
    String[] posTags = RiTa.pos(lines[i]);

    for (int j = 0; j < posTags.length; j++) 
      linesInPos[i] += posTags[j]+" ";
  }
  return linesInPos;
}//end of linesInPos

String[] PosToText(String[] originalLines, String[] linesInPos, Map<String, List<String>> dictionary) {

  String[] lines = new String[linesInPos.length];

  for (int i = 0; i < lines.length; i++) {
    String[] Poss = split(linesInPos[i], " ");
    //println(originalLines[i]);
    String line = originalLines[i].replaceAll("[^a-zA-Z ]", "");
    String[] lineInWords = split(line, " ");
    int spCH=0;
    lines[i] = "";

    for (int j = 0; j < Poss.length; j++) {
      List<String> myList = dictionary.get(Poss[j]);
      if (myList != null ) {
        String word;
        boolean replacable =  Poss[j].startsWith("nn") || Poss[j].startsWith("jj");
        if (replacable) {
          word = myList.get(floor(random(0, myList.size())));
          //println(Poss[j], word) ;
          if (j == 0) //Capitalize the beginning of each sentense
            word = Character.toUpperCase(word.charAt(0)) + word.substring(1);
        } else word = lineInWords[ j - spCH ] ;//if POS is not replaceable, stay with the original word

        if (j != 0) word = " " + word;
        lines[i] += word;
      } else {
        //for special characters
        lines[i] += Poss[j];
        spCH++;
      }
    }//end of j loop
  }//end of i loop

  return lines;
}//end of PosToText


Map generateDictionary(String[] lines) {

  Map<String, List<String>> dictionary = new HashMap <String, List<String>>();

  for (int i = 0; i < lines.length; i++) {
    String line = lines[i].replaceAll("[^a-zA-Z ]", "");
    line = line.replaceAll("([A-Z])", "").toLowerCase();
    String[] words = split(line, ' ');

    for (int j = 0; j < words.length; j++) {
      String[] posTags;
      try {
        posTags = RiTa.pos(words[j]);
      } catch (NullPointerException e) {
        posTags = new String[0];
      }
      if (posTags.length > 0 && posTags[0].matches("[a-zA-Z\u0024]++")) {
        List<String> myList = dictionary.get(posTags[0]);
        if (myList == null) {
          myList = new ArrayList<String>();
          dictionary.put(posTags[0], myList);
        }
        myList.add(words[j]);
      }
    }
  }

  return dictionary;
}//end of generateDictionarys
