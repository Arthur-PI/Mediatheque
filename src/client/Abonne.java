package client;

import java.util.ArrayList;
import java.util.Random;

public class Abonne {
	private static ArrayList<String> NUMEROS = new ArrayList<>();
	private String nom;
	private String prenom;
	private String numero;
	
	
	public Abonne(String prenom, String nom) {
		this.nom = nom;
		this.prenom = prenom;
		generateNumero();
	}
	
	private void generateNumero() {
		/*
		 * Genere un numero d'utilisateur aleatoire qui n'existe pas deja
		 * */
		Random r = new Random();
		this.numero = "";
		for(int i=0; i < 4; i++) {
			this.numero += String.valueOf(r.nextInt(10));
		}
		if (NUMEROS.contains(this.numero)) {
			generateNumero();
		}
		else {
			NUMEROS.add(this.numero);
		}
	}
	
	public String getNumero() {
		return this.numero;
	}
	
	public String getPrenom() {
		return this.prenom;
	}
	
	public String getNom() {
		return this.nom;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Abonne other = (Abonne) obj;
		if (this.numero == null) {
			if (other.getNumero() != null)
				return false;
		} else if (!this.numero.equals(other.getNumero()))
			return false;
		return true;
	}
	
}
