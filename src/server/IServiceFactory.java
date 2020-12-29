package server;

import java.net.Socket;

public interface IServiceFactory {
	public void newService(int n, Socket s);
}
