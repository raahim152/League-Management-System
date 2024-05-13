public class MatchResults {

    private Match match;
    private int homeTeamGoals;
    private int  awayTeamGoals;
    private static String outcome;

    public MatchResults(int homeTeamGoals, int awayTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;

    }

    public Match getMatch() {
        return match;
    }

    public int getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public int getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public String getOutcome() {

        return outcome;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setHomeTeamGoals(int homeTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
    }

    public void setAwayTeamGoals(int awayTeamGoals) {
        this.awayTeamGoals = awayTeamGoals;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    @Override
    public String toString() {
        return match.getHomeTeam().getTeamName() + " " + homeTeamGoals + " - " + awayTeamGoals + " " + match.getAwayTeam().getTeamName() + " " + outcome;
    }

}
