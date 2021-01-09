package service;

import produit.DVD;

public class ReservationException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private DVD dvd;
	private String message;
	
	public ReservationException(DVD dvd, String message) {
		this.dvd = dvd;
		this.message = message;
	}
	
	@Override
	public String toString() {
		return this.message;
	}

}
