package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import client.Abonne;
import server.Service;

public class Emprunt implements Runnable, Service {
	/* PORT 4000 */
	private Thread thread;
	private HashMap<String, Abonne> abonnes;
	private Abonne abonne;
	private Socket clientSoc;
	private BufferedReader sin;
	private PrintWriter cout;
	private static int CODERREUR = 1;
	
	private static String ENCOURS = "en cours";
	private static String NOUVEAU = "nouveau";
	private static String FINI = "over";
	
	
	public Emprunt(Socket s) {
		this.clientSoc = s;
		this.thread = new Thread(this);
		this.thread.start();
	}
	
	public void launch() {
		this.thread.start();
	}
	
	public int setNumAbo() throws IOException {
		String numeroAbo;
		String message = "Quel est votre numero d'abonne (0 pour annuler):";
		String etat = NOUVEAU;
		System.out.println("Authentification de l'utilisateur en cours...");
		
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

	@Override
	public void run() {
		try {
			this.sin = new BufferedReader(new InputStreamReader(this.clientSoc.getInputStream()));
			this.cout = new PrintWriter (this.clientSoc.getOutputStream(), true);
			
			if (setNumAbo() == CODERREUR) { // Annulation de connexion
				
				this.cout.println(FINI);
				this.cout.close();
				this.clientSoc.close();
				return;
			}
			cout.println(FINI);
			cout.println("Bienvenue sur le service emprunt !");
			this.sin.close();
			this.cout.close();
			this.clientSoc.close();
			
			
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}

}
