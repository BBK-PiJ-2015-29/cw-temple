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
import javax.swing.JOptionPane;

/**
 * Created by Oliver Coulson on 09/02/2016.
 */
public class TextResults {

    //Update these two fileNames with source and destination files

    final static Charset ENCODING = StandardCharsets.UTF_8;

    public static void main(String[] args) {
        String FILE_NAME;

        FILE_NAME = JOptionPane.showInputDialog("Insert Input File Path");

        TextResults textResults = new TextResults();

        List<String> input;
        double totalBonus = 0;
        double totalScore = 0;

        double count = 0;
        double highestBonus = 0;
        double highestScore = 0;

        try {
            input = textResults.readTextFile(FILE_NAME);

            for (String s : input) {
                if (s.length() == 0) {
                    continue;
                } else if (s.contains("Bonus")) {
                    double value = Double.parseDouble(s.substring(19));
                    if (value > highestBonus) {
                        highestBonus = value;
                    }
                    totalBonus += value;
                    count ++;
                } else if (s.contains("Score")) {
                    double value = Double.parseDouble(s.substring(19));
                    if (value > highestScore) {
                        highestScore = value;
                    }
                    totalScore += value;
                }
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }

        String info = "Average Bonus: " + (totalBonus/count) + "\nHighest Bonus: " + highestBonus +
                "\nAverage Score: " + (totalScore/count) + "\nHighestScore: " + (highestScore);

        JOptionPane.showMessageDialog(null, info);

    }



    List<String> readTextFile(String aFileName) throws IOException {
        Path path = Paths.get(aFileName);
        return Files.readAllLines(path, ENCODING);
    }

}
