package mslogic;

/**
 * Created on 1/17/2017, 2:03 PM
 *
 * @author Noah Morton
 *         Tully 7th period
 *         Part of project MinesweeperProject
 */

public class ScoreEntry implements Comparable {
    private String username;
    private int score;

    public ScoreEntry(String username, int score) {
        this.score = score;
        this.username = username;
    }

    @Override
    public String toString() {
        return username + "-" + score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(Object o) {
        return (this.score < (int) o) ? -1 : ((this.score == (int) o) ? 0 : 1);
    }
}
