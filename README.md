
# Générateur de noms — INF1004

Programme Java en console qui génère des noms aléatoires à l'aide d'une chaîne de Markov construite à partir d'une banque de noms.

## Fonctionnement

Le programme analyse les transitions entre lettres dans une liste de noms existants pour construire une matrice de probabilités. Cette matrice est ensuite utilisée pour générer de nouveaux noms qui "ressemblent" aux noms d'origine.

## Fonctionnalités

- Génération de N noms aléatoires à la demande
- Ajout d'un nom à la banque et recalcul de la matrice
- Chargement d'un fichier `.txt` de noms personnalisé
- Affichage de la matrice de probabilités de transition

## Technologies

- Java (console)
- Chaîne de Markov

## Lancer le projet

```bash
# Compiler
javac *.java

# Exécuter
java App
```

Un fichier `names.txt` doit être présent dans le même répertoire — un nom par ligne.

## Structure

```
App.java                 — point d'entrée et menu
MarkovNameGenerator.java — logique Markov et génération
Interval.java            — représentation des intervalles de probabilité
names.txt                — banque de noms par défaut
```

---

Projet universitaire solo — cours INF1004, UQTR.
