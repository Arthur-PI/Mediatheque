package service;

import produit.DVD;

public class EmpruntException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private DVD dvd;
	
	public EmpruntException(DVD dvd) {
		this.dvd = dvd;
	}
	
	@Override
	public String toString() {
		return "Le dvd: " + this.dvd + " n'est emprunter par personne.";
	}

}
