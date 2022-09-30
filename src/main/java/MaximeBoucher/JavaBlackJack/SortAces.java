package MaximeBoucher.JavaBlackJack;

import java.util.Comparator;

public class SortAces implements Comparator<Card>{

	// fonction pour classer les as à la fin du tableau qui est créé dans la classe Hand au cas où le total des points dépasse 21. Permet de compter les as en dernier.
    public int compare(Card a, Card b) {
		if (a.getRank().equalsIgnoreCase("ace") && (b.getRank().equalsIgnoreCase("ace")))
			return 0;
		else if (a.getRank().equalsIgnoreCase("ace")) 
			return 1;
		else
			return -1;
	}
    
}
