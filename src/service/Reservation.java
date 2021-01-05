package service;

import java.net.Socket;

import server.Service;

public class Reservation implements Runnable, Service{
	/* PORT 3000 */
	private Thread thread;
	
	public Reservation(Socket s) {
		this.thread = new Thread();
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
