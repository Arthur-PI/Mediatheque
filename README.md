# Mediatheque
Bonjour bienvenue dans votre mediateque preferée.
Avant de commencer je vous pris de bien vous assurer que vous utiliser JAVA 8 pour votre execution autrement cela pourrait vous provoquer des erreurs.

J'ai fournis des fichier de lancement pour Windows dans le dossier `bin` avec 4 scripts qui correspondent resperctivement a:
- Lancement du serveur : appServeur.bat
- Lancement de l'application de reservation a distance: appClient.bat
- Lancement de l'application d'emprunt sur la borne: borneEmprunt.bat
- Lancement de l'application de retour sur la borne: borneRetour.bat

Il y a les memes scripts pour unix dans `bin unix`

Avant de d'executer les scripts vous devez vous assurez que la variable d'environement %JAVA_HOME% existe bien
pour se faire ouvrez un CMD en mode administrateur et taper la command `echo %JAVA_HOME%` si le resultat est le chemin
de votre JDK java alors c'est bon sinon taper `setx -m JAVA_HOME "C:\Progra~1\Java\jdk1.8.0_XX"` avec votre version de JDK java a la fin (regarder dans Program Files/Java).

Vous etes fin pret vous pouvez maintenant double cliquer sur les .bat en commancant par le serveur.
ATTENTION, quand vous ouvrez le projet avec eclipse les script dans `bin/` sont supprimer alors je vous en met de rechange dans un dossier `rechange`