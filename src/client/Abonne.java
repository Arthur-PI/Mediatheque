package client;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;

public class Abonne {
	private static ArrayList<String> NUMEROS = new ArrayList<>();
	private String nom;
	private String prenom;
	private String numero;
	private String email;
	private LocalDate dateNaissance;
	private LocalDate penalite;
	
	private static int AGEADULTE = 16;
	
	
	public Abonne(String prenom, String nom, String dateNaissance) {
		this.nom = nom;
		this.prenom = prenom;
		int y = Integer.parseInt(dateNaissance.substring(6));
		int m = Integer.parseInt(dateNaissance.substring(3, 5));
		int d = Integer.parseInt(dateNaissance.substring(0, 2));
		this.dateNaissance = LocalDate.of(y, m, d);
		this.penalite = null;
		this.email = "pigeon.arthur@hotmail.fr";
		generateNumero();
	}
	
	private void generateNumero() {
		/*
		 * Genere un numero d'utilisateur aleatoire qui n'existe pas deja
		 * */
		synchronized (NUMEROS) {
			Random r = new Random(); // 12/09/2001
			this.numero = "";
			for (int i = 0; i < 4; i++) {
				this.numero += String.valueOf(r.nextInt(10));
			}
			if (NUMEROS.contains(this.numero)) {
				generateNumero();
			} else {
				NUMEROS.add(this.numero);
			}
		}
	}
	
	public boolean isAdulte() {
		long days = ChronoUnit.DAYS.between(this.dateNaissance, LocalDate.now());
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
	
	public String getEmail() {
		return this.email;
	}
	
	public void startPenaliteRetard() {
		this.penalite = LocalDate.now().plusDays(30);
	}
	
	public boolean isBanished() {
		synchronized (this) {
			if (this.penalite == null || LocalDate.now().isAfter(this.penalite)) {
				this.penalite = null;
				return false;
			}
			return true;
		}
	}
	
	public String getDatePenalite() {
		synchronized (this) {
			return this.penalite != null ? this.penalite.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
					: null;
		}
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
