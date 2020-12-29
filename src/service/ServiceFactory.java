package service;

import java.net.Socket;

import server.IServiceFactory;

public class ServiceFactory implements IServiceFactory
{
	public void newService(int n, Socket s) {
		switch(n) {
		case 3000:
			new Emprunt(s);
			break;
		case 4000:
			new Reservation(s);
			break;
		case 5000:
			new Retour(s);
			break;
		}
	}
}
