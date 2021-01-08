package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import client.Abonne;
import produit.Document;

public abstract class Service {
	protected Thread thread;
	
	protected HashMap<String, Abonne> abonnes;
	protected HashMap<String, Document> documents;
	
	protected Abonne abonne;
	protected Socket clientSoc;
	protected BufferedReader sin;
	protected PrintWriter cout;
	
	protected static int CODERREUR = 1;
	protected static String ENCOURS = "en cours";
	protected static String NOUVEAU = "nouveau";
	protected static String FINI = "over";
	
	protected int setNumAbo() throws IOException {
		/*
		 * Fait la procedure d'authentification de l'utilisateur via le socket
		 * Input: None
		 * Output: 0 si reussie, CODERREUR sinon
		 * */
		
		String numeroAbo;
		String message = "Quel est votre numero d'abonne (0 pour annuler):";
		String etat = NOUVEAU;
		do {
			cout.println(etat);
			cout.println(message);
			numeroAbo = this.sin.readLine();
			etat = ENCOURS;
		} while (!this.abonnes.containsKey(numeroAbo) && !numeroAbo.equals("0"));
		
		if (numeroAbo.equals("0")) {
			return CODERREUR;
		}
		else {
			this.abonne = this.abonnes.get(numeroAbo);
			return 0;
		}
	}
	
	protected void terminate() throws IOException {
		/*
		 * Ferme les flux ouvert durant la discussion avec le client
		 * Input: None
		 * Output: None
		 * */
		
		this.cout.println(FINI);
		this.cout.close();
		this.clientSoc.close();
	}
	
	public abstract void run();
	
}
