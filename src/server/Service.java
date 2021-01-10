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
	
	protected static final int CODERREUR = 1;
	protected static final int ENCOURS = 0b0000001;
	protected static final int NOUVEAU = 0b0000010;
	protected static final int FINI = 0b0000100;
	protected static final int NORESPONSE = 0b0001000;
	protected static final int LONGMESSAGE = 0b0010000;
	protected static final int NOMESSAGE = 0b0100000;
	protected static final int MUSIQUE = 0b1000000;
	
	protected int setNumAbo() throws IOException {
		/*
		 * Fait la procedure d'authentification de l'utilisateur via le socket
		 * Input: None
		 * Output: 0 si reussie, CODERREUR sinon
		 * */
		
		String numeroAbo;
		String message = "Quel est votre numero d'abonne (0 pour annuler):";
		int etat = NOUVEAU;
		do {
			cout.write(etat);
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
		this.cout.write(FINI);
		this.cout.close();
		this.sin.close();
		this.clientSoc.close();
	}
	
	protected String getStock() {
		String stock = "";
		Document curDoc;
		for (String docNum : this.documents.keySet()) {
			curDoc = this.documents.get(docNum);
			stock +="\n\n" + curDoc.toString();
		}
		return stock;
	}
	
	protected String getDocumentChoice(int etat, String message) throws IOException {
		String docNum = "";
		do {
			cout.write(etat);
			cout.println(message);
			docNum = sin.readLine();
			etat = ENCOURS + NOMESSAGE;
		} while(!docNum.equals("0") && !this.documents.containsKey(docNum));
		return docNum;
	}
	
	public abstract void run();
	
}
