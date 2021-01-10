package server;

import java.util.HashMap;

import client.Abonne;
import produit.DVD;
import produit.Document;
import service.ServiceFactory;

public class ServerHandler {
	private static int[] PORTS = {3000, 4000, 5000};
	private static ServiceFactory FACTORY = new ServiceFactory();
	
	public static void main(String args[]) {
		
		HashMap<String, Abonne> abonnes = getAbonnes();
		HashMap<String, Document> documents = getDocuments();
		
		FACTORY.setAbonnes(abonnes);
		FACTORY.setDocuments(documents);
		
		System.out.println("Starting Server");
		
		for(int i=0; i < 3; i++) {
			new PortListener(PORTS[i], FACTORY);
		}
		while(true) {
			
		}
	}
	
	public static HashMap<String, Abonne> getAbonnes(){
		Abonne a1 = new Abonne("Arthur", "Pigeon", "12/09/2001");
		Abonne a2 = new Abonne("Jorge", "Koch", "24/04/2005");
		Abonne a3 = new Abonne("Landon", "Ritter", "03/01/1995");
		Abonne a4 = new Abonne("Oisin", "Deacon", "22/08/1955");
		Abonne a5 = new Abonne("Sanaya", "Mcclure", "27/01/1996");
		Abonne a6 = new Abonne("Rosie", "Dawson", "17/12/2006");
		Abonne a7 = new Abonne("Rafael", "Sellers", "19/05/2003");
		
		
		
		HashMap<String, Abonne> test = new HashMap<>();
		test.put(a1.getNumero(), a1);
		test.put(a2.getNumero(), a2);
		test.put(a3.getNumero(), a3);
		test.put(a4.getNumero(), a4);
		test.put(a5.getNumero(), a5);
		test.put(a6.getNumero(), a6);
		test.put(a7.getNumero(), a7);
	
		System.out.println("Abonnes:");
		for (String a : test.keySet()) {
			System.out.println(a);
		}
		
		return test;
	}
	
	public static HashMap<String, Document> getDocuments(){
		DVD d1 = new DVD("The Matrix", 3, false, "2020");
		DVD d2 = new DVD("Les evades", 2.5, false, "1994");
		DVD d3 = new DVD("Alien", 3.24, true, "1979");
		DVD d4 = new DVD("Drunk", 4, false, "2020");
		DVD d5 = new DVD("La Haine", 2.70, false, "1995");
		DVD d6 = new DVD("The Trueman Show", 3, false, "2020");
		DVD d7 = new DVD("Parasite", 4.2, false, "2019");
		
		
		HashMap<String, Document> test = new HashMap<>();
		test.put(String.valueOf(d1.numero()), d1);
		test.put(String.valueOf(d2.numero()), d2);
		test.put(String.valueOf(d3.numero()), d3);
		test.put(String.valueOf(d4.numero()), d4);
		test.put(String.valueOf(d5.numero()), d5);
		test.put(String.valueOf(d6.numero()), d6);
		test.put(String.valueOf(d7.numero()), d7);
		
		System.out.println("DVD:");
		for (String a : test.keySet()) {
			System.out.println(a);
		}
		
		return test;
	}
}
