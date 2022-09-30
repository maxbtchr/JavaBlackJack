package MaximeBoucher.JavaBlackJack;
public class Deck {
    private Card[] deck;
    private int currentCardIndex;
    
	//stocke les cartes dans  Card[ ] deck et ensuite il appelle la fonction shuflleDeck(). 
    public Deck() {
    	deck = new Card[52];
    	String[] allRanks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    	String[] allSuites = {"Spades", "Hearts", "Clubs", "Diamonds"};
    	int counter = 0;
    	for(String suite: allSuites) {
    		for(String rank: allRanks) {
    			int points;
    			switch(rank) {
	    			case "Jack": case "Queen": case "King": points =  10;
	    			break;
	    			case "Ace": points = 11;
	    			break;
	    			default: points = Integer.parseInt(rank);
	    			break;
    			}
    			deck[counter] = new Card(suite, rank, points);
    			counter++;
    		}
    		shuffleDeck();
    	}
    }
    
	//shuffleDeck, pour mélanger les cartes à l'aide de l'algorithme de mélange de Fisher-Yates:
	//https://www.geeksforgeeks.org/shuffle-a-given-array-using-fisher-yates-shuffle-algorithm/
    private void shuffleDeck() {
    	for (int i = 51; i > 0; i--) {
    		int j = (int) (Math.random()*52);
    		Card temp = deck[i];
    		deck[i] = deck[j];
    		deck[j] = temp;
    	}
    }
    
    public Card drawCard() {
        if(currentCardIndex == 51) {
            Card currCard = deck[currentCardIndex];
            shuffleDeck();
            return currCard;
        }
        else {
        	while (deck[currentCardIndex] == null) {
        		currentCardIndex++;
        	}
            Card currCard = deck[currentCardIndex];
        	currentCardIndex++;
            return currCard;        
        }
    }

}
