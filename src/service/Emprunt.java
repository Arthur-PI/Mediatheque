package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import client.Abonne;
import server.Service;

public class Emprunt implements Runnable, Service {
	/* PORT 4000 */
	private Thread thread;
	private HashMap<String, Abonne> abonnes;
	private Abonne abonne;
	private Socket clientSoc;
	private BufferedReader sin;
	private PrintWriter cout;
	private static int CODERREUR = 1;
	
	
	public Emprunt(Socket s) {
		this.thread = new Thread();
	}
	
	public void launch() {
		this.thread.start();
	}
	
	public int setNumAbo() throws IOException {
		String numeroAbo;
		String message = "Quel est votre numero d'abonne (0 pour annuler):";
		do {
			cout.write(message);
			numeroAbo = this.sin.readLine();
		} while (!this.abonnes.containsKey(numeroAbo) && !numeroAbo.equals("0"));
		
		if (numeroAbo.equals("0")) {
			return CODERREUR;
		}
		else {
			this.abonne = this.abonnes.get(numeroAbo);
			return 0;
		}
	}

	@Override
	public void run() {
		try {
			this.sin = new BufferedReader(new InputStreamReader(this.clientSoc.getInputStream()));
			this.cout = new PrintWriter (this.clientSoc.getOutputStream(), true);
			
			if (setNumAbo() == CODERREUR) { // Annulation de connexion
				this.sin.close();
				this.cout.write("over");
				this.cout.close();
				this.clientSoc.close();
				return;
			}
			// L'abonne c'est connecte
			
			
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}

}
