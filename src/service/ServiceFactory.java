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
import server.IServiceFactory;

public class ServiceFactory implements IServiceFactory
{
	/*
	 * Creer les services demandes en fonction du numero de PORT donne
	 * Resercvation: 3000
	 * Emprunt: 4000
	 * Retour: 5000
	 * */
	
	private HashMap<String, Abonne> abo;
	private HashMap<String, Document> docs;
	
	public void newService(int n, Socket s) {
		switch(n) {
		case 3000:
			new Reservation(s, abo, docs);
			break;
		case 4000:
			new Emprunt(s, abo, docs);
			break;
		case 5000:
			new Retour(s, abo, docs);
			break;
		}
	}
	
	public void setAbonnes(HashMap<String, Abonne> abo) {
		this.abo = abo;
	}
	
	public void setDocuments(HashMap<String, Document> docs) {
		this.docs = docs;
	}
}
