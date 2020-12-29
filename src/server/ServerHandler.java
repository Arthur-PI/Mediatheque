package server;

public class ServerHandler {
	private static int[] PORTS = {3000, 4000, 5000};
	
	public static void main(String args[]) {
		for(int i=0; i < PORTS.length; i++) {
			new PortListener(PORTS[i]);
		}
	}
	
}
