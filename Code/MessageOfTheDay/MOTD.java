import java.io.*;
import java.net.*;

public class MOTD {

    public static String readInMOTD () throws Exception {
        StringBuilder puzzle = new StringBuilder();
        URL motdURL = new URL("http://cswebcat.swansea.ac.uk/puzzle");
        URLConnection conn = motdURL.openConnection();
        InputStream message = conn.getInputStream();
        BufferedReader readIn = new BufferedReader(new InputStreamReader(message));
        puzzle.append(readIn.readLine());
        readIn.close();
        return (puzzle.toString());
    }

    public static String processMOTD (String MOTD) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int position;
        char newLetter;
        String solution = "";
        for (int i = 0; i == MOTD.length() - 1; i++) {
            position = alphabet.indexOf(MOTD.charAt(i));
            System.out.println(position);
            if (i % 2 == 0 || i == 0) {
                newLetter = alphabet.charAt(position + (i + 1));
                System.out.println(newLetter);
            }
            else {
                newLetter = alphabet.charAt(position - (i + 1));
            }
            solution += newLetter;
        }
        System.out.println(solution.toString());
        int solutionLength = ("CS-230" + solution.toString()).length();
        return ("CS-230" + solution.toString() + solutionLength);
    }

    public static String confirmMOTD (String MOTD) throws Exception {
        StringBuilder output = new StringBuilder();
        System.out.println("BRUH" + " " + MOTD);
        System.out.println("http://cswebcat.swansea.ac.uk/message?solution=" + MOTD);
        URL solutionCheckURL = new URL("http://cswebcat.swansea.ac.uk/message?solution=" + MOTD);
        URLConnection conn = solutionCheckURL.openConnection();
        InputStream message = conn.getInputStream();
        BufferedReader readIn = new BufferedReader(new InputStreamReader(message));
        output.append(readIn.readLine());
        readIn.close();
        return (output.toString());
    }

    public static void main (String[] args) {
        try {
            System.out.println(confirmMOTD(processMOTD(readInMOTD())));
        }
        catch (Exception exception) {
            System.out.println("INCORRECT_SOLUTION");
        }
    }

}

