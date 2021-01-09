package service;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Properties;

import java.util.*;  
import javax.mail.*;  
import javax.mail.internet.*;
import javax.activation.*;

import client.Abonne;
import produit.Document;
import server.Service;

public class Emprunt extends Service implements Runnable {
	/* Service de Emprunt de la mediateque, le thread s'occuppe d'un client a la fois
	 * La comunication vers le client se fait en deux temps un premier String pour donner l'etat de la communication:
	 * Nouveau debut de communication(NOUVEAU), Communication en cours (ENCOURS), et communication finit(FINIS)
	 * et une deuxieme chaine de characteres pour donner le message a afficher a l'utilisateur
	 * Connection au PORT 4000
	 *  */
	
	public Emprunt(Socket s, HashMap<String, Abonne> abo, HashMap<String, Document> docs) {
		this.thread = new Thread(this);
		this.clientSoc = s;
		this.abonnes = abo;
		this.documents = docs;
		this.thread.start();
	}

	@Override
	public void run() {
		try {
			this.sin = new BufferedReader(new InputStreamReader(this.clientSoc.getInputStream()));
			this.cout = new PrintWriter (this.clientSoc.getOutputStream(), true);
			
			if (setNumAbo() == CODERREUR) { // Annulation de connexion
				terminate();
				return;
			}
			
			cout.write(NOUVEAU + NORESPONSE);
			cout.println("Bonjour " + this.abonne.getPrenom() + ", bienvenue au service Emprunt.");
			
			String stock = this.getStock();
			String message = "Que voulez-vous emprunter (0 pour annuler):\n" + stock;
			
			String numeroDocument = this.getDocumentChoice(ENCOURS + LONGMESSAGE, message);
			if (numeroDocument.equals("0")) {
				terminate(); 
				return;
			}
			try {
				this.documents.get(numeroDocument).empruntPar(this.abonne);
				cout.write(NOUVEAU + NORESPONSE);
				cout.println("Vous avez louer le le document numero: " + numeroDocument);
			} catch(EmpruntException e) {
				
				String reponse = "";
				cout.write(NOUVEAU + NORESPONSE);
				cout.println(e);
				cout.write(ENCOURS + NORESPONSE);
				cout.println("Voulez-vous recevoir un mail quand ce produit revient ? (oui/non):");
				do {
					cout.write(ENCOURS + NOMESSAGE);
					cout.println("");
					reponse = sin.readLine();
				} while (!reponse.equals("oui") && !reponse.equals("non"));
				
				if (reponse.equals("oui")) {
					cout.write(ENCOURS + NORESPONSE);
					this.sendEmail();
					cout.println("Vous serez donc prevenu par mail");
				}
			}
			cout.write(ENCOURS + NORESPONSE);
			cout.println("Merci de votre visite, a bientot.");
			terminate();
			
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public void sendEmail() {
		// Ne fonctionne que sous Java 8 !!
		final String user = "roreply.mediatheque@gmail.com";
		final String password = "Motdepassedelamediateque";
		String host = "smtp.gmail.com";
		
		String to = this.abonne.getEmail();
		
		Properties props = new Properties();
		
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
		
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
		    message.setSubject("MY FUCKING CONTENT");  
		    message.setText("LE VOILA TON PUTAIN DE MAIL SALE FILS DE PUTE,\nVA BIEN NIQUER TA MERE LA PROCHAINE FOIS !");
		    Transport.send(message);
		    System.out.println("Mail envoyer !");
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
