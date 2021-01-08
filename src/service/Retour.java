package service;

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
		// TODO Auto-generated method stub
	}

	
}
