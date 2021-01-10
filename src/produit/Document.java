package produit;

import java.util.ArrayList;

import client.Abonne;
import service.EmpruntException;
import service.ReservationException;

public interface Document {
	int numero();
	void reserverPour(Abonne ab) throws ReservationException ;
	void empruntPar(Abonne ab) throws EmpruntException;
	void retour();
	
	// Ajouter seulement pour la Certification Sitting Bull
	void addToMailList(String email);
	ArrayList<String> getMailList();
	
	//Ajouter SEULEMENT pour la Certification Grand Shaman
	int getSecondUntilFinReserve();
}
