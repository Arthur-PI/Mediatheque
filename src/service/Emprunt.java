package service;

import java.net.Socket;

import server.Service;

public class Emprunt implements Runnable, Service {
	/* PORT 4000 */
	private Thread thread;
	
	public Emprunt(Socket s) {
		this.thread = new Thread();
	}
	
	public void launch() {
		this.thread.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
