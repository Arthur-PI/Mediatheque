package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import client.Abonne;
import produit.Document;
import server.Service;

public class Emprunt extends Service implements Runnable {
	/* Service de Emprunt de la mediateque, le thread s'occuppe d'un client a la fois
	 * La comunication vers le client se fait en deux temps un premier String pour donner l'etat de la communication:
	 * Nouveau debut de communication(NOUVEAU), Communication en cours (ENCOURS), et communication finit(FINIS)
	 * et une deuxieme chaine de characteres pour donner le message a afficher a l'utilisateur
	 * Connection au PORT 4000
	 *  */
	
	public Emprunt(Socket s, HashMap<String, Abonne> abo, HashMap<String, Document> docs) {
		this.thread = new Thread(this);
		this.clientSoc = s;
		this.abonnes = abo;
		this.documents = docs;
		this.thread.start();
	}

	@Override
	public void run() {
		try {
			this.sin = new BufferedReader(new InputStreamReader(this.clientSoc.getInputStream()));
			this.cout = new PrintWriter (this.clientSoc.getOutputStream(), true);
			
			if (setNumAbo() == CODERREUR) { // Annulation de connexion
				terminate();
				return;
			}
			
			cout.println(NOUVEAU);
			cout.println(this.abonne.getPrenom() + ", bienvenue au service Emprunt.");
			// TODO
			
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}

}
