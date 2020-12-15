package produit;

import client.Abonne;
import service.EmpruntException;
import service.ReservationException;

public class DVD implements Document{
	private static int NUMEROS = 123412;
	
	private int numero;
	private String titre;
	private boolean pourAdulte;
	private double prixParJour;
	
	private Abonne reservationPar;
	private Abonne emprunterPar;
	
	
	public DVD(String titre, double prix, boolean pourAdulte) {
		this.titre = titre;
		this.prixParJour = prix;
		this.numero = NUMEROS++;
		this.pourAdulte = pourAdulte;
	}
	
	public DVD(String titre, double prix) {
		this(titre, prix, false);
	}
	
	public DVD(DVD d2) {
		this(new String(d2.getTitre()), d2.getPrix(), d2.isPourAdulte());
	}
	
	@Override
	public int numero() {
		return this.numero;
	}

	@Override
	public void reservationPour(Abonne ab) throws ReservationException {
		if (this.isReserver() || this.isEmprunter()) { throw new ReservationException(this); }
		
		this.reservationPar = ab;
	}

	@Override
	public void empruntPar(Abonne ab) throws EmpruntException {
		if (this.isReserver() &&  this.reservationPar != ab) { throw new EmpruntException(this); }
		else if (this.isEmprunter()) { throw new EmpruntException(this); }
		
		this.emprunterPar = ab;
		this.reservationPar = null;
	}
	
	@Override
	public void retour() {
		// TODO
	}
	
	public double getPrix() {
		return this.prixParJour;
	}
	
	public String getTitre() {
		return this.titre;
	}

	public boolean isReserver() {
		return this.reservationPar != null;
	}

	public boolean isEmprunter() {
		return this.emprunterPar != null;
	}

	public boolean isPourAdulte() {
		return this.pourAdulte;
	}
}
