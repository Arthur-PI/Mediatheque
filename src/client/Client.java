package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	private static String SERVERIP = "127.0.0.1";
	private static String ENCOURS = "en cours";
	private static String NOUVEAU = "nouveau";
	private static String FINI = "over";
	
	public static void main(String args[]) throws IOException {
		Socket monSocket;
		String serviceNumber;
		String etat;
		
		if (args.length == 0 || Integer.parseInt(args[0]) < 0 || Integer.parseInt(args[0]) > 3) {
			serviceNumber = homeScreen();
		}
		else {
			serviceNumber = args[0];
		}
		monSocket = socketService(serviceNumber);
		if (monSocket == null) { return; }
		BufferedReader sin = new BufferedReader(new InputStreamReader(monSocket.getInputStream()));
		PrintWriter cout = new PrintWriter(monSocket.getOutputStream());
		
		etat = NOUVEAU;
		
		String inputMessage = "";
		String outputMessage = "";
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Debut de la tranmission");
		
		while(!etat.equals(FINI)) {
			etat = sin.readLine();
			System.out.println(etat);
			if (etat.equals(NOUVEAU)) { clearScreen(); }
			
			inputMessage = sin.readLine();
			
			System.out.println(inputMessage);
			
			outputMessage = getUserInput(sc);
			
			cout.println(outputMessage);
			cout.flush();
		}
		
		System.out.println("Fin de la tranmission");
		
		cout.close();
		sin.close();
		monSocket.close();
		sc.close();
		
	}
	
	public static String getUserInput(Scanner sc) {
		String rep = "";
		System.out.print("> ");
		
		rep = sc.nextLine();
		
		return rep;
	}
	
	public static void clearScreen() {  
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	}  
	
	public static String homeScreen() {
		System.out.println("Bienvenue sur l'application de votre Mediatheque preferer");
		System.out.println("Que voulez-vous faire ?");
		System.out.println("1 - Reservation");
		System.out.println("2 - Emprunt");
		System.out.println("3 - Retour");
		System.out.println("0 - Quitter");
		int rep;
		Scanner sc = new Scanner(System.in);
		
		do {
			try {
				rep = Integer.parseInt(getUserInput(sc));
			} catch (NumberFormatException e) { 
				e.printStackTrace();
				rep = -1; 
			}
		} while (rep < 0 || rep > 3);
		
		System.out.println("You choose " + rep);
		
		sc.close();
		
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
