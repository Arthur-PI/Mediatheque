package client;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;

public class Abonne {
	private static ArrayList<String> NUMEROS = new ArrayList<>();
	private String nom;
	private String prenom;
	private String numero;
	private LocalDate dateNaissance;
	
	private static int AGEADULTE = 16;
	
	
	public Abonne(String prenom, String nom, String dateNaissance) {
		this.nom = nom;
		this.prenom = prenom;
		int y = Integer.parseInt(dateNaissance.substring(6));
		int m = Integer.parseInt(dateNaissance.substring(3, 5));
		int d = Integer.parseInt(dateNaissance.substring(0, 2));
		this.dateNaissance = LocalDate.of(y, m, d);
		generateNumero();
	}
	
	private void generateNumero() {
		/*
		 * Genere un numero d'utilisateur aleatoire qui n'existe pas deja
		 * */
		Random r = new Random(); // 12/09/2001
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
	
	public boolean isAdulte() {
		long days = ChronoUnit.DAYS.between(this.dateNaissance, LocalDate.now());
		System.out.println(days);
		if (days >= 365 * AGEADULTE)
			return true;
		return false;
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
