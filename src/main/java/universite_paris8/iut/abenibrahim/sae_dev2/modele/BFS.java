package universite_paris8.iut.abenibrahim.sae_dev2.modele;

import java.util.LinkedList;
import java.util.Queue;

public class BFS {
    private static final int[][] DIRECTIONS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public static Noeud bfs(int[][] carte, int xDepart, int yDepart, int xCible, int yCible) {
        int nbLignes = carte.length;
        int nbColonnes = carte[0].length;
        boolean[][] visite = new boolean[nbLignes][nbColonnes];
        Queue<Noeud> file = new LinkedList<>();

        Noeud depart = new Noeud(xDepart, yDepart, null);
        visite[yDepart][xDepart] = true;
        file.offer(depart);

        while (!file.isEmpty()) {
            Noeud courant = file.poll();

            if (courant.x == xCible && courant.y == yCible) {
                return courant;
            }

            for (int[] direction : DIRECTIONS) {
                int nouvelleX = courant.x + direction[0];
                int nouvelleY = courant.y + direction[1];

                if (deplacementValide(carte, nouvelleX, nouvelleY, visite)) {
                    Noeud nouveauNoeud = new Noeud(nouvelleX, nouvelleY, courant);
                    visite[nouvelleY][nouvelleX] = true;
                    file.offer(nouveauNoeud);
                }
            }
        }

        return null;
    }

    private static boolean deplacementValide(int[][] carte, int x, int y, boolean[][] visite) {
        int nbLignes = carte.length;
        int nbColonnes = carte[0].length;

        return x >= 0 && x < nbColonnes && y >= 0 && y < nbLignes && carte[y][x] != 2 && !visite[y][x];
    }

}

