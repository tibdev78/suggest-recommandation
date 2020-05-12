# esgi 202005

Cours de big data pour l'ESGI de mai 2020

## Pré requis technique 

* Java 8 exactement (pas 7, pas 9 et au dela, 8 exactement)
* Un IDE, disons Intellij
* Une connexion internet 
* gradle
* git

## Installer le projet

### Cas de linux ou mac

Rien de particulier pour l'installation. 

### Cas de windows

Parce que Windows dispose de son propre FS, il faut utiliser une sorte d'adaptateur. 
* Télécharger le projet winutils ici : https://github.com/steveloughran/winutils
* Créer sur D: un répertoire Hadoop 
* Décompresser winutils dans D:\Hadoop
* Dans winutils\ supprimer tous les répertoires hadoop sauf hadoop-2.7.1
* Vous devez avoir ce chemin : D:\Hadoop\winutils\hadoop-2.7.1\bin

Si le chemin ne vous convient pas, vous modifier la valeur PATH dans la classe SparkSessionFactory

## Builder le projet 

C'est un projet gradle, rien de particulier à faire. 
* Se mettre à la racine du projet 
* gradle clean build 
* Evidemment le projet doit builder et ne doit pas lever d'erreur à l'exécution pour Spark