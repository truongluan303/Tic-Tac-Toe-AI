import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileIO {
    private final String filename = "match_history.txt";



    public void write(char winner) {
        try {
            File myFile = new File(filename); // get the file
            myFile.createNewFile(); // create new file if it does not exist

            FileWriter fr = new FileWriter(filename, true);
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr = new PrintWriter(br);

            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"); // format date and time
            LocalDateTime instance = LocalDateTime.now(); // get current date and time

            pr.println(formatter.format(instance));
            if (winner == ' ')
                pr.println("Draw");
            else
                pr.println(winner + " wins");
            pr.println();

            pr.close();
            br.close();
            fr.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }




    public JScrollPane read() {
        JTextArea myText = new JTextArea();

        try {
            File file = new File(filename);    //creates a new file instance
            FileReader fr = new FileReader(file);   //reads the file
            BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream

            myText.read(br, null);
            br.close();
            myText.requestFocus();
        }
        catch (IOException e) {
            try {
                File myFile = new File(filename);
                myFile.createNewFile(); // create new file
            } catch (Exception ignored) {}
        }

        myText.setEditable(false);

        return new JScrollPane(myText);
    }
}
