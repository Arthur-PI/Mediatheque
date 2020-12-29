package service;

import server.Service;

public class Retour implements Runnable, Service{
	private Thread thread;
	
	
	public Retour() {
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
