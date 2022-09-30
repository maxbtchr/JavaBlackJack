package MaximeBoucher.JavaBlackJack;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BlackjackApp extends Application{
    
	private static BlackjackGame game = new BlackjackGame();
	
	private static double betAmount;
	
	private static TextField moneyField;
	private static TextField betField;
	private static TextField playerPointsField;
	private static TextField dealerPointsField;
	private static TextField resultField;
	
	private static ObservableList<String> dealerObsList;
	private static ObservableList<String> playerObsList;
	private static ListView<String> dealerListView;
	private static ListView<String> playerListView;
	
	private static Button hitButton;
	private static Button standButton;
	private static  Button playButton;
	private static Button exitButton;
	
	private static String resultDisplay = "";
	private static boolean appRunning = false;

    public static void main(String[] args) {
    	
    	launch();
    	
    }
    
	@Override
	public void start(Stage primaryStage) {
		
		primaryStage.setTitle("Blackjack!"); 	// Titre
		
		GridPane grid = new GridPane(); 	// Initializer grid
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setHgap(10);
		grid.setVgap(10);
		
		VBox vbox = new VBox(10); 		// Itinializer vbox
		
		Label moneyLabel = new Label("Money:"); 		// initializer les champs
		Label betLabel = new Label("Bet:");
		Label dealerLabel = new Label("DEALER");
		Label dealerCardsLabel = new Label("Cards:");
		Label dealerPointsLabel = new Label("Points:");
		Label playerLabel = new Label("YOU");
		Label playerCardsLabel = new Label("Cards:");
		Label playerPointsLabel = new Label("Points:");
		Label resultLabel = new Label("RESULT:");
		
		Label[] labels = {
							moneyLabel,
							betLabel,
							dealerLabel,
							dealerCardsLabel,
							dealerPointsLabel,
							playerLabel,
							playerCardsLabel,
							playerPointsLabel,
							resultLabel
						 };
		
		for(Label label: labels) { 		// setter largeur des champs
			label.setPrefWidth(50);
			label.setMaxWidth(50);
		}
		
		dealerObsList = FXCollections.observableArrayList(); 	// declarer les lists
		playerObsList = FXCollections.observableArrayList();
		dealerListView = new ListView<>(dealerObsList);
		playerListView = new ListView<>(playerObsList);
		
		moneyField = new TextField(); 		// declarer le champs money et y mettre la valeur totalMoney
		moneyField.setEditable(false);
		moneyField.setText(Double.toString(showMoney()));
		
		betField = new TextField();

		dealerPointsField = new TextField();
		
		playerPointsField = new TextField();
		
		hitButton = new Button("Hit");		 // declarer les boutons et les evenements
		hitButton.setDisable(true);
		hitButton.setOnAction(e-> hitButtonClick());
		standButton = new Button("Stand");
		standButton.setDisable(true);
		standButton.setOnAction(e-> standButtonClick());
		
		resultField = new TextField(); 		// declarer le champ resultat et setter sa largeur
		resultField.setPrefWidth(250);
		resultField.setMaxWidth(250);
		
		playButton = new Button("Play"); 		// declarer les boutons play et exit et leurs evenements
		playButton.setOnAction(e-> playButtonClick());
		exitButton = new Button("Exit");
		exitButton.setOnAction(e-> System.exit(0));
		
		HBox moneyBox = new HBox(5); 		// creer les hbox et leur donner des enfants
		moneyBox.getChildren().addAll(moneyLabel, moneyField);
		HBox betBox = new HBox(5);
		betBox.getChildren().addAll(betLabel, betField);
		HBox dealerCardsBox = new HBox(5);
		dealerCardsBox.getChildren().addAll(dealerCardsLabel, dealerListView);
		HBox dealerPointsBox = new HBox(5);
		dealerPointsBox.getChildren().addAll(dealerPointsLabel, dealerPointsField);
		HBox playerCardsBox = new HBox(5);
		playerCardsBox.getChildren().addAll(playerCardsLabel, playerListView);
		HBox playerPointsBox = new HBox(5);
		playerPointsBox.getChildren().addAll(playerPointsLabel, playerPointsField);
		HBox hitStandBox = new HBox(10);
		hitStandBox.getChildren().addAll(hitButton, standButton);
		HBox resultBox = new HBox(5);
		resultBox.getChildren().addAll(resultLabel, resultField);
		HBox playExitBox = new HBox(10);
		playExitBox.getChildren().addAll(playButton, exitButton);
		
		vbox.getChildren().addAll(			// mettre les hbox dans la vbox
										moneyBox, 
										betBox, 
										dealerLabel,
										dealerCardsBox, 
										dealerPointsBox,
										playerLabel,
								  		playerCardsBox, 
								  		playerPointsBox, 
								  		hitStandBox, 
								  		resultBox, 
								  		playExitBox
								  );
		
		grid.add(vbox, 0, 0); 		// ajouter vbox à la grid
	
		Scene scene = new Scene(grid, 350, 600); 		// initializer scene
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	// pour vider les champs texte
	private static void emptyFields() { 
    	resultField.clear();
		dealerObsList.clear();
		playerObsList.clear();
		dealerPointsField.clear();
		playerPointsField.clear();
	}
	
	// pour activer / désactiver les boutons et le champ bet
	private static void setDisable() { 		
		if (appRunning) {		
			playButton.setDisable(true);
			exitButton.setDisable(true);
			hitButton.setDisable(false);
			standButton.setDisable(false);
		} else {
			playButton.setDisable(false);
			exitButton.setDisable(false);
			hitButton.setDisable(true);
			standButton.setDisable(true);
		}
		betField.setDisable(appRunning);
	}
	
	// pour stand et montrer le gagnant
	private void standButtonClick() { 		
		game.stand();
    	showWinner();
	}

	// pour hit et montrer le gagnant
	private void hitButtonClick() {	
		game.hit();
    	showWinner();
	}

	// l'app débute si le montant du bet est correct. Si pas 1ere partie, les champs se vident. l'argent est remis à 100 si à 0.
	private void playButtonClick() {	
		if (game.isOutOfMoney()) {
			game.resetMoney();
			moneyField.setText(Double.toString(showMoney()));
		}
		if (validateBetAmount()) {
			appRunning = true;
			setDisable();
			if (!resultDisplay.isEmpty()) {
				emptyFields();
				game = new BlackjackGame();
			}
			game.deal();
			showDealerShowCard();
			showPlayerHand();
			playerPointsField.setText(Double.toString(game.getPlayerHand().getPoints()));
		}
	}

	// renvoie true si le bet du joueur est parsable to double. sinon, un alert apparaît.
	private static boolean validateBetAmount() {	
    	try {
    		betAmount = Double.parseDouble(betField.getText());
    		if (game.isValidBet(betAmount)) {
    			game.setBet(betAmount);
    		}
    		else {
    			Alert alert = new Alert(Alert.AlertType.ERROR);
    			alert.setTitle("Error box");
    			alert.setHeaderText("Invalid entry");
    			alert.setContentText("Bet amount must be between $5 and $1000, and you cannot bet more than what you have!");
    			alert.showAndWait();
    			return false;	
    		}
    	} catch (NumberFormatException e){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error box");
			alert.setHeaderText("Invalid entry");
			alert.setContentText("Please enter a valid bet!");
			alert.showAndWait();
			return false;
    	}
    	return true;
		
	}
   
	// affiche le deuxieme carte dans la main du croupier
    private static void showDealerShowCard() {
    	
    	dealerObsList.add(game.getDealerShowCard().display());
    	
    }
    
	// affiche les cartes dans la main du croupier
    private static void showDealerHand() {
    	for(Card card: game.getDealerHand().getCards()) {
    		if (card != null)
    			if (!dealerObsList.contains(card.display()))
    				dealerObsList.add(card.display());
    	}
    }
    
	// affiche les cartes dans la main du joueur
    private static void showPlayerHand() {
    	for(Card card: game.getPlayerHand().getCards()) {
    		if (card != null)
    			if (!playerObsList.contains(card.display()))
    				playerObsList.add(card.display());
    	}
    	
    }
    
	// affiche le montant total
    private static double showMoney() {
    	return game.getTotalMoney();
    }
    
    // affiche le total des points, fait et affiche le calcul
    private static void showWinner() {
        showPlayerHand();
        playerPointsField.setText(Integer.toString(game.getPlayerHand().getPoints()));
        
        showDealerHand();
        dealerPointsField.setText(Integer.toString(game.getDealerHand().getPoints()));
        
        resultDisplay = "";
        game.setBet(betAmount);
        if(game.isPush()) { 
        	resultDisplay = "It's a tie!";
        	resultField.setText(resultDisplay);
        } else if(game.playerWins() && game.getPlayerHand().isBlackjack()) {
        	resultDisplay = "BLACKJACK! You win!";
        	resultField.setText(resultDisplay);
            game.addBlackjackToTotal();
        } else if (game.playerWins()) {
        	resultDisplay = "You win!";
        	resultField.setText(resultDisplay);
            game.addBetToTotal();
        } else {
        	resultDisplay = "Sorry, you lose.";
        	resultField.setText("Sorry, you lose.");
            game.subtractBetFromTotal();
        }
        if (game.isBlackjackOrBust()) 
        	resultField.setText(resultDisplay + " Go get some ice cream!");
        
        moneyField.setText(Double.toString(showMoney()));
        appRunning = false;
        setDisable();
  
    }

}
