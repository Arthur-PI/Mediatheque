package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class client {
	private static String SERVERIP = "127.0.0.1";
	
	public static void main(String args[]) {
		Socket monSocket;
		System.out.println("Bienvenue sur l'application de votre Mediatheque preferer");
		System.out.println("Que voulez-vous faire ?");
		System.out.println("1 - Reservation");
		System.out.println("2 - Emprunt");
		System.out.println("3 - Retour");
		System.out.println("0 - Quitter");
		
		Scanner in = new Scanner(System.in);
		int rep = -1;
		
		do {
			try {
				rep = in.nextInt();
			} catch(InputMismatchException e) {in.next();}
		} while (rep < 0 || rep > 3);
		
		System.out.println("You choose " + rep);
		
		if (rep == 0) {
			in.close();
			return;
		}
		try {
			if(rep == 1) {
				monSocket = new Socket(SERVERIP, 3000);
			}
			else if(rep == 2) {
				monSocket = new Socket(SERVERIP, 4000);
			}
			else if(rep == 3) {
				monSocket = new Socket(SERVERIP, 5000);
			}
		}catch(IOException e2) {
			System.out.println("Unnable to connect to 127.0.0.1");
			return;
		}
		
	}
}
