package MaximeBoucher.JavaBlackJack;
public class BlackjackGame {
    private final Hand playerHand;
    private final Hand dealerHand;
    private final Deck deck;
    private double betAmount;
    private final double minBet;
    private final double maxBet;
    private static double totalMoney = 100;
    
	//Initialiser deck, playerHand, dealerHand, minBet et maxBet
	//le minimum et le maximum de la mise sont de 5 et 1000 respectivement.
    public BlackjackGame() {
    	
    	this.deck = new Deck();
    	this.playerHand = new Hand("player");
    	this.dealerHand = new Hand("dealer");
    	this.minBet = 5.0;
    	this.maxBet = 1000.0;
    	
    } 
    
    public void loadMoney() {
        totalMoney = 100;
    } 
    
	//retourne true le total d’argent dont un joueur dispose est inférieur au minimum de mise. False sinon.
    public boolean isOutOfMoney() {
    	if (totalMoney < this.minBet) {
    		return true;
    	}
    	return false;
    }

	// pour initialiser totalMoney a 100
    public void resetMoney() {
    	totalMoney = 100;
    }
    
	//retourne false si double localBetAmt est inférieur au minBet ou supérieur au maxBet ou supérieur au totalMoney. True sinon.
    public boolean isValidBet(double localBetAmt) {
		if (localBetAmt< minBet || localBetAmt > maxBet ||localBetAmt >getTotalMoney())
			return false;

		return true;
    }
    
	//retourner minBet
    public double getMinBet() {  
    	return minBet;
    }
    
	//retourner le montant total que le joeur peut l'utiliser pour la mise.
    public double getMaxBet() {
    	return maxBet;
    }
    
	// pour retrouner le montant total
    public double getTotalMoney() {    
    	return totalMoney;
    }
    
	//pour intialiser le montant de la mise qu'on va le faire
    public void setBet(double amt) {   
    	betAmount = amt;
    }
    
	// distribue deux cartes pour le joueur (playerHand) et deux cartes pour le croupier (dealerHand).
    public void deal() {
    	playerHand.addCard(deck.drawCard()); // card 1
    	playerHand.addCard(deck.drawCard()); // card 2
    	
    	dealerHand.addCard(deck.drawCard()); // card 1
    	dealerHand.addCard(deck.drawCard()); // card 2
    	
    }
    
	//pour distribuer une carte en plus pour le joueur dans le cas où il fait hit.
    public void hit() {
    	playerHand.addCard(deck.drawCard());
    }
    
	//qui ajoute des cartes au main du croupier tant que la somme des points dont il dispose est moins que 17.
    public void stand() {
    	if (dealerHand.getPoints() < 17)
    		dealerHand.addCard(deck.drawCard());
    }
    
	//retourne le deuxième carte dans la main du croupier.
    public Card getDealerShowCard() {
		return dealerHand.getCards()[1];
     }
    
	//retourne dealerHand
    public Hand getDealerHand() {
    	return this.dealerHand;
    }

	//retourne playerHand
    public Hand getPlayerHand() {
    	return this.playerHand;

    }
    
	// ice cream
    public boolean isBlackjackOrBust() {
        if(playerHand.isBlackjack() || playerHand.isBust() || dealerHand.isBlackjack() || dealerHand.isBust())
            return true;
        return false;
    }
    
	//retourne true si les points dans la main de joueur est inférieur ou égale 21 et ces points sont égales aux points avec le croupier. False sinon.
    public boolean isPush() {
		return (playerHand.getPoints() <= 21) && (playerHand.getPoints() == dealerHand.getPoints());
	}
    
	
	//retourne true si le player gagne. False sinon.
    public boolean playerWins() {
    	int playerPointsRest = 21 - playerHand.getPoints();
    	int dealerPointsRest = 21 - dealerHand.getPoints();
		return (!playerHand.isBust() && dealerPointsRest > playerPointsRest) || !playerHand.isBust() && dealerHand.isBust();
	}
    
	// ajoute le montant du mise gagner au montant total
    public void addBetToTotal() {
    	if (this.playerWins())
    		totalMoney += betAmount; 
    }
    
	// ajoute le montant de mise gagner selon 3:2 au montant total dans le cas de blackjack
    public void addBlackjackToTotal() {
    	if (playerHand.isBlackjack()) {
    		double moneyWon = betAmount * 1.5;
    		totalMoney += moneyWon;
    	}
    }
    
	// soustraire le montant du bet perdu du montant total
    public void subtractBetFromTotal() {
    	if (!this.playerWins()) {
    		totalMoney -= betAmount;
    	}
    }

}
