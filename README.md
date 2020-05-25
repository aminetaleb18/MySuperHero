# MySuperHero
# Presentation  du projet Mon Super Hero :

Ce  projet  a pour but de développer une application sous Android Studio en java, permettant d'afficher les 731 supers heros
et  leurs caractéristiques.

Ce projet utilise  une clean architecture et un pattern MVC dans une application android codé en java.
sous le repository Git https://github.com/aminetaleb18/MySuperHero.git

Ce projet  utilise une  rest API issu du serveur hébergé à la'adresse suivante :  https://superheroapi.com/
# Prerequis :

  .  Installation de Android Studio
  .  Récupérer la branche de développment : https://github.com/aminetaleb18/MySuperHero
  .  Pour accéder aux Api rest du serveur, il est nécessaire de récupérer un access_token ou clé que l'on obtient en s'enregistrant via son compte facebook. On obtient une base url du type BASE_URL = "https://superheroapi.com/api/588673XXXXXX/";
  
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

  . CHARGEMENT DES DONNEES ISSUS DU SERVEUR :   
  
  Le chargment initial consiste à récupérer les données considérées comme données basique qui renvoi
   l'url de l'image du super héro et son nom
   
   Ces données sont récupéreés par rest Api, sur le répertoire un gson://superheroapi.com/api/access-token/Id/image
        
          . Id correspond à l'identifiant ou numéro du super héro.
         
          . répertoire contenant l'url de image et le nom du super héro
                   
          ex : renvoi un gson file pour le super héro numéro 2 {"response":"success","id":"2","name":"AbeSapien","url":"https:\/\/www.superherodb.com\/pictures2\/portraits\/10\/100\/956.jpg"}
          
          Le chargement de l'image se réalise et s'affiche grâce à l'utilisation de la librairie Picasso version:'2.71828
          
          ex :  Picasso.get().load(imageUri).into(holder.imHero);
          où imageUri est le path de l'image sur le serveur et holder.imHero est une imageView.
          
  Dans l'image suivante, on voit le chargement des 732 supers héros, ce chargement est assez long, un Toast indique le chargement du héros encours<, le numéro encours et indique aussi qu'il faut patienter
  
![Screenshot_20200525_201726_com example td4](https://user-images.githubusercontent.com/62145128/82835620-de628a80-9ec4-11ea-884e-d51c1dccb387.jpg)

  . AFFICHAGE DES CARACTERISTIQUES D'UN SUPER HERO :
  
  Les caractéristiques dites détaillées de nos super héro a lieu en cliquant sur un des items, celui-ci lancera une nouvelle activité
  qui permettra de faire via nouvelle rest APi sur le répertoire "powerstats"
  
     Ces données sont récupéreés, sur le répertoire un gson://superheroapi.com/api/access-token/Id/powerstats
        
          . Id correspond à l'identifiant ou numéro du super héro.
         
          . répertoire powerstats contenant les données
          
          ex : renvoi un gson file pour le super héro numéro 2 {"response":"success","id":"2","name":"Abe Sapien","intelligence":"88","strength":"28","speed":"35","durability":"65","power":"100","combat":"85"}
          
          Les données sont l'intelligence, force, combat, puissance etc.
          
L'exemple suivant montre les caractéristiques pour le super héro dont l'Id vaut 2

![superHero2](https://user-images.githubusercontent.com/62145128/82763565-042a5980-9e09-11ea-8e31-945de3acddea.jpg)

AJOUT GESTION FAVORI

Lorsque l'utilisateur à sélectionné un super héro, il peut l'ajouter en favbori
une étoile permettra de visualiser les favoris dans la liste principale.
Un checkbox a été ajouter afin d'indiquer que le super héro devient un favori

![Screenshot_20200525_200300_com example td4](https://user-images.githubusercontent.com/62145128/82834956-ed483d80-9ec2-11ea-9815-a115c6e51a15.jpg)

Visualisation de la liste globale avec un favori

![Screenshot_20200525_201245_com example td4](https://user-images.githubusercontent.com/62145128/82835404-377dee80-9ec4-11ea-938d-9fe7cfd596e0.jpg)

RECHERCHE D'UN SUPER HERO

Un fonctionnalité de recherche a été ajouté, car il y a 732 supers héros ce qui est assez conséquent
La recherche a lieu en cliquant sur l'icône "search" en haut à droite

![Screenshot_20200525_215227_com example td4](https://user-images.githubusercontent.com/62145128/82840294-4b305180-9ed2-11ea-86d0-6ed23ccb7bbc.jpg)

Lorsqu'on appui sur l'icône, une barre de recherche permettant de faire une recherche dynamique
en fonction des caractères entrés la liste est remise à jour dynamiquement.

![Screenshot_20200525_215741_com example td4](https://user-images.githubusercontent.com/62145128/82840430-dc072d00-9ed2-11ea-8aad-c0edd04e41d0.jpg)




# EVOLUTIONS POSSIBLES

  . Passez le projet en MVVM.
  
  . Possibilité d'afficher uniquement les favoris dans une liste dédiée.
  
  . Possibilité de comparer 2 supers héros en indiquant le plus fort ou plus puissant.
  


          
  
  
  
