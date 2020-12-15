package server;

import service.Emprunt;

public class ServerHandler {
	private static int[] PORTS = {3000, 4000, 5000};
	
	public static void main(String args[]) {
		PortListener serverEmprunt = new PortListener();
		PortListener serverReservation = new PortListener();
		PortListener serverRetour = new PortListener();
	}
	
}
