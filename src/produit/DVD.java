package produit;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

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
	
	private Abonne reserverPar;
	private Abonne emprunterPar;
	// Les Date sont les dates et heures de fin des services pas le debut
	private LocalDateTime dateEmprunt;
	private LocalDateTime dateReservation;
	private ArrayList<String> mailListe;
	
	private final static long NBJOURS_EMPRUNT = 15;
	private final static long LIMITE_RETARD = 15;
	private final static long NBMINUTES_RESERVATION = 120;
	
	
	public DVD(String titre, double prix, boolean pourAdulte, String annee) {
		this.titre = titre;
		this.prixParJour = prix;
		this.numero = NUMEROS++;
		this.pourAdulte = pourAdulte;
		this.annee = annee;
		this.mailListe = new ArrayList<>();
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
		synchronized (this) {
			if (ab.isBanished())
				throw new ReservationException(this, "Vous avez une penalite de retard vous ne pouvez rien reservé jusqu'au " + ab.getDatePenalite());
			else if (this.isReserver()) {
				long difference = this.getSecondUntilFinReserve();
				if ( difference <= 60 && difference > 0)
					throw new ReservationException(this, "Le DVD est deja reservé mais il reste moins de " + difference + " secondes avant la fin, voulez-vous attendre ? (oui/non):");
				throw new ReservationException(this, "Le DVD est deja reservé jusqu'a " + this.dateReservation.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
			}
			else if (this.isEmprunter())
				throw new ReservationException(this, "Le DVD est deja emprunter jusqu'au " + this.dateEmprunt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)));
			else if (!ab.isAdulte() && this.isPourAdulte())
				throw new ReservationException(this, "Vous n'avez pas l'age pour emprunter ce DVD");
			
			this.reserverPar = ab;
			this.dateReservation = LocalDateTime.now().plusMinutes(NBMINUTES_RESERVATION);
		}
	}

	@Override
	public void empruntPar(Abonne ab) throws EmpruntException {
		synchronized (this) {
			if (ab.isBanished())
				throw new EmpruntException(this,"Vous avez une penalite de retard vous ne pouvez rien emprunter jusqu'au " + ab.getDatePenalite());
			else if (this.isReserver() && this.reserverPar != ab)
				throw new EmpruntException(this, "Le DVD est deja reserver jusqu'a " + this.dateReservation.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
			else if (this.isEmprunter())
				throw new EmpruntException(this, "Le DVD est deja emprunter jusqu'au " + this.dateEmprunt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)));
			else if (!ab.isAdulte() && this.isPourAdulte())
				throw new EmpruntException(this, "Vous n'avez pas l'age pour emprunter ce DVD");
			
			this.emprunterPar = ab;
			this.dateEmprunt = LocalDateTime.now().plusDays(NBJOURS_EMPRUNT);
			this.reserverPar = null;
			this.dateReservation = null;
		}
	}
	
	@Override
	public void retour() {
		synchronized (this) {
			if (this.dateEmprunt == null)
				return;
			else if (LocalDateTime.now().isAfter(this.dateEmprunt.plusDays(LIMITE_RETARD)))
				this.emprunterPar.startPenaliteRetard();
			this.emprunterPar = null;
			this.dateEmprunt = null;
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
		synchronized (this) {
			if (this.dateReservation == null)
				return false;
			if (this.dateReservation.isAfter(LocalDateTime.now()))
				return true;
			this.dateReservation = null;
			this.reserverPar = null;
			return false;
		}
	}

	public boolean isEmprunter() {
		return this.emprunterPar != null;
	}

	public boolean isPourAdulte() {
		return this.pourAdulte;
	}
	
	public void addToMailList(String email) {
		/*
		 * Ajoute une adresse email a liste des adresses qui attendent d'etre prevenue en cas de retour
		 * */
		synchronized(this.mailListe) {
			this.mailListe.add(email);
		}
		
	}
	
	public ArrayList<String> getMailList(){
		/*
		 * Renvoie une l'ArrayList des adresses mail qui attendre d'etre prevenue en cas de retour
		 * Clear la liste une fois envoyer
		 * */
		synchronized (this.mailListe) {
			ArrayList<String> tmp = new ArrayList<>(this.mailListe);
			this.mailListe.clear();
			return tmp;
		}
	}
	
	public int getSecondUntilFinReserve(){
		/*
		 * Renvoie le temps qu'il reste en secondes avant la fin de la reservation en cours
		 * */
		return (int) Duration.between(LocalDateTime.now(), this.dateReservation).getSeconds();
	}

	@Override
	public String toString() {
		String adulte = this.isPourAdulte()?"Oui":"Non";
		return this.titre + "\n- Type: DVD\n- Date: " + this.annee + "\n- Prix: " + this.prixParJour + "\n- Adulte: " + adulte + "\n- Numero: " + this.numero;
	}
	
	
}
