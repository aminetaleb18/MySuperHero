# MySuperHero
# Presentation  du projet Mon Super Hero :

Ce  projet  a pour but de développer une application sous Android Studio en java, permettant d'afficher les 731 supers heros
et  leurs caractéristiques.

Ce projet utilise  une clean architecture et un pattern MVC dans une application android codé en java.

Ce projet  utilise une  rest API issu du serveur hébergé à la'adresse suivante :  https://superheroapi.com/
# Prerequis :

  .  Installation de Android Studio
  .  Récupérer la branche de développment : https://github.com/aminetaleb18/MySuperHero
  .  Pour accéder aux Api rest du serveur, il est nécessaire de récupérer une clé que l'on obtient en s'enregistrant via son compte      facebook. On obtien une base url du type BASE_URL = "https://superheroapi.com/api/588673XXXXXX/";
  
  ![Baseurl](https://user-images.githubusercontent.com/62145128/82763219-5453ec80-9e06-11ea-86b2-d7a56b4df42c.png)

  
# Consignes respectées : 

  . Clean architecture & MVC
  
  . Appels REST
  
  . Ecrans : 2 activités
  
  . Affichage d'une liste dans un RecyclerView permettant d'afficher les images et noms de chaque super héro dans un item spécifique.
  
  . Affichage des caractéristiques du super héro, image, force, vitesse ...
  
  # Fonctionnalités :
  
  . Icône de démarrage de l'application avec un logo marvel
  
  ![BureauHero](https://user-images.githubusercontent.com/62145128/82762611-48fec200-9e02-11ea-9f5a-4f666776e5a8.png)
    
  . Splash affichant une image avec la marque "Marvel"
  
  ![SlashAmine](https://user-images.githubusercontent.com/62145128/82762673-a4c94b00-9e02-11ea-8a29-6386c6aacd93.png)

  . Chargement des données issus du serveur 
  
  Le chargment initial consiste à récupérer les données considérées comme données basique qui renvoi
   l'url de l'image du super héro et son nom
   
   Ces données sont récupéreés par rest Api, sur le répertoire un gson://superheroapi.com/api/access-token/Id/image
        
          . Id correspond à l'identifiant ou numéro du super héro.
         
          . répertoire image contenant les données
          
          ex : renvoi un gson file pour le super héro numéro 2 {"response":"success","id":"2","name":"AbeSapien","url":"https:\/\/www.superherodb.com\/pictures2\/portraits\/10\/100\/956.jpg"}
          
  Dans l'image suivante, on voit le chargement des 731 super héro, ce chargement est assez long, un Toast indique le chargement du héros encours et indique aussi qu'il faut patienter
  
  ![ScreenshotLoading](https://user-images.githubusercontent.com/62145128/82763103-71d48680-9e05-11ea-81e5-02bf0fa6f038.jpg)
  
