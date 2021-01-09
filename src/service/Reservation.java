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
			String message = "Que voulez-vous reserver (0 pour annuler):\n" + stock;
			
			String numeroDocument = this.getDocumentChoice(ENCOURS + LONGMESSAGE, message);
			if (numeroDocument.equals("0")) {
				terminate(); 
				return;
			}
			
			message = "";
			try {
				this.documents.get(numeroDocument).reserverPour(this.abonne);
				message = "Vous avez reserver le document numero " + numeroDocument + ", vous avez 2H pour venir le recuperer.";
			} catch(ReservationException e) {
				message = e.toString();
			}
			
			cout.write(NOUVEAU + NORESPONSE);
			cout.println(message);
			terminate();
			
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}

	
}
