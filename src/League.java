import java.io.*;
import java.util.*;

public class League{
    private String leagueName;
    private List<Team> teams;
    private List<Match> fixtures;
    private List<MatchResults> matchResults;

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

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
    public void displayFixtures(){
            System.out.println("Fixtures:");
            for (int i = 0; i < fixtures.size(); i++) {
                System.out.println("Match " + (i + 1) + ": " + fixtures.get(i));
            }
    }

    public void simulateMatches(){
        matchResults.clear();

        for (Match match: fixtures){

            Team homeTeam = match.getHomeTeam();
            Team awayTeam = match.getAwayTeam();

            double homeTeamRating = homeTeam.calculateTeamRating();
            double awayTeamRating = awayTeam.calculateTeamRating();

            double totalTeamRating = homeTeamRating + awayTeamRating;
            double homeWinProbability = homeTeamRating/totalTeamRating;
            double drawProbability = (totalTeamRating - Math.abs(homeTeamRating - awayTeamRating)) / totalTeamRating;
            double result = Math.random();

            int homeGoals;
            int awayGoals;

            if (result < homeWinProbability) {
                // Home team wins
                homeGoals = (int) (Math.random() * 5); // Random number of goals (0-5)
                awayGoals = (int) (Math.random() * 4); // Random number of goals (0-4)
            } else if (result < homeWinProbability + drawProbability) {
                // Draw
                homeGoals = (int) (Math.random() * 3); // Random number of goals (0-3)
                awayGoals = (int) (Math.random() * 2); // Random number of goals (0-2)
            } else {
                // Away team wins
                homeGoals = (int) (Math.random() * 5); // Random number of goals (0-5)
                awayGoals = (int) (Math.random() * 4); // Random number of goals (0-4)
            }

            match.setHomeTeamGoals(homeGoals);
            match.setAwayTeamGoals(awayGoals);

            MatchResults matchResult = new MatchResults(match, homeGoals, awayGoals);
            matchResults.add(matchResult);
        }
    }

    public void displayResults(){

        System.out.println("Match Results:");
            for (int i = 0; i < fixtures.size(); i++) {
                Match match = fixtures.get(i);
                MatchResults mr = matchResults.get(i);
                System.out.println("Match " + (i + 1) + ": " + match.getHomeTeam().getTeamName() + " " + match.getHomeTeamGoals() + " - " + match.getAwayTeamGoals() + " " + match.getAwayTeam().getTeamName() + " " + mr);
            }

    }

    public void updateStandings() {
        // Reset goals scored and conceded for all teams
        for (Team team : teams) {
            team.setGoalsScored(0);
            team.setGoalsConceded(0);
            team.setPoints(0);
            team.setWin(0);
            team.setDraw(0);
            team.setLoss(0);
        }

        // Update goals scored and conceded based on match results
        for (MatchResults result : matchResults) {
            Team homeTeam = result.getMatch().getHomeTeam();
            Team awayTeam = result.getMatch().getAwayTeam();
            homeTeam.setGoalsScored(homeTeam.getGoalsScored() + result.getHomeTeamGoals());
            homeTeam.setGoalsConceded(homeTeam.getGoalsConceded() + result.getAwayTeamGoals());
            awayTeam.setGoalsScored(awayTeam.getGoalsScored() + result.getAwayTeamGoals());
            awayTeam.setGoalsConceded(awayTeam.getGoalsConceded() + result.getHomeTeamGoals());

            if (result.getHomeTeamGoals() > result.getAwayTeamGoals()){
                homeTeam.setPoints(homeTeam.getPoints() + 3);
                homeTeam.incrementWins();
                awayTeam.incrementLoss();
            } else if (result.getAwayTeamGoals() > result.getHomeTeamGoals()){
                awayTeam.setPoints(awayTeam.getPoints() + 3);
                awayTeam.incrementWins();
                homeTeam.incrementLoss();
            } else {
                homeTeam.setPoints(homeTeam.getPoints() + 1);
                awayTeam.setPoints(awayTeam.getPoints() + 1);
                homeTeam.incrementDraws();
                awayTeam.incrementDraws();
            }
            homeTeam.incrementMatchesPlayed();
            awayTeam.incrementMatchesPlayed();

        }

        // Sort teams based on points and goal difference
        Collections.sort(teams, new Comparator<Team>() {
            @Override
            public int compare(Team team1, Team team2) {
                int pointsCompare = Integer.compare(team2.getPoints(), team1.getPoints()); // Sort in descending order of points
                if (pointsCompare != 0) {
                    return pointsCompare;
                } else {
                    return Integer.compare(team2.goalDifference(), team1.goalDifference()); // Sort by goal difference
                }
            }
        });
    }

    public void displayStandings(){
        updateStandings();

        System.out.println("League Standings:");
        System.out.println("Team\tMatches Played\tWins\tDraw\tLoss\tPoints\tGoal Difference\tGoals Scored\tGoals Conceded");
        for (Team team : teams) {
            System.out.printf("%-15s%-15d%-6d%-6d%-6d%-8d%-16d%-14d%-16d%n",
                    team.getTeamName(), team.getMatchesPlayed(),team.getWin(),team.getDraw(),team.getLoss(),
                    team.getPoints(), team.goalDifference() ,team.getGoalsScored(),
                    team.getGoalsConceded());
        }
    }



    @Override
    public String toString() {
        return "League Name:" + leagueName + "\n" + "Teams: " + teams;
    }
}
