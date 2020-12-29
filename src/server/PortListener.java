package server;

public class PortListener implements Runnable{
	
	private final int PORT;
	private Thread thread;
	
	public PortListener(int port) {
		this.PORT = port;
		this.thread = new Thread();
		this.thread.start();
	}

	@Override
	public void run() {
		// TODO
	}

}
