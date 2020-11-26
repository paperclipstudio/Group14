package MessageOfTheDay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MessageOfTheDay{



    private static String getUrlString(String urlName){
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

    private static String shiftUrlString(){
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        String puzzle = getUrlString("http://cswebcat.swansea.ac.uk/puzzle");
        String answer = "";
        int shift;
        System.out.println(puzzle);

        for (int i = 0; i <= puzzle.length(); i++){
            int position = letters.indexOf(puzzle.charAt(i));
            if ((i +1) % 2 == 1){
                shift = (position - (i+1) % letters.length());
                if (shift < 0) {
                    shift = shift + letters.length();
                }

            }
            else {
                 shift = (position + (i+1) % letters.length());
                 if (shift > letters.length()){
                     shift = shift- letters.length();
                 }

            }

            char newCharacter = letters.charAt(shift);
            answer += newCharacter;
            System.out.println(answer);

        }
        return answer.toString();
    }

    public static void main(String[] args) throws StringIndexOutOfBoundsException {
        System.out.println(shiftUrlString())



    }
}
