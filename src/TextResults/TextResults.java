package TextResults;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * Created by Oliver Coulson on 09/02/2016.
 */
public class TextResults {

    //Update these two fileNames with source and destination files

    final static String FILE_NAME = "/Users/olliecoulson/Documents/IntelliJProjects/MSc/cw-temple/src/TextResults/jonnyMapResultsWithRoutePlanner.txt";
    final static String OUTPUT_FILE_NAME = "/Users/olliecoulson/Documents/IntelliJProjects/MSc/cw-temple/src/TextResults/OutputJonnyMapResultsWithRoutePlanner.txt";
    final static Charset ENCODING = StandardCharsets.UTF_8;

    public static void main(String[] args) {
        TextResults textResults = new TextResults();

        int countOnes = 0;
        Deque<String> output = new ArrayDeque<>();
        List<String> input;
        double total = 0;
        double count = 0;
        try {
            input = textResults.readTextFile(FILE_NAME);

            for (String s : input) {
                if (s.length() == 0) {
                    continue;
                } else if (s.contains("Bonus")) {
                    double value = Double.parseDouble(s.substring(19));
                    total += value;
                    count ++;
                }
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        int numberOfProblems = output.size() / 3;
        String averageBonus = "Average Bonus: " + (total/count);

        output.addFirst(averageBonus);

        try{
            textResults.writeTextFile(output, OUTPUT_FILE_NAME);
        } catch(IOException ex) {
            ex.printStackTrace();
            System.out.println("Output file issue");
        }



    }



    List<String> readTextFile(String aFileName) throws IOException {
        Path path = Paths.get(aFileName);
        return Files.readAllLines(path, ENCODING);
    }

    void writeTextFile(Deque<String> aLines, String aFileName) throws IOException {
        Path path = Paths.get(aFileName);
        Files.write(path, aLines, ENCODING);
    }
}
