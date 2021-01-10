package server;

import java.util.HashMap;

import client.Abonne;
import produit.DVD;
import produit.Document;
import service.ServiceFactory;

public class ServerHandler {
	private static int[] PORTS = {3000, 4000, 5000};
	
	public void launch(IServiceFactory factory) {
		System.out.println("Starting Server");
		for(int i=0; i < PORTS.length; i++) {
			new PortListener(PORTS[i], factory);
		}
	}
}
