package server;

import java.io.*;
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
		/*
		 * Renvoie une chaine de charactere representant le stock de documents de la mediateque
		 * */
		String stock = "";
		Document curDoc;
		for (String docNum : this.documents.keySet()) {
			curDoc = this.documents.get(docNum);
			stock +="\n\n" + curDoc.toString();
		}
		return stock;
	}
	
	protected String getDocumentChoice(int etat, String message) throws IOException {
		/*
		 * Communique avec l'abonne pour recupere son choix de document.
		 * Input: etat initial de la comunication et message initial
		 * Outpur: reponse de l'utilisateur soit un numero de document valide soit 0.
		 * */
		String docNum = "";
		do {
			cout.write(etat);
			cout.println(message);
			docNum = sin.readLine();
			etat = ENCOURS + NOMESSAGE;
		} while(!docNum.equals("0") && !this.documents.containsKey(docNum));
		return docNum;
	}
	
	protected String getReponseOuiNon(int etat, String message) {
		/*
		 * Comunication avec le client dans le but de recuperer une reponse Oui/Non
		 * Input: etat initial de la discussion et message initial de la discussion 
		 * Output: reponse Oui/Non donnee par l'utilisateur (null en cas d'erreur de lecture)
		 * */
		try {
		String reponse = "";
		cout.write(etat);
		cout.println(message);
		do {
			cout.write(ENCOURS + NOMESSAGE);
			cout.println("");
			reponse = sin.readLine();
		} while (!reponse.equals("oui") && !reponse.equals("non"));
		return reponse;
		
		} catch (IOException e) {return null;}
	}
	
	public abstract void run();
	
}
