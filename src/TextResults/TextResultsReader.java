package TextResults;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Created by Oliver Coulson on 09/02/2016.
 */
public class TextResultsReader {

    public static void main(String[] args) {
        TextResultsReader textResultsReader = new TextResultsReader();

        int countOnes = 0;
        Deque<String> output = new ArrayDeque<>();
        List<String> input;
        try {
            input = textResultsReader.readSmallTextFile(FILE_NAME);
            String seed = "";
            String bonus = "";
            for (String s : input) {
                if (s.length() == 0) {
                    continue;
                } else if (s.contains("Seed")) {
                    seed = s;
                } else if (s.contains("Bonus")) {
                    bonus = s;
                    double value = Double.parseDouble(s.substring(19));
                    if (value < 1.2) {
                        output.addLast(seed);
                        output.addLast(bonus);
                        output.addLast("");
                        if (value == 1) {
                            countOnes++;
                        }
                    }

                }

            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        int numberOfProblems = output.size() / 3;
        String summary = "Number of problem seeds: " + numberOfProblems;
        String summary2 = "Number of Bonus 1: " + countOnes;

        output.addFirst("");
        output.addFirst(summary2);
        output.addFirst(summary);


        try{
            textResultsReader.writeSmallTextFile(output, OUTPUT_FILE_NAME);
        } catch(IOException ex) {
            ex.printStackTrace();
            System.out.println("Output file issue");
        }



    }

    final static String FILE_NAME = "/Users/olliecoulson/Documents/IntelliJProjects/MSc/cw-temple/src/TextResults/DepthFirstMark2TextResults";
    final static String OUTPUT_FILE_NAME = "/Users/olliecoulson/Documents/IntelliJProjects/MSc/cw-temple/src/TextResults/FilteredDepthFirstMark2TextResults.txt";
    final static Charset ENCODING = StandardCharsets.UTF_8;


    List<String> readSmallTextFile(String aFileName) throws IOException {
        Path path = Paths.get(aFileName);
        return Files.readAllLines(path, ENCODING);
    }

    void writeSmallTextFile(Deque<String> aLines, String aFileName) throws IOException {
        Path path = Paths.get(aFileName);
        Files.write(path, aLines, ENCODING);
    }
}
