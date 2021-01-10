package service;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

import client.Abonne;
import produit.Document;
import server.Service;

public class Reservation extends Service implements Runnable{
	/* Service de Reservation de la mediateque, le thread s'occuppe d'un client a la fois
	 * Connection au PORT 3000
	 *  */
	
	public Reservation(Socket s, HashMap<String, Abonne> abo, HashMap<String, Document> docs) {
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
			
			// ---- MESSAGE DE BIENVENUE SUR LE SERVICE ----
			cout.write(NOUVEAU + NORESPONSE);
			cout.println(this.abonne.getPrenom() + ", bienvenue au service Emprunt.");
			
			// ---- AFFICAGE DU STOCK + DEMANDE DU CHOIX ----
			cout.write(ENCOURS + LONGMESSAGE);
			String stock = this.getStock();
			String message = "Que voulez-vous réserver (0 pour annuler):\n" + stock;
			
			// ---- RECUPERATION DU CHOIX DE RESERVATION DE L'ABONNE ----
			String numeroDocument = this.getDocumentChoice(ENCOURS + LONGMESSAGE, message);
			if (numeroDocument.equals("0")) {
				terminate();
				return;
			}
			
			// ---- RESERVATION DU DOCUMENT CHOISIT PAR L'ABONNE ----
			message = "";
			try {
				this.documents.get(numeroDocument).reserverPour(this.abonne);
				message = "Vous avez réserver le document numéro " + numeroDocument + ", vous avez 2H pour venir le recuperer.";
			} catch(ReservationException e) {
				// ---- EN CAS D'ERREUR, ENVOYER L'ERREUR A L'UTILISATEUR OU MUSIQUE D'ATTENTE SI MOINS D'UNE MINUTE ----
				if(e.toString().contains("il reste moins de")) {
					String reponse = getReponseOuiNon(NOUVEAU + NORESPONSE, e.toString());
					if (reponse.equals("oui"))
						message = sendMusique(numeroDocument);
				}
				else {
					// ---- SI LE DOCUMENT N'EST PAS DISPONIBLE A L'EMPRUNT AFFICHAGE DU MESSAGE D'ERREUR A L'ABONNE ----
					String reponse = "";
					cout.write(NOUVEAU + NORESPONSE);
					cout.println(e);
					
					// ---- DEMANDE A L'ABONNE SI IL VEUT RECEVOIR UN MAIL QUAND LE DOCUEMENT REVIENT ----
					reponse = this.getReponseOuiNon(ENCOURS + NORESPONSE, "Voulez-vous recevoir un mail quand ce produit revient ? (oui/non):");
					
					if (reponse.equals("oui")) {
						// ---- ENREGISTREMENT DE L'EMAIL DE L"ABONNE DANS LA LISTE D'ATTENTE DU DOCUMENT ----
						cout.write(ENCOURS + NORESPONSE);
						this.documents.get(numeroDocument).addToMailList(this.abonne.getEmail());
						cout.println("Vous serez donc prevenu par mail");
					}
					message = "Au revoir et a bientot dans notre mediateque.";
				}
			}
			
			// ---- MESSAGE DE FIN + FERMUTURE DE TOUT LES FLUX ----
			cout.write(ENCOURS + NORESPONSE);
			cout.println(message);
			terminate();
			
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}
	
	private String sendMusique(String numeroDocument) {
		/*
		 * Envoie de la musique a l'abonne le temps que son document soit libre a la reservation.
		 * A la fin du temps imparti, engage une reservation pour l'abonne si il n'as pas ete emprunter entre temps
		 * */
		String message = "";
		int duration = this.documents.get(numeroDocument).getSecondUntilFinReserve() + 2;
		cout.write(NOUVEAU + MUSIQUE);
		cout.write(duration);
		cout.println("Seulement " + duration + "s a patienter.");
		try {
			Thread.sleep(duration * 1000);
			this.documents.get(numeroDocument).reserverPour(this.abonne);
			message = "Vous avez réserver le document numéro " + numeroDocument + ", vous avez 2H pour venir le recuperer.";
			return message;
		} catch (InterruptedException | ReservationException e1) {
			cout.write(NOUVEAU + NORESPONSE);
			cout.println("Malheureusement le document à été emprunté par la personne ayant reservé...");
			return "Merci de votre visite, a bientot.";
		}
	}

	
}
