package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {
	private static String SERVERIP = "127.0.0.1";
	
	public static void main(String args[]) {
		Socket monSocket;
		String serviceNumber;
		if (args.length == 0) {
			serviceNumber = homeScreen();
		}
		else {
			serviceNumber = args[0];
		}
		monSocket = socketService(serviceNumber);
		
	}
	
	public static String homeScreen() {
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
		
		return String.valueOf(rep);
		
	}
	
	public static Socket socketService(String n) {
		if (n.equals("0")) {
			return null;
		}
		
		try {
			if (n.equals("1")) {
				return new Socket(SERVERIP, 3000);
			}
			else if (n.equals("2")) {
				return new Socket(SERVERIP, 4000);
			}
			else {
				return new Socket(SERVERIP, 5000);
			}
		} catch (IOException e2) {
			System.out.println("Unnable to connect to 127.0.0.1");
			return null;
		}
	}
}
