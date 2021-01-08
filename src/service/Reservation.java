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
			
			cout.println(NOUVEAU);
			cout.println(this.abonne.getPrenom() + ", bienvenue au service Reservation.");
			// TODO
			
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}

	
}
