package MessageOfTheDay;
import java.io.*;
import java.net.*;

public class MOTD {
	private static final int A_NUM = (int) 'A';
	private static final int Z_NUM = (int) 'Z';


	public static String readInMOTD() throws Exception {
		URL motdURL = new URL("http://cswebcat.swansea.ac.uk/puzzle");
		URLConnection conn = motdURL.openConnection();
		InputStream message = conn.getInputStream();
		BufferedReader readIn = new BufferedReader(new InputStreamReader(message));
		String puzzle = readIn.readLine();
		readIn.close();
		return (puzzle);
	}

	public static String processMOTD(String MOTD) {
		char[] puzzle = MOTD.toCharArray();
		for(int i = 0; i < MOTD.length(); i++) {
			int charNum;
			// apply shifting.
			if (i % 2 == 0) {
				charNum = puzzle[i] - i - 1;
			} else {
				charNum = puzzle[i] + i + 1;
			}
			// bring back in range of A-Z
			while (charNum < A_NUM) {
				charNum += 26;
			}
			while (charNum > Z_NUM) {
				charNum -= 26;
			}
			puzzle[i] = (char) charNum;
		}
		String result = "CS-230" + String.valueOf(puzzle);
		return result + result.length();
	}

	public static String confirmMOTD(String MOTD) throws IOException {
		StringBuilder output = new StringBuilder();
		System.out.println("http://cswebcat.swansea.ac.uk/message?solution=" + MOTD);
		URL solutionCheckURL = new URL("http://cswebcat.swansea.ac.uk/message?solution=" + MOTD);
		URLConnection conn = solutionCheckURL.openConnection();
		InputStream message = conn.getInputStream();
		BufferedReader readIn = new BufferedReader(new InputStreamReader(message));
		output.append(readIn.readLine());
		readIn.close();
		return (output.toString());
	}

	public static String puzzle() throws Exception {
		String toSolve = readInMOTD();
		String solved = processMOTD(toSolve);
		String message = confirmMOTD(solved);
		return message;
	}

}

