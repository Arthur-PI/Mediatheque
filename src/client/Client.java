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
	private static final int ENCOURS = 0b000001;
	private static final int NOUVEAU = 0b000010;
	private static final int FINI = 0b000100;
	private static final int NORESPONSE = 0b001000;
	private static final int LONGMESSAGE = 0b010000;
	private static final int NOMESSAGE = 0b100000;
	private static final int MUSIQUE = 0b1000000;
	
	public static void main(String args[]) throws IOException, InterruptedException {
		Socket monSocket;
		String serviceNumber;
		int etat;
		Scanner sc = new Scanner(System.in);
		int musiqueDuration;
		MP3 clip = null;
		
		if (args.length == 0 || Integer.parseInt(args[0]) < 0 || Integer.parseInt(args[0]) > 3)
			serviceNumber = homeScreen(sc);
		else
			serviceNumber = args[0];
		
		monSocket = socketService(serviceNumber);
		
		if (monSocket == null) 
			return;
		
		BufferedReader sin = new BufferedReader(new InputStreamReader(monSocket.getInputStream()));
		PrintWriter cout = new PrintWriter(monSocket.getOutputStream());
		
		etat = NOUVEAU;
		String inputMessage = "";
		String outputMessage = "";
		

		System.out.println("Debut de la transmission");
		
		while((etat & FINI) != FINI) {
			etat = sin.read();
			
			if ((etat & NOUVEAU) == NOUVEAU)
				clearScreen();
			if ((etat & FINI) == FINI)
				continue;
			
			if ((etat & MUSIQUE) == MUSIQUE) {
				clip = startMusique();
				musiqueDuration = sin.read();
				System.out.println(sin.readLine());
				Thread.sleep(musiqueDuration * 1000);
				if(clip != null)
					clip.close();
				continue;
			}
			
			inputMessage = "";
			
			if ((etat & LONGMESSAGE) == LONGMESSAGE)
				while(sin.ready())
					inputMessage += sin.readLine() + "\n";
			else
				inputMessage = sin.readLine();
			
			if ((etat & NOMESSAGE) != NOMESSAGE)
				System.out.println(inputMessage);
			
			if ((etat & NORESPONSE) == NORESPONSE)
				continue;
			
			outputMessage = getUserInput(sc);
			
			cout.println(outputMessage);
			cout.flush();
		}
		
		System.out.println("Fin de la transmission");
		
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
	
	public static MP3 startMusique(){
		/*
		 * Joue la musique d'attente et retourne le player
		 * */
		MP3 music = new MP3("../bestmusicever.mp3");
		music.play();
	    return music;
	}
}
