import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class League {
    private String leagueName;
    private List<Team> teams;
    private List<Match> fixtures;
    private List<MatchResults> matchResults;

    public League(String leagueName) {
        this.leagueName = leagueName;
        this.teams = new ArrayList<>();
        this.fixtures = new ArrayList<>();
        this.matchResults = new ArrayList<>();
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public void removeTeam(Team team) {
        teams.remove(team);
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void generateFixtures() {
        fixtures.clear();//ie for every new season

        int numTeams = teams.size();
        List<Team> shuffledTeams = new ArrayList<>(teams);
        Collections.shuffle(shuffledTeams);//to shuffle teams accordingly.

        // Create a copy of shuffledTeams to ensure every team plays every other team at least once
        List<Team> tempTeams = new ArrayList<>(shuffledTeams);

        for (int i = 0; i < numTeams; i++) {
            for (int j = i + 1; j < numTeams; j++) {
                Team homeTeam = tempTeams.get(i);
                Team awayTeam = tempTeams.get(j);
                Match match = new Match(homeTeam, awayTeam);
                fixtures.add(match);
            }
        }

        // If the number of teams is odd, add a dummy team to make the number even
        if (numTeams % 2 != 0) {
            tempTeams.add(null);
            numTeams++;
        }

        // Repeat the process to ensure every team plays every other team at most twice
        for (int round = 0; round < numTeams - 1; round++) {
            for (int i = 0; i < numTeams / 2; i++) {
                Team homeTeam = tempTeams.get(i);
                Team awayTeam = tempTeams.get(numTeams - 1 - i);

                // Skip if both teams are null (dummy team)
                if (homeTeam != null && awayTeam != null) {
                    Match match = new Match(homeTeam, awayTeam);
                    fixtures.add(match);
                }
            }

            // Rotate teams to create new matches for the next round
            Collections.rotate(tempTeams.subList(1, tempTeams.size()), 1);

        }
    }
    public void displayfixtures(){
            System.out.println("Fixtures:");
            for (int i = 0; i < fixtures.size(); i++) {
                System.out.println("Match " + (i + 1) + ": " + fixtures.get(i));
            }
    }


    public void simulateMatches(){
        for (Match match: fixtures){
            Team homeTeam = match.getHomeTeam();
            Team awayTeam = match.getAwayTeam();

            double homeTeamRating = homeTeam.calculateTeamRating();
            double awayTeamRating = awayTeam.calculateTeamRating();

            double totalTeamRating = homeTeamRating + awayTeamRating;
            double homeWinProbability = homeTeamRating/totalTeamRating;
            double drawProbability = (totalTeamRating - Math.abs(homeTeamRating - awayTeamRating)) / totalTeamRating;
            double result = Math.random();

            if (result < homeWinProbability) {
                // Home team wins
                int homeGoals = (int) (Math.random() * 4); // Random number of goals (0-3)
                int awayGoals = (int) (Math.random() * 3); // Random number of goals (0-2)
                match.setResult(homeGoals, awayGoals);
            } else if (result < homeWinProbability + drawProbability) {
                // Draw
                int homeGoals = (int) (Math.random() * 3); // Random number of goals (0-2)
                int awayGoals = (int) (Math.random() * 3); // Random number of goals (0-2)
                match.setResult(homeGoals, awayGoals);
            } else {
                // Away team wins
                int homeGoals = (int) (Math.random() * 3); // Random number of goals (0-2)
                int awayGoals = (int) (Math.random() * 4); // Random number of goals (0-3)
                match.setResult(homeGoals, awayGoals);
            }
        }
    }

    public void displayResults(){

        System.out.println("Match Results:");
            for (int i = 0; i < fixtures.size(); i++) {
                Match match = fixtures.get(i);

                System.out.println("Match " + (i + 1) + ": " + match.getHomeTeam().getTeamName() + " " + match.getHomeTeamGoals() + " - " + match.getAwayTeamGoals() + " " + match.getAwayTeam().getTeamName()) ;
            }

    }

    @Override
    public String toString() {
        return "League Name:" + leagueName + "\n" + "Teams: " + teams;
    }
}
