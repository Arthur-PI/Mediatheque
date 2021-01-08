# Mediatheque
Ok ya trop de choses a dire

Pour commencer, j'ai terminer de configurer la connection entre le client et le differents services.
Pour que tu puisse travailler dessus je vais te donner toutes les petites info a savoir;
Deja de 1, la communication. Pour communiquer avec le client tu vas utiliser les variables
`sin` et `cout`.`cout` c'est pour ecrire au client (c'est l'equivalent de System.out)
pour envoyer un String tu fais cout.println(monString) et pour envoyer un int tu fais cout.write(monInt).
`sin` c'est pour recevoir les informations depuis le client, tu fais toujours monString = sin.readLine().
Apres t'as quelques variable comme `abonne` l'abonne qui est en train d'utiliser le service, t'as `abonnes`une hashmap de tout les abonnes et `documents` une hashmap de tout les DVD, tu peux tout retrouver dans la classe `Service` du package `server`.
Ensuite, le fonctionement du programme, c'est une boucle qui fais toujours la meme chose. D'abord t'envoie le mode de communication au client avec les variable:
`ENCOURS`: Evite que le terminal du client ne clear
`NOUVEAU`: Fais en sorte que terminal du client clear
`FINI`: Termine la communication avec le client
`NORESPONSE`: Evite que le client ne reponde et revient au debut
`LONGMESSAGE`: Permet d'envoyer un message sur plusieurs lignes
Ex: cout.write(NOUVEAU), ou tu peux les additionner: cour.write(NOUVEAU + NORESPONSE)

Ensuite le deuxieme trucs que t'envoie c'est le message qui est UNE SEULE chaine de charactere. C'est ce que le client verra sur son terminal.
Si tu veux envoyer une String contenant des `\n` donc sur plusieurs lignes faut mettre l'option LONGMESSAGE avant.
Ex: cout.println(monMessage)

Apres ca si tu n'as pas mis l'option NORESPONSE ou FINI le client vas automatiquement repondre et tu n'a plus qu'as attendre sa reponse en mettant: sin.readLine()

Voila voila, j'espere que c'est clair sinon hesite pas a me dm bg. Bisous <3
