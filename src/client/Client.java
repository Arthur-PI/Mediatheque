package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	/*
	 * Application cliente de la mediatheque
	 * Connection via socket a l'addresse SERVERIP
	 * */
	
	private static String SERVERIP = "127.0.0.1";
	private static String ENCOURS = "en cours";
	private static String NOUVEAU = "nouveau";
	private static String FINI = "over";
	
	public static void main(String args[]) throws IOException {
		Socket monSocket;
		String serviceNumber;
		String etat;
		Scanner sc = new Scanner(System.in);
		
		if (args.length == 0 || Integer.parseInt(args[0]) < 0 || Integer.parseInt(args[0]) > 3) {
			serviceNumber = homeScreen(sc);
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
		
		
		System.out.println("Debut de la tranmission");
		
		while(!etat.equals(FINI)) {
			etat = sin.readLine();
			if (etat.equals(NOUVEAU)) { clearScreen(); }
			if (etat.equals(FINI)) {
				continue;
			}
			
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
		/*
		 * Recupere la saisie clavier de l'utilisateur dans le terminal
		 * Input: Un flux de type Scanner
		 * Output: La reponse de l'utilisateur
		 * */
		String rep = "";
		System.out.print("> ");
		
		rep = sc.nextLine();
		
		return rep;
	}
	
	public static void clearScreen() { 
		/*
		 * Fait un clear du terminal de l'utilisateur
		 * Input: None
		 * Output None
		 * */
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	}  
	
	public static String homeScreen(Scanner sc) {
		/*
		 * Procedure du choix du service par l'utilisateur si aucun fichier de configuration n'est present
		 * Input: Un flux de type Scanner
		 * Output: Une des valeur possible entre (0/1/2/3) 
		 * */
		System.out.println("Bienvenue sur l'application de votre Mediatheque preferer");
		System.out.println("Que voulez-vous faire ?");
		System.out.println("1 - Reservation");
		System.out.println("2 - Emprunt");
		System.out.println("3 - Retour");
		System.out.println("0 - Quitter");
		int rep;
		
		do {
			try {
				rep = Integer.parseInt(getUserInput(sc));
			} catch (NumberFormatException e) { 
				e.printStackTrace();
				rep = -1; 
			}
		} while (rep < 0 || rep > 3);
		
		System.out.println("You choose " + rep);
		return String.valueOf(rep);
	}
	
	public static Socket socketService(String n) {
		/*
		 * Creer un socket entre le client et le service donner en argument
		 * Input: Un numero de service
		 * Output: Un socket vers le service demander sinon null 
		 * */
		
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
