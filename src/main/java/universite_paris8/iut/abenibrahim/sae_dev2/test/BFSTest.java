package universite_paris8.iut.abenibrahim.sae_dev2.test;

import org.junit.jupiter.api.Test;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.BFS;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Noeud;

import static org.junit.jupiter.api.Assertions.*;

class BFSTest {

    @Test
    void testBFS_CheminExistant() {
        int[][] carte = {
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        int xDepart = 0, yDepart = 0;
        int xCible = 3, yCible = 0;

        Noeud resultat = BFS.bfs(carte, xDepart, yDepart, xCible, yCible);
        assertNotNull(resultat);
        assertEquals(xCible, resultat.x);
        assertEquals(yCible, resultat.y);
    }

    @Test
    void testBFS_PasDeCheminExistant() {
        int[][] carte = {
                {0, 0, 0, 0},
                {0, 2, 2, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        int xDepart = 0, yDepart = 0;
        int xCible = 3, yCible = 0;

        Noeud resultat = BFS.bfs(carte, xDepart, yDepart, xCible, yCible);
        assertNull(resultat);
    }

    @Test
    void testBFS_CasLimite() {
        int[][] carte = {
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        int xDepart = 0, yDepart = 0;
        int xCible = 0, yCible = 0;

        Noeud resultat = BFS.bfs(carte, xDepart, yDepart, xCible, yCible);
        assertNotNull(resultat);
        assertEquals(xDepart, resultat.x);
        assertEquals(yDepart, resultat.y);
    }

    @Test
    void testBFS_CarteVide() {
        int[][] carte = {};
        int xDepart = 0, yDepart = 0;
        int xCible = 0, yCible = 0;

        Noeud resultat = BFS.bfs(carte, xDepart, yDepart, xCible, yCible);
        assertNull(resultat);
    }

    @Test
    void testBFS_CoordonneesSortantDeLaCarte() {
        int[][] carte = {
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        int xDepart = -1, yDepart = 0;
        int xCible = 4, yCible = 0;

        Noeud resultat = BFS.bfs(carte, xDepart, yDepart, xCible, yCible);
        assertNull(resultat);
    }
}