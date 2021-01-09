package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import client.Abonne;
import produit.Document;
import server.Service;

public class Retour extends Service implements Runnable{
	/* Service de Retour de la mediateque, le thread s'occuppe d'un client a la fois
	 * Connection au PORT 5000
	 *  */
	
	public Retour(Socket s, HashMap<String, Abonne> abo, HashMap<String, Document> docs) {
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
			
			cout.write(NOUVEAU + NORESPONSE);
			cout.println("Bienvenue au service Emprunt.");
			
			String message = "Donnez le numero du document que vous voulez rendre:";
			
			String numeroDocument = this.getDocumentChoice(ENCOURS, message);
			if (numeroDocument.equals("0")) {
				terminate();
				return;
			}
			
			this.documents.get(numeroDocument).retour();
			cout.write(ENCOURS + NORESPONSE);
			cout.println("Merci de votre visite, a bientot.");
			terminate();
			return;
			
		} catch (IOException e) { e.printStackTrace(); }
		
		
		
	}

	
}
