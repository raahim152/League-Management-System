import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        List<Player> team1Players = new ArrayList<>();
        for (int i = 1; i <= 11; i++) {
            Player player = new Player("Player " + i, "OnField", 60);
            team1Players.add(player);
        }
        Team team1 = new Team("Team 1");
        team1.setPlayers(team1Players);
        System.out.println(team1.calculateTeamRating());


        List<Player> team2Players = new ArrayList<>();
        for (int i = 1; i <= 11; i++) {
            Player player = new Player("Player " + (i + 11), "OnField", 60);
            team2Players.add(player);
        }
        Team team2 = new Team("Team 2");
        team2.setPlayers(team2Players);
        System.out.println(team2.calculateTeamRating());


        League premierLeague = new League("Premier League");


        for (int i = 1; i <= 2; i++) {
            premierLeague.addTeam(new Team("Team " + i));
        }

        System.out.println(premierLeague);
        premierLeague.generateFixtures();
        premierLeague.displayfixtures();
        premierLeague.simulateMatches();
        premierLeague.displayResults();
}
    }
