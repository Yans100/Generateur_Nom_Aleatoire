/*---------------------------------------------------------------------------------------------------------------
 *                          INF1004   Structures de données et algorithmes
 *                          SESSION:  hiver 2023
 *                        	TRAVAIL PRATIQUE #2 - GÉNÉRATEUR DE NOMS
 * 							Yannick Poirier
 * --------------------------------------------------------------------------------------------------------------
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
/* cette classe  permet : - D'ajouter un nom et réinitialiser le générateur: permet à l'utilisateur d'ajouter un nom à la liste de noms et de réinitialiser le générateur.
                          - Lire un fichier de noms: permet à l'utilisateur de spécifier un fichier de noms à utiliser pour générer des noms.
                          - Affiche la matrice de probabilités utilisée pour générer des noms.
*/
public class App {
	static String message = "";
	
	public static void main(String[] args) throws Exception {
		// appelle la méthode readNames() pour lire le fichier de noms "names.txt" et stocke les noms dans une liste
		ArrayList<String> names = readNames("names.txt");
		MarkovNameGenerator nameGenerator = new MarkovNameGenerator(names);		
		int choix = -1;
		// affiche le menu
		do {
			System.out.println("\nGénérateur aléatoire de noms:");
			System.out.println("\n[Menu]");
			System.out.println("1: Générer des noms aléatoirement");
			System.out.println("2: Ajouter un nom et réinitialiser le générateur");
			System.out.println("3: Lire un fichier de noms (names.txt par défaut)");
			System.out.println("4: Montrer la matrice de probabilités");
			System.out.println("5: Quitter");
			System.out.println("\n" + message);
			System.out.print("\nFaites votre choix et appuyez sur ENTER: ");
			// appelle la méthode getInt() pour convertir l'entrée de l'utilisateur en entier
			choix = getInt();
			message = "";

			switch (choix) {
			case 1:
				System.out.print("Déterminer le nombre de noms à générer aléatoirement (0 pour annuler): ");
				int namesCount = getInt();
				int exit = 0;
				String error = "";
			
				if(namesCount < exit) { // vérifie si le nombre de noms est inférieur à zéro
					error += "Choix invalide! ";
				}

				if(namesCount == exit || error.length() > 0) {// vérifie si le nombre de noms est égal à zéro ou si il Y'a une erreur
					message = error + "Retour au menu.";
					break;
				}
				// génère le nombre de noms spécifié par l'utilisateur
				for (int i = 0; i < namesCount; i++) {
					String name = nameGenerator.generateRandomName();
					System.out.println(name);
				}
				
				break;
				// Ajouter un nom à la liste de noms et réinitialiser le générateur
			case 2:
				System.out.print("\nVeuillez entrer un nom à ajouter: ");
				String name = getString();
				names.add(name);
				nameGenerator = new MarkovNameGenerator(names);
				message = "Le nom " + name + " a été ajouté et le générateur a été réinitialisé.";
				break;

			case 3:
				// Lire une liste de noms depuis un fichier texte
				System.out.print("\nVeuillez entrer le nom du fichier contenant la banque de noms: ");
				String fileName = getString();
				
				if (!fileExists(fileName)) {
					message = "Fichier non trouvé.";
					break;
				}
				
				names = readNames(fileName);
				nameGenerator = new MarkovNameGenerator(names);
				message = "Le générateur a été réinitialisé à partir des noms fournis par le fichier: " + fileName;
				break;

			case 4:
				// Affichage de la matrice de probabilités
				nameGenerator.showMatrix();
				break;

			case 5:
				System.out.println("\nAu revoir!");
				break;

			default:
				message = "Choix invalide!  Veuillez recommencer.";
				break;
			}
		} while (choix != 5);

		System.exit(0);
	}
	/*---------------------------------------------------------------------------------------
    Methode : readNames(String fileName)

    Cette méthode lit les noms stockés dans un fichier et les ajoute à une liste.

    Entrée: fileName le nom du fichier contenant les noms

    Sortie: une liste de chaînes de caractères contenant les noms
    -----------------------------------------------------------------------------------------
    */
	public static ArrayList<String> readNames(String fileName) {
		ArrayList<String> names = new ArrayList<String>();
		
		try {
			// Création d'un objet File à partir du nom de fichier spécifié
			File namesFile = new File(fileName);
            // Création d'un objet Scanner pour lire le contenu du fichier
			Scanner namesReader = new Scanner(namesFile);
            // Lecture des lignes une par une et ajout des noms à la liste
			while (namesReader.hasNextLine()) {
				String name = namesReader.nextLine();
				names.add(name);
			}
			namesReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return names;
	}
	/*---------------------------------------------------------------------------------------
    Methode : fileExists(String fileName)

	Cette fonction vérifie si un fichier existe à l'emplacement spécifié par le nom de fichier

    Entrée: fileName le nom du fichier contenant les noms

    Sortie: Elle renvoie "true" si le fichier existe et "false" sinon
    -----------------------------------------------------------------------------------------
    */
	public static boolean fileExists(String fileName) {
		File file = new File(fileName);
		if(file.isFile()) { 
		    return true;
		}
		
		return false;
	}

	/*---------------------------------------------------------------------------------------
    Methode : getString()

    Cette méthode lit une ligne de texte entrée par l'utilisateur à partir de la console et la retourne sous forme de chaîne de caractères.

    Entrée: rien

    Sortie: retourne la chaîne de caractères lue.
    -----------------------------------------------------------------------------------------
    */
	public static String getString() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();

		return input;
	}
	/*---------------------------------------------------------------------------------------
    Methode : getInt()

    Cette méthode lit une ligne de texte entrée par l'utilisateur à partir de la console
     et essaie de la convertir en entier.

    Entrée: rien

    Sortie: Si la conversion échoue, elle retourne 0.
    -----------------------------------------------------------------------------------------
    */
	public static int getInt() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();

		try {
			return Integer.parseInt(input);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
