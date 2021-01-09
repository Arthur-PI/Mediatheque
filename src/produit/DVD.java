package produit;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import client.Abonne;
import service.EmpruntException;
import service.ReservationException;

public class DVD implements Document{
	private static int NUMEROS = 123412;
	
	private int numero;
	private String titre;
	private boolean pourAdulte;
	private double prixParJour;
	private String annee;
	
	private Abonne reservationPar;
	private Abonne emprunterPar;
	// Les Date sont les dates et heures de fin des services pas le debut
	private LocalDateTime dateEmprunt;
	private LocalDateTime dateReservation;
	
	private static long NBJOURSEMPRUNT = 15;
	
	
	public DVD(String titre, double prix, boolean pourAdulte, String annee) {
		this.titre = titre;
		this.prixParJour = prix;
		this.numero = NUMEROS++;
		this.pourAdulte = pourAdulte;
	}
	
	public DVD(String titre, double prix, String annee) {
		this(titre, prix, false, annee);
	}
	
	public DVD(DVD d2) {
		this(new String(d2.getTitre()), d2.getPrix(), d2.isPourAdulte(), new String(d2.getAnnee()));
	}
	
	@Override
	public int numero() {
		return this.numero;
	}

	@Override
	public void reserverPour(Abonne ab) throws ReservationException {
		synchronized(this) {
			if (this.isReserver() || this.isEmprunter()) { throw new ReservationException(this, "caca"); }
			this.reservationPar = ab;
		}
	}

	@Override
	public void empruntPar(Abonne ab) throws EmpruntException {
		if (this.isReserver() && this.reservationPar != ab)
			throw new EmpruntException(this, "Le DVD est reserver jusqu'a " + this.dateReservation.format(DateTimeFormatter.ISO_LOCAL_TIME));
		else if (this.isEmprunter())
			throw new EmpruntException(this, "Le DVD est emprunter jusqu'au " + this.dateEmprunt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM)));
		else if (!ab.isAdulte())
			throw new EmpruntException(this, "Vous n'avez pas l'age pour emprunter ce DVD");
		
		this.emprunterPar = ab;
		this.dateEmprunt = LocalDateTime.now().plusDays(NBJOURSEMPRUNT);
		this.reservationPar = null;
		this.dateReservation = null;
	}
	
	@Override
	public void retour() {
		synchronized(this) {
			// TODO
		}
	}
	
	public double getPrix() {
		return this.prixParJour;
	}
	
	public String getTitre() {
		return this.titre;
	}
	
	public String getAnnee() {
		return this.annee;
	}

	public boolean isReserver() {
		if (this.dateReservation == null)
			return false;
		if (this.dateReservation.isBefore(LocalDateTime.now()))
			return true;
		this.dateReservation = null;
		this.reservationPar = null;
		return false;
	}

	public boolean isEmprunter() {
		return this.emprunterPar != null;
	}

	public boolean isPourAdulte() {
		return this.pourAdulte;
	}

	@Override
	public String toString() {
		String adulte;
		if (this.isPourAdulte())
			adulte = "Oui";
		else
			adulte = "Non";
		return this.titre + "\n- Type: DVD\n- Date: " + this.annee + "\n- Prix: " + this.prixParJour + "\n- Adulte: " + adulte + "\n- Numero: " + this.numero;
	}
	
	
}
