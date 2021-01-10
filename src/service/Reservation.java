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
			this.sin = new BufferedReader(new InputStreamReader(this.clientSoc.getInputStream()));
			this.cout = new PrintWriter (this.clientSoc.getOutputStream(), true);
			
			if (setNumAbo() == CODERREUR) { // Annulation de connexion
				terminate();
				return;
			}
			
			cout.write(NOUVEAU + NORESPONSE);
			cout.println(this.abonne.getPrenom() + ", bienvenue au service Emprunt.");
			
			cout.write(ENCOURS + LONGMESSAGE);
			String stock = this.getStock();
			String message = "Que voulez-vous réserver (0 pour annuler):\n" + stock;
			
			String numeroDocument = this.getDocumentChoice(ENCOURS + LONGMESSAGE, message);
			if (numeroDocument.equals("0")) {
				terminate();
				return;
			}
			
			message = "";
			try {
				this.documents.get(numeroDocument).reserverPour(this.abonne);
				message = "Vous avez réserver le document numéro " + numeroDocument + ", vous avez 2H pour venir le recuperer.";
			} catch(ReservationException e) {
				if(e.toString().contains("moins d'une minute")) {
					String reponse = "";
					cout.write(ENCOURS + NORESPONSE);
					cout.println(e);
					do {
						cout.write(ENCOURS + NOMESSAGE);
						cout.println("");
						reponse = sin.readLine();
					} while (!reponse.equals("oui") && !reponse.equals("non"));
					
					if (reponse.equals("oui")) {
						int duration = this.documents.get(numeroDocument).getSecondUntilFinReserve();
						cout.write(NOUVEAU + MUSIQUE);
						cout.write(duration);
						cout.println("Seulement " + duration + "s a patienter.");
						try {
							Thread.sleep(duration * 1000);
							this.documents.get(numeroDocument).reserverPour(this.abonne);
							message = "Vous avez réserver le document numéro " + numeroDocument + ", vous avez 2H pour venir le recuperer.";
						} catch (InterruptedException | ReservationException e1) {
							cout.write(NOUVEAU + NORESPONSE);
							cout.println("Malheureusement le document à été emprunté par la personne ayant reservé...");
						}
					}
				}
			}
			
			cout.write(NOUVEAU + NORESPONSE);
			cout.println(message);
			terminate();
			
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}

	
}
