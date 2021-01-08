package server;

import java.util.ArrayList;
import java.util.HashMap;

import client.Abonne;
import service.ServiceFactory;

public class ServerHandler {
	private static int[] PORTS = {3000, 4000, 5000};
	private static ServiceFactory FACTORY = new ServiceFactory();
	
	public static void main(String args[]) {
		
		Abonne a1 = new Abonne("Arthur", "Pigeon");
		Abonne a2 = new Abonne("Jorge", "Koch");
		Abonne a3 = new Abonne("Landon", "Ritter");
		Abonne a4 = new Abonne("Oisin", "Deacon");
		Abonne a5 = new Abonne("Sanaya", "Mcclure");
		Abonne a6 = new Abonne("Rosie", "Dawson");
		Abonne a7 = new Abonne("Rafael", "Sellers");
		
		
		HashMap<String, Abonne> test = new HashMap<>();
		test.put(a1.getNumero(), a1);
		test.put(a2.getNumero(), a2);
		test.put(a3.getNumero(), a3);
		test.put(a4.getNumero(), a4);
		test.put(a5.getNumero(), a5);
		test.put(a6.getNumero(), a6);
		test.put(a7.getNumero(), a7);
		
		for (String a : test.keySet()) {
			System.out.println(a);
		}
		
		FACTORY.setAbonnes(test);
		
		
		System.out.println("Starting Server");
		
		for(int i=0; i < 3; i++) {
			new PortListener(PORTS[i], FACTORY);
		}
		while(true) {
			
		}
	}
}
