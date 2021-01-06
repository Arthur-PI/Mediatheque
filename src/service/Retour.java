package service;

import java.net.Socket;

import server.Service;

public class Retour implements Runnable, Service{
	/* PORT 5000 */
	private Thread thread;
	
	
	public Retour(Socket s) {
		this.thread = new Thread(this);
	}
	
	@Override
	public void launch() {
		this.thread.start();
	}
	
	public void getNumAbo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}

	
}
