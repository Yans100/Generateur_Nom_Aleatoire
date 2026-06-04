/*--------------------------------------------------------------------------------------------------------------
 *                       INF1004   Structures de données et algorithmes
 *                       SESSION:  hiver 2023
 *                       TRAVAIL PRATIQUE #2 - GÉNÉRATEUR DE NOMS
 *                       Yannick Poirier
 * --------------------------------------------------------------------------------------------------------------
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
//classe Java qui permet de générer des noms aléatoires en utilisant une chaîne de Markov
public class MarkovNameGenerator {
    // variable TOTALCHAR à '%' en tant que caractère spécial pour le total
    private Character TOTALCHAR = '%';
    // ArrayList de Strings contenant les noms utilisés pour la génération
    private ArrayList<String> names;
    // HashMap contenant une autre HashMap de caractères avec un objet Interval
    private HashMap<Character, HashMap<Character, Interval>> matrix;


//constructeur
    public MarkovNameGenerator(ArrayList<String> names) {
        // variable names avec les noms fournis en argument
        this.names = names;
        // matrice utilisée pour la génération de noms
        this.matrix = generateMatrixWithInterval();
    }
/*-----------------------------------------------------------------------------------------
  Methode : extractUniqueCharacters()

  Méthode permettant d'extraire les caractères des noms fournis

  Entrée: rien

  Sortie: Retourne la liste uniqueCharacters contenant tous les caractères uniques extraits
  -----------------------------------------------------------------------------------------
 */
    private List<Character> extractUniqueCharacters() {
        List<Character> uniqueCharacters = new ArrayList<>();
        uniqueCharacters.add(' '); // Représente l'absence de char
        for (String name : names) {
            name = name.toLowerCase(); // Insensible à la case
            for (char letter : name.toCharArray()) {
                // Ignorer les caractères qui ne sont pas des lettres et éviter les doublons
                if (Character.isLetter(letter) && !uniqueCharacters.contains(letter)) {
                    uniqueCharacters.add(letter);
                }
            }
        }
        return uniqueCharacters;
    }

      /*---------------------------------------------------------------------------------------
       Methode : generateMatrixWithCount()

       Méthode permettant de générer une matrice des caractères présents dans les noms fournis

       Entrée: rien

       Sortie: retourne la matrice de compte des caractères présents dans les noms fournis
       -----------------------------------------------------------------------------------------
       */
    private HashMap<Character, HashMap<Character, Integer>> generateMatrixWithCount() {
        HashMap<Character, HashMap<Character, Integer>> matrix = new HashMap<>();
        List<Character> keys = extractUniqueCharacters();

        for (Character key: keys) {
            // Initialiser une matrice avec les chars uniques de la banque de noms
            matrix.put(key, new HashMap<>());
            for (Character k: keys) {
                matrix.get(key).put(k, 0);
            }
            matrix.get(key).put(TOTALCHAR, 0); // Initialisation du compteur total du caractère à 0
        }

        for (String name : names) {
            Character previousChar = ' ';
            name += previousChar; // Le dernier char est un vide
            for (Character letter: name.toLowerCase().toCharArray()) {
                matrix.get(previousChar).put(letter, matrix.get(previousChar).get(letter) + 1);
                matrix.get(previousChar).put(TOTALCHAR, matrix.get(previousChar).get(TOTALCHAR) + 1);
                previousChar = letter;
            }
        }

        return matrix;
    }

    /*---------------------------------------------------------------------------------------
    Methode : generateMatrixWithInterval()

    Cette méthode calcul les intervalles de probabilité et génère une matrice de transition

    Entrée: rien

    Sortie: Retourne la matrice de transition avec les intervalles de probabilité
    -----------------------------------------------------------------------------------------
   */
    private HashMap<Character, HashMap<Character, Interval>> generateMatrixWithInterval() {
        HashMap<Character, HashMap<Character, Integer>> origin = generateMatrixWithCount();
        HashMap<Character, HashMap<Character, Interval>> matrix = new HashMap<>();

        for (Map.Entry<Character, HashMap<Character, Integer>> fistEntry : origin.entrySet()) {
            // Calculer la probabilité pour chaque transition
            Double prob = 1.0 / Double.valueOf(fistEntry.getValue().get(TOTALCHAR));
            Double min = 0.0, max = 0.0;
            matrix.put(fistEntry.getKey(), new HashMap<>());
            for (Map.Entry<Character, Integer> secondEntry : fistEntry.getValue().entrySet()) {
                if (secondEntry.getKey().equals(TOTALCHAR)) { continue; }
                // Calculer l'intervalle de probabilité pour la transition courante
                max += prob * secondEntry.getValue();
                matrix.get(fistEntry.getKey()).put(secondEntry.getKey(), new Interval(min, max));
                min = max;
            }
        }

        return matrix;
    }
    /*---------------------------------------------------------------------------------------
   Methode : regenerateMatrix(ArrayList<String> names)

   Cette méthode permet de mettre à jour la matrice Markov avec une nouvelle liste de noms

   Entrée: names La nouvelle liste de noms.

   Sortie: rien
   -----------------------------------------------------------------------------------------
   */
    public void regenerateMatrix(ArrayList<String> names) {
        this.names = names;
        matrix = generateMatrixWithInterval();
    }

    /*---------------------------------------------------------------------------------------
    Methode : showMatrix()

    Affiche la matrice de transition sous forme de tableau.

    Entrée: rien

    Sortie: rien
    -----------------------------------------------------------------------------------------
    */
    public void showMatrix() {
        String formatChar = "%-12c|"; // Format pour l'affichage des caractères
        String formatString = "%-12s|"; // Format pour l'affichage des intervalles
        // Récupère la nouvelle matrice de transition
        HashMap<Character, HashMap<Character, Interval>> matrix = generateMatrixWithInterval();
        // Affiche les en-têtes de colonne
           var test = matrix.get(' ').keySet();
        for (Character item : test) {
            if (item.equals(' ')) {
                System.out.format(formatChar, item);
                System.out.format(formatString, "vide");
                continue;
            }
            System.out.format(formatChar, item);
        }
        System.out.println("");
        // Affiche le reste de la matrice
        for (Map.Entry<Character, HashMap<Character, Interval>> firstEntry : matrix.entrySet()) {
            var leftHeader = firstEntry.getKey().toString();
            if (firstEntry.getKey().equals(' ')) {
                leftHeader = "vide";
            }
            System.out.format(formatString, leftHeader);
            
            for (Map.Entry<Character, Interval> secondEntry : firstEntry.getValue().entrySet()) {
                System.out.format(formatString, secondEntry.getValue().toString());
            }
            System.out.println("");
        }
    }
    /*---------------------------------------------------------------------------------------
    Methode : generateRandomName()

    Génère un nom aléatoire basé sur la matrice de probabilité construite à partir des noms passés en paramètre

    Entrée: rien

    Sortie: return Le nom aléatoire généré
    -----------------------------------------------------------------------------------------
    */
    public String generateRandomName() {
    	String randomName = "";
        Random random = new Random();
        char current = ' ';
        boolean endOfName = false; // Indique si la fin du nom est atteinte
        //boucle qui Continue la générà du nom tant que la fin n'est pas atteinte
        while (!endOfName) {
            HashMap<Character, Interval> possibleNext = matrix.get(current);
            double index = 0.0 + (1.0 - 0.0) * random.nextDouble();
            // Parcourt les caractères possibles pour déterminer le prochain caractère à ajouter
            for (Map.Entry<Character, Interval> entry : possibleNext.entrySet()) {
                char next = entry.getKey();
                Interval interval = entry.getValue();
                if (index >= interval.getMin() && index < interval.getMax()) {
                	if (next == ' ') {
                		endOfName = true;
                		break;
                	}
                    current = next;
                    randomName += current;
                    break;
                }
            }
        }
        return randomName;
    }
}
