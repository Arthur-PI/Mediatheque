package service;

import server.Service;

public class Emprunt implements Runnable, Service {
	private Thread thread;
	
	public Emprunt() {
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
