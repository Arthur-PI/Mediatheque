package service;

import produit.DVD;

public class ReservationException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private DVD dvd;
	
	public ReservationException(DVD dvd) {
		this.dvd = dvd;
	}
	
	@Override
	public String toString() {
		return "Le dvd: " + this.dvd + " n'est reservee par personne.";
	}

}
