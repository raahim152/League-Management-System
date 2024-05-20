import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LeagueManager {
    private static Scanner scanner;
    private final List<League> leagues;

    public LeagueManager() {
        scanner = new Scanner(System.in);
        this.leagues = new ArrayList<>();
    }

    public void menu() {
        while (true) {
            if (doesLeagueExist()) {
                System.out.println("No leagues exist. You must add a league first.");
                addLeague();
            }

            System.out.println("\nMenu:");
            System.out.println("1. Add League");
            System.out.println("2. Edit League");
            System.out.println("3. Remove League");
            System.out.println("4. Manage Teams");
            System.out.println("5. Generate Fixtures");
            System.out.println("6. Simulate Matches");
            System.out.println("7. Display Standings");
            System.out.println("8. Display Fixtures");
            System.out.println("9. Display Results");
            System.out.println("10. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addLeague();
                    break;
                case 2:
                    editLeague();
                    break;
                case 3:
                    removeLeague();
                    break;
                case 4:
                    manageTeams();
                    break;
                case 5:
                    generateFixtures();
                    break;
                case 6:
                    simulateMatches();
                    break;
                case 7:
                    displayStandings();
                    break;
                case 8:
                    displayFixtures();
                    break;
                case 9:
                    displayResults();
                    break;
                case 10:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public boolean doesLeagueExist() {
        return leagues.isEmpty();
    }
    public void addLeague() {

        System.out.println("Enter League Name : ");
        String leagueName = scanner.nextLine();
        leagues.add(new League(leagueName));
        System.out.println("League Added: " + leagueName);
    }
    public void editLeague() {
        System.out.print("Enter league name to edit: ");
        String leagueName = scanner.nextLine();
        League league = findLeagueByName(leagueName);
        if (league != null) {
            System.out.print("Enter new league name: ");
            String newLeagueName = scanner.nextLine();
            league.setLeagueName(newLeagueName);
            System.out.println("League name updated to: " + newLeagueName);
        } else {
            System.out.println("League not found.");
        }
    }
    public void removeLeague() {
        System.out.print("Enter league name to remove: ");
        String leagueName = scanner.nextLine();
        League league = findLeagueByName(leagueName);
        if (league != null) {
            leagues.remove(league);
            System.out.println("League removed: " + leagueName);
        } else {
            System.out.println("League not found.");
        }
    }
    public void manageTeams() {
        System.out.print("Enter league name to manage teams: ");
        String leagueName = scanner.nextLine();
        League league = findLeagueByName(leagueName);
        if (league != null) {
            manageTeamsMenu(league);
        } else {
            System.out.println("League not found.");
        }
    }
    public void manageTeamsMenu(League league) {
        while (true) {
            System.out.println("\nManage Teams in " + league.getLeagueName() + ":");
            System.out.println("1. Add Team");
            System.out.println("2. Remove Team");
            System.out.println("3. Add Player to Team");
            System.out.println("4. Remove Player from Team");
            System.out.println("5. Back to Main Menu");
            System.out.println("6. Edit Player");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addTeam(league);
                    break;
                case 2:
                    removeTeam(league);
                    break;
                case 3:
                    addPlayerToTeam(league);
                    break;
                case 4:
                    removePlayerFromTeam(league);
                    break;
                case 5:
                    return;
                case 6:
                    editPlayer(league);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    public void addTeam(League league) {
        if (league.getTeams().size() >= 20) {
            System.out.println("Cannot add more teams. Maximum limit reached.");
            return;
        }
        System.out.println("Enter Team Name: ");
        String teamName = scanner.nextLine();
        Team team = new Team(teamName);
        league.addTeam(team);
        System.out.println("Team added: " + teamName);
    }
    public void removeTeam(League league) {
        if (league.getTeams().size() <= 2) {
            System.out.println("Cannot remove teams. Minimum limit reached.");
            return;
        }
        System.out.print("Enter team name to remove: ");
        String teamName = scanner.nextLine();
        Team team = findTeamByName(league, teamName);
        if (team != null) {
            league.removeTeam(team);
            System.out.println("Team removed: " + teamName);
        } else {
            System.out.println("Team not found.");
        }
    }
    public void addPlayerToTeam(League league) {
        System.out.print("Enter team name: ");
        String teamName = scanner.nextLine();
        Team team = findTeamByName(league, teamName);
        if (team != null) {
            if (team.getPlayers().size() >= 3) {
                System.out.println("Cannot add more players. Maximum limit reached.");
                return;
            }
            System.out.print("Enter player name: ");
            String playerName = scanner.nextLine();
            System.out.println("Enter Player Position");
            String playerPosition = scanner.nextLine();
            System.out.print("Enter player rating: ");
            double playerRating = scanner.nextDouble();
            scanner.nextLine();

            Player player = new Player(playerName, playerPosition, playerRating);
            team.addPlayer(player);
            System.out.println("Player added: " + playerName);
        } else {
            System.out.println("Team not found.");
        }
    }
    public void editPlayer(League league) {
        System.out.print("Enter team name: ");
        String teamName = scanner.nextLine();
        Team team = findTeamByName(league, teamName);
        if (team != null) {
            System.out.print("Enter player name to edit: ");
            String playerName = scanner.nextLine();
            Player player = findPlayerByName(team, playerName);
            if (player != null) {
                System.out.print("Enter new rating for player: ");
                double newRating = scanner.nextDouble();
                scanner.nextLine(); // consume newline
                player.setRating(newRating);
                System.out.println("Player rating updated: " + playerName + " - " + newRating);
            } else {
                System.out.println("Player not found.");
            }
        } else {
            System.out.println("Team not found.");
        }
    }
    public void removePlayerFromTeam(League league) {
        System.out.print("Enter team name: ");
        String teamName = scanner.nextLine();
        Team team = findTeamByName(league, teamName);
        if (team != null) {
            if (team.getPlayers().size() <= 11) {
                System.out.println("Cannot remove players. Minimum limit reached.");
                return;
            }
            System.out.print("Enter player name to remove: ");
            String playerName = scanner.nextLine();
            Player player = findPlayerByName(team, playerName);
            if (player != null) {
                team.removePlayer(player);
                System.out.println("Player removed: " + playerName);
            } else {
                System.out.println("Player not found.");
            }
        } else {
            System.out.println("Team not found.");
        }
    }
    public League findLeagueByName(String leagueName) {
        for (League league : leagues) {
            if (league.getLeagueName().equalsIgnoreCase(leagueName)) {
                return league;
            }
        }
        return null;
    }
    public Team findTeamByName(League league, String teamName) {
        for (Team team : league.getTeams()) {
            if (team.getTeamName().equalsIgnoreCase(teamName)) {
                return team;
            }
        }
        return null;
    }
    public Player findPlayerByName(Team team, String playerName) {
        for (Player player : team.getPlayers()) {
            if (player.getName().equalsIgnoreCase(playerName)) {
                return player;
            }
        }
        return null;
    }
    public void generateFixtures() {
        if (doesLeagueExist()) {
            System.out.println("No leagues exist. Add a league first.");
            return;
        }
        System.out.print("Enter league name to generate fixtures: ");
        String leagueName = scanner.nextLine();
        League league = findLeagueByName(leagueName);
        if (league != null) {
            league.generateFixtures();
            System.out.println("Fixtures generated.");
        } else {
            System.out.println("League not found.");
        }
    }
    public void simulateMatches() {
        if (doesLeagueExist()) {
            System.out.println("No leagues exist. Add a league first.");
            return;
        }
        System.out.print("Enter league name to simulate matches: ");
        String leagueName = scanner.nextLine();
        League league = findLeagueByName(leagueName);
        if (league != null) {
            league.simulateMatches();
            System.out.println("Matches simulated.");
        } else {
            System.out.println("League not found.");
        }
    }
    public void displayStandings() {
        if (doesLeagueExist()) {
            System.out.println("No leagues exist. Add a league first.");
            return;
        }
        System.out.print("Enter league name to display standings: ");
        String leagueName = scanner.nextLine();
        League league = findLeagueByName(leagueName);
        if (league != null) {
            league.displayStandings();
        } else {
            System.out.println("League not found.");
        }
    }
    public void displayFixtures() {
        if (doesLeagueExist()) {
            System.out.println("No leagues exist. Add a league first.");
            return;
        }
        System.out.print("Enter league name to display fixtures: ");
        String leagueName = scanner.nextLine();
        League league = findLeagueByName(leagueName);
        if (league != null) {
            league.displayFixtures();
        } else {
            System.out.println("League not found.");
        }
    }
    public void displayResults() {
        if (doesLeagueExist()) {
            System.out.println("No leagues exist. Add a league first.");
            return;
        }
        System.out.print("Enter league name to display results: ");
        String leagueName = scanner.nextLine();
        League league = findLeagueByName(leagueName);
        if (league != null) {
            league.displayResults();
        } else {
            System.out.println("League not found.");
        }
    }
}