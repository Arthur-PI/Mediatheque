package service;

import java.io.*;
import java.net.Socket;
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
			// ---- CREATION DES FLUX DE COMMUNICATION AVEC L'UTILISATEUR ----
			this.initFlux();
			
			// ---- AUTHENTIFICATION DE L'ABONNE ----
			if (setNumAbo() == CODERREUR) { // Annulation de connexion
				terminate();
				return;
			}
			
			// ---- MESSAGE DE BIENVENUE A L'ABONNE ----
			cout.write(NOUVEAU + NORESPONSE);
			cout.println("Bonjour " + this.abonne.getPrenom() + ", bienvenue au service Emprunt.");
			
			// ---- AFFICHAGE DU STOCK POUR L'ABONNE ----
			String stock = this.getStock();
			String message = "Que voulez-vous emprunter (0 pour annuler):\n" + stock;
			
			// ---- RECUPERATION DU CHOIX DE DOCUMENT DE L'ABONNE ----
			String numeroDocument = this.getDocumentChoice(ENCOURS + LONGMESSAGE, message);
			if (numeroDocument.equals("0")) {
				terminate(); 
				return;
			}
			try {
				// ---- EMPRUNT DU DOCUMENT CHOISIT PAR L'ABONNE SI DISPONIBLE ----
				this.documents.get(numeroDocument).empruntPar(this.abonne);
				cout.write(NOUVEAU + NORESPONSE);
				cout.println("Vous avez louer le le document numero: " + numeroDocument);
			} catch(EmpruntException e) {
				// ---- SI LE DOCUMENT N'EST PAS DISPONIBLE A L'EMPRUNT AFFICHAGE DU MESSAGE D'ERREUR A L'ABONNE ----
				String reponse = "";
				cout.write(NOUVEAU + NORESPONSE);
				cout.println(e);
			}
			
			// ---- MESSAGE DE FIN + FERMETURE DES FLUX ----
			cout.write(ENCOURS + NORESPONSE);
			cout.println("Merci de votre visite, a bientot.");
			terminate();
			
		} catch (IOException e) { e.printStackTrace(); }
	}
}
