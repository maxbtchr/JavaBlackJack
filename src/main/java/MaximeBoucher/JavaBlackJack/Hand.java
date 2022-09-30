package MaximeBoucher.JavaBlackJack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Hand{
    private Card [] hand;
    private String user; //Soit le joueur soit le croupier. Selon comment on appelle le constructeur et le parametre lorsque utilisé
    private int cardNumber;
    
    public Hand(String user) {
    	this.user = user;
    	this.cardNumber = 0;
    	this.hand = new Card[10];
    }
    
	//retroune le tableau hand
    public Card[] getCards() {
    	
    	return this.hand;
    	
    }
    
	// retourne la somme des cartes dans le tableau hand. Si la somme est >21, il faut recompter les cartes pour verifier s'il y a un as. Si oui on le considere comme 1, sinon on ajoute la somme des points
    public int getPoints() {

    	int total = 0;
    	for (Card card: hand) {
    		if (card != null)
    			total += card.getPoints();
    	}
    	if (total > 21) { // si la somme dépasse 21
    		total = 0;
    		ArrayList<Card> cards = new ArrayList<Card>(); // on crée un nouveau tableau pour vérifier si la main contient un as
    		boolean containsAce = false;
    		for (Card card: hand) {
				if (card != null) {	
    			cards.add(card);
	    			if(card.isAce()) {
	    				containsAce = true;
	    			}
				}
    		}
    		Collections.sort(cards, new SortAces()); // on reclasse le tableau afin de compter les points des as à la fin
    		for (Card card: cards) {
    				if (containsAce) {				// si cette main contient un as
    					if(card.isAce() && (card.getPoints() + total > 21)) // si cette carte est un as et que le total en l'incluant dépasse 21, on ajoute 1 point au lieu de 11
    						total += 1;
    					else
    						total = total + card.getPoints();
    				}
					else
						total = total + card.getPoints();
    		}
    	}
    	
    	return total;
    	
    }
    
	// ajouter une carte au tableau
    public void addCard(Card card) {
    	
    	this.hand[cardNumber] = card;
    	this.cardNumber++;
    	
    }
    
	//retourne true si la somme de deux cartes est égale à 21. False sinon
    public boolean isBlackjack() {
    	
    	if (this.getPoints() == 21)
    		return true;
    	
    	return false;

    }
    
	// retourne true si la somme des points a une valeur supérieur a 21. False sinon.
    public boolean isBust() {

    	if (this.getPoints() > 21)
    		return true;
    	
    	return false;
    	
    }

}