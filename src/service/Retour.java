package service;

import java.net.Socket;

import server.Service;

public class Retour implements Runnable, Service{
	/* PORT 5000 */
	private Thread thread;
	
	
	public Retour(Socket s) {
		this.thread = new Thread();
	}
	
	@Override
	public void launch() {
		this.thread.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
}
