public class MatchResults {

    private Match match;
    private int homeTeamGoals;
    private int  awayTeamGoals;
    private String outcome ;

    public MatchResults(Match match, int homeTeamGoals, int awayTeamGoals) {
        this.match = match;
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;
        setOutcome();
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

    public void setOutcome() {
        if(homeTeamGoals > awayTeamGoals){
            outcome = "Home Win";
        } else if(awayTeamGoals > homeTeamGoals){
            outcome = "Away Win";
        } else{
            outcome = "Draw";
        }
    }

    @Override
    public String toString() {
        return this.outcome;
    }

}
