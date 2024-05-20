import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class LeagueApplication extends Application {

    private Stage primaryStage;
    private TextField leagueNameField;
    private List<League> leagues;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("League Manager");

        leagues = new ArrayList<>();

        Label titleLabel = new Label("Welcome to League Manager");
        Label leagueNameLabel = new Label("League Name:");
        leagueNameField = new TextField();
        Button addLeagueButton = new Button("Add League");

        VBox addLeagueLayout = new VBox(10);
        addLeagueLayout.setPadding(new Insets(20));
        addLeagueLayout.getChildren().addAll(titleLabel, leagueNameLabel, leagueNameField, addLeagueButton);

        addLeagueButton.setOnAction(e -> addLeague());

        Scene addLeagueScene = new Scene(addLeagueLayout, 300, 200);
        primaryStage.setScene(addLeagueScene);
        primaryStage.show();
    }
    private void addLeague() {
        String leagueName = leagueNameField.getText();
        if (!leagueName.isEmpty()) {
            League league = new League(leagueName);
            leagues.add(league);
            System.out.println("League added: " + leagueName);
            leagueNameField.clear();

            showLeagueMenu(league);
        } else {
            System.out.println("Please enter a league name.");
        }
    }

    private void showLeagueMenu(League league) {

        Label titleLabel = new Label("Menu for " + league.getLeagueName());
        Button addTeamButton = new Button("Add Team");
        Button removeTeamButton = new Button("Remove Team");
        Button editTeamButton = new Button("Edit Team");
        Button addPlayerButton = new Button("Add Player");
        Button removePlayerButton = new Button("Remove Player");
        Button editPlayerButton = new Button("Edit Player");
        Button generateFixturesButton = new Button("Generate Fixtures");
        Button displayFixturesButton = new Button("Display Fixtures");
        Button displayStandingsButton = new Button("Display Standings");


        VBox menuLayout = new VBox(10);
        menuLayout.setPadding(new Insets(20));
        menuLayout.getChildren().addAll(titleLabel, addTeamButton, removeTeamButton, editTeamButton,
                addPlayerButton, removePlayerButton, editPlayerButton, generateFixturesButton,
                displayFixturesButton, displayStandingsButton);

        addTeamButton.setOnAction(e -> addTeam(league));
        removeTeamButton.setOnAction(e -> removeTeam(league));
        editTeamButton.setOnAction(e -> editTeam(league));
        addPlayerButton.setOnAction(e -> addPlayer(league));
        removePlayerButton.setOnAction(e -> removePlayer(league));
        editPlayerButton.setOnAction(e -> editPlayer(league));
        generateFixturesButton.setOnAction(e -> generateFixtures(league));
        displayFixturesButton.setOnAction(e -> displayFixtures(league));
        displayStandingsButton.setOnAction(e -> displayStandings(league));

        Scene menuScene = new Scene(menuLayout, 300, 400);
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    private void addTeam(League league) {

        Label titleLabel = new Label("Add Team to " + league.getLeagueName());
        Label teamNameLabel = new Label("Team Name:");
        TextField teamNameField = new TextField();
        Button addButton = new Button("Add Team");
        Button returnButton = new Button("Return to Menu");

        VBox addTeamLayout = new VBox(10);
        addTeamLayout.setPadding(new Insets(20));
        addTeamLayout.getChildren().addAll(titleLabel, teamNameLabel, teamNameField, addButton, returnButton);

        addButton.setOnAction(e -> {
            String teamName = teamNameField.getText();
            if (!teamName.isEmpty()) {
                Team team = new Team(teamName);
                league.addTeam(team);
                Label label = new Label("Team Added!");
                addTeamLayout.getChildren().addAll(label);
                teamNameField.clear();
            } else {
                System.out.println("Please enter a team name.");
            }
        });

        returnButton.setOnAction(e -> showLeagueMenu(league));

        Scene addTeamScene = new Scene(addTeamLayout, 300, 200);
        primaryStage.setScene(addTeamScene);
        primaryStage.show();
    }

    private void removeTeam(League league) {
        // Implementation for removing a team
    }

    private void editTeam(League league) {
        // Implementation for editing a team
    }

    private void addPlayer(League league) {
        // Implementation for adding a player
    }

    private void removePlayer(League league) {
        // Implementation for removing a player
    }

    private void editPlayer(League league) {
        // Implementation for editing a player
    }

    private void generateFixtures(League league) {
        // Implementation for generating fixtures
    }

    private void displayFixtures(League league) {
        // Implementation for displaying fixtures
    }

    private void displayStandings(League league) {

        TableView<Team> standingsTable = new TableView<>();
        TableColumn<Team, String> teamColumn = new TableColumn<>("Team");
        teamColumn.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        TableColumn<Team, Integer> matchesPlayedColumn = new TableColumn<>("Matches Played");
        matchesPlayedColumn.setCellValueFactory(new PropertyValueFactory<>("matchesPlayed"));
        TableColumn<Team, Integer> winsColumn = new TableColumn<>("Wins");
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("win"));
        TableColumn<Team, Integer> drawsColumn = new TableColumn<>("Draws");
        drawsColumn.setCellValueFactory(new PropertyValueFactory<>("draw"));
        TableColumn<Team, Integer> lossesColumn = new TableColumn<>("Losses");
        lossesColumn.setCellValueFactory(new PropertyValueFactory<>("loss"));
        TableColumn<Team, Integer> pointsColumn = new TableColumn<>("Points");
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        TableColumn<Team, Integer> goalDifferenceColumn = new TableColumn<>("Goal Difference");
        goalDifferenceColumn.setCellValueFactory(new PropertyValueFactory<>("goalDifference"));
        TableColumn<Team, Integer> goalsScoredColumn = new TableColumn<>("Goals Scored");
        goalsScoredColumn.setCellValueFactory(new PropertyValueFactory<>("goalsScored"));
        TableColumn<Team, Integer> goalsConcededColumn = new TableColumn<>("Goals Conceded");
        goalsConcededColumn.setCellValueFactory(new PropertyValueFactory<>("goalsConceded"));

        standingsTable.getColumns().addAll(teamColumn, matchesPlayedColumn, winsColumn, drawsColumn,
                lossesColumn, pointsColumn, goalDifferenceColumn,
                goalsScoredColumn, goalsConcededColumn);

        ObservableList<Team> teamData = FXCollections.observableArrayList(league.getTeams());
        standingsTable.setItems(teamData);

        Tab standingsTab = new Tab("Standings");
        standingsTab.setContent(standingsTable);

        Button returnButton = new Button("Return to Menu");
        returnButton.setOnAction(e -> showLeagueMenu(league));

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(standingsTab.getContent());
        borderPane.setBottom(returnButton);

        Scene standingsScene = new Scene(borderPane, 800, 600);
        Stage standingsStage = new Stage();
        standingsStage.setScene(standingsScene);
        standingsStage.setTitle("League Standings");
        standingsStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

