package MessageOfTheDay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MessageOfTheDay{



    private static String urlString(String urlName){
        StringBuilder puzzle = new StringBuilder();

        try {
            URL url = new URL(urlName);
            URLConnection urlConnection = url.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null){
                puzzle.append(line);
            }
            in.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return puzzle.toString();
    }

    private static void shiftLetters(){
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        String puzzle = urlString("http://cswebcat.swansea.ac.uk/puzzle");
        String answer = "";
        int i = 0;
        int shift;
        System.out.println(puzzle);

        while (puzzle.length() > answer.length() ){
            System.out.println(i+1);
            int position = letters.indexOf(puzzle.charAt(i));
            if ((i +1) % 2 == 1){
                shift = (position - (i+1)) % letters.length();
                if (shift < 0) {
                    shift = shift + letters.length();
                }

            }
            else {
                shift = (position + (i+1)) % letters.length();


            }

            char newCharacter = letters.charAt(shift);
            answer += newCharacter;
            System.out.println(answer);
            i++;




        }
        System.out.println(answer);

    }

    public static void main(String[] args){

        shiftLetters();

    }
}
