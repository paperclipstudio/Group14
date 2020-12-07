package FrontEnd;

import BackEnd.Leaderboard;
import BackEnd.Profile;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Screen that shows once someone has won the game.
 *
 * @author David Landmaid
 */
public class WinScreenController extends StateLoad {
	@FXML
	private Button returnButton;

	@FXML
	private Text winner;

	private WindowLoader wl;

	/**
	 * This initialises the page, displays the winners message and updates the leaderboards and profiles.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (getInitData() != null) {
			int playerCount = Integer.parseInt(getInitData().get("PlayerCount"));
			Profile[] profiles = new Profile[playerCount];
			for (int i = 0; i < playerCount; i++) {
				Profile current = null;
				try {
					current = Profile.readProfile(getInitData().get("Profile" + i));
				} catch (IOException e) {
					System.out.println("Profile" + getInitData().get("Profile" + i) + " Failed");
					System.exit(1);
				}
				profiles[i] = current;
			}


			int winnerNum = Integer.parseInt(getInitData().get("Winner"));
			winner.setText("Congratulations " + profiles[winnerNum].getName() + "!");
			Leaderboard leaderboard = null;
			String board = getInitData().get("Board");
			try {
				leaderboard = new Leaderboard(board);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			for (int i = 0; i < playerCount; i++) {
				if (i == winnerNum) {
					try {
						leaderboard.addWin(profiles[i].getName());
						profiles[i].incWins();
						profiles[i].incWinStreak();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					try {
						leaderboard.addLoss(profiles[i].getName());
						profiles[i].incLosses();
						profiles[i].resetWinStreak();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				Profile.saveAllProfiles(profiles);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns to main menu
	 */
	public void onReturnButton() {
		wl = new WindowLoader(returnButton);
		wl.load("MenuScreen", getInitData());
	}

	/**
	 * Returns to game setup
	 */
	public void onPlayAgainButton() {
		wl = new WindowLoader(returnButton);
		wl.load("GameSetup", getInitData());
	}
}
