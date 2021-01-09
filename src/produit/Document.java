package produit;

import client.Abonne;
import service.EmpruntException;
import service.ReservationException;

public interface Document {
	int numero();
	void reserverPour(Abonne ab) throws ReservationException ;
	void empruntPar(Abonne ab) throws EmpruntException;
	void retour();
	
}
