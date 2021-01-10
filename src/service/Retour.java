package service;

import java.io.*;
import java.net.Socket;
import java.util.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import client.Abonne;
import produit.Document;
import server.Service;

public class Retour extends Service implements Runnable{
	/* Service de Retour de la mediateque, le thread s'occuppe d'un client a la fois
	 * Connection au PORT 5000
	 *  */
	
	public Retour(Socket s, HashMap<String, Abonne> abo, HashMap<String, Document> docs) {
		this.thread = new Thread(this);
		this.clientSoc = s;
		this.abonnes = abo;
		this.documents = docs;
		this.thread.start();
	}

	@Override
	public void run() {
		try {
			// ---- CREATION DES FLUX DE COMMUNICATION AVEC L'UTILISATEUR ----
			this.initFlux();
			
			// ---- MESSAGE DE BIENVENUE ----
			cout.write(NOUVEAU + NORESPONSE);
			cout.println("Bienvenue au service Emprunt.");
			
			String message = "Donnez le numero du document que vous voulez rendre:";
			
			// ---- RECUPERATION DU NUMERO DE DOCUMENT A RENDRE ----
			String numeroDocument = this.getDocumentChoice(ENCOURS, message);
			if (numeroDocument.equals("0")) {
				terminate();
				return;
			}
			
			
			Document doc = this.documents.get(numeroDocument);
			
			// ---- PROCEDURE DE RETOUR DU DOCUMENT ----
			doc.retour();
			// ---- MESSAGE DE FIN + FERMETURE DES FLUX ----
			cout.write(ENCOURS + NORESPONSE);
			cout.println("Merci de votre visite, a bientot.");
			terminate();
			
			// ---- PROCEDURE D'ENVOIE DES EMAIL AU ABONNE DANS LA LISTE D'ATTENTE DU DOCUMENT ----
			ArrayList<String> mailList = doc.getMailList();
			for (String email: mailList) {
				this.sendEmail(email, "Alerte médiatèque !", "Bonjour,\nLe produit que vous attendiez viens d'être retourné, vous pouvez le reserver dés maintenant!\nBonne journée.");
			}
			return;
		} catch (IOException e) { e.printStackTrace(); }
	}

	public void sendEmail(String email, String titre, String msg) {
		/*
		 * Permet d'envoyer un mail a l'abonne 
		 * Input: Une adresse email, le message et le titre de l'email
		 * Output: rien
		 * */
		String user = "roreply.mediatheque@gmail.com";
		String password = "Motdepassedelamediateque";
		String host = "smtp.gmail.com";
		
		String to = email;
		
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
		    message.setSubject(titre);  
		    message.setText(msg);
		    Transport.send(message);
		    System.out.println("Mail envoyer !");
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
