package MessageOfTheDay;
import java.io.*;
import java.net.*;


/**
 * This class is used for getting the message of the day.
 *
 * @author Christian Sanger
 */
public class MessageOfTheDay {
	private static final int A_NUM = 'A';
	private static final int Z_NUM = 'Z';

	/**
	 * Reads in the MoTD string
	 * @return the string that need to be converted
	 * @throws Exception if URL can't be accessed
	 */
	private static String readInMOTD() throws Exception {
		URL motdURL = new URL("http://cswebcat.swansea.ac.uk/puzzle");
		URLConnection conn = motdURL.openConnection();
		InputStream message = conn.getInputStream();
		BufferedReader readIn = new BufferedReader(new InputStreamReader(message));
		String puzzle = readIn.readLine();
		readIn.close();
		return (puzzle);
	}

	/**
	 * Solves the puzzle
	 * @param Message the encrypted message
	 * @return the unencrypted message
	 */
	private static String processMOTD(String Message) {
		char[] puzzle = Message.toCharArray();
		for(int i = 0; i < Message.length(); i++) {
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

	/**
	 * Uses unencrypted message to get final message from server
	 * @param MOTD unencrypted message
	 * @return final Message of the day.
	 * @throws IOException if URL or message incorrect.
	 */
	private static String confirmMOTD(String MOTD) throws IOException {
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

	/**
	 * Reads the message of the day from the server
	 * @return message from server
	 * @throws Exception if error in solving puzzle
	 */
	public static String puzzle() throws Exception {
		String toSolve = readInMOTD();
		String solved = processMOTD(toSolve);
		return confirmMOTD(solved);
	}

}

