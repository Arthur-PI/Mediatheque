package service;

import server.Service;

public class Reservation implements Runnable, Service{
	private Thread thread;
	
	public Reservation() {
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
