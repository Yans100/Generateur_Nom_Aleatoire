/*--------------------------------------------------------------------------------------------------------------
*                       INF1004   Structures de données et algorithmes
*                       SESSION:  hiver 2023
*                       TRAVAIL PRATIQUE #2 - GÉNÉRATEUR DE NOMS
*                       Yannick Poirier
* --------------------------------------------------------------------------------------------------------------
*/
//classe Interval qui permet de représenter un intervalle de nombres réels
public class Interval {
    private Double max;
    private Double min;

    // Constructeur qui prend les bornes de l'intervalle en paramètre.
    public Interval(Double min, Double max) {
        if (min.equals(max)) {
            this.min = 0.0;
            this.max = 0.0;
        } else {
            this.min = min;
            this.max = max;
        }
    }

    // Getter pour la borne supérieure exclue.
    public Double getMax() {
        return max;
    }

    // Getter pour la borne inférieure incluse.
    public Double getMin() {
        return min;
    }

    // Redéfinition de la méthode toString() pour afficher l'intervalle sous forme de chaîne de caractères.
    @Override
    public String toString() {
        if (max > 0) {
            return "[" + Math.round(min * 100.0) / 100.0 + "; " + Math.round(max * 100.0) / 100.0 + "]";
        }
        return " - ";
    }
}