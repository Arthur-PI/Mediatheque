package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import service.ServiceFactory;

public class PortListener implements Runnable{
	
	private final int PORT;
	private IServiceFactory factory;
	private Thread thread;
	
	public PortListener(int port, IServiceFactory factory) {
		this.PORT = port;
		this.factory = factory;
		this.thread = new Thread(this);
		this.thread.start();
	}

	@Override
	public void run() {
		try {
			ServerSocket serveur = new ServerSocket(PORT);
			System.out.println("Beginning listening on PORT " + this.PORT);
			while(true) {
				Socket newClient = serveur.accept();
				System.out.println("New Client connection on PORT " + this.PORT);
				this.factory.newService(this.PORT, newClient);
			}
		} catch (IOException e) { e.printStackTrace(); }
		
	}
	
	public static void main(String args[]) {
		new PortListener(3000, new ServiceFactory());
		while(true) {}
	}

}
