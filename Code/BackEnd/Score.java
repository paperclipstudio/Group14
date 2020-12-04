package BackEnd;

/**
 * Stores the wins/loss for one profile
 *
 * @author Christian Sanger
 */
public class Score {

    /*
     * These variables hold information about the player wins and losses.
     */
    private final String name;
    private final int wins;
    private final int loss;

    /**
     * Creates a new Score.
     *
     * @param name name of profile this score is for
     * @param wins number of wins this player has had on this board
     * @param loss number of losses this player has had on this board
     */
    public Score(String name, int wins, int loss) {
        this.name = name;
        this.wins = wins;
        this.loss = loss;
    }

    /**
     * @return The number of wins this profile has had on this board
     */
    public int getWins() {
        return wins;
    }

    /**
     * @return The number of losses this profile has had on this board
     */
    public int getLoss() {
        return loss;
    }

    /**
     * @return the profile name of this score
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName() + " " + getWins() + " " + getLoss();
    }
}
