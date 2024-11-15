package universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.Map;

public class TerrainManager {
    private final int longueur;
    private final int largeur;
    private final Map map;

    public TerrainManager(int longueur, int largeur) {
        this.longueur = longueur;
        this.largeur = largeur;
        this.map = new Map();
    }

    public boolean dansTerrain(int x, int y) {
        return x >= 0 && x < longueur && y >= 0 && y < largeur;
    }

    public boolean verifierCollisions(int x, int y) {
        return map.verifierCollisions(x, y);
    }

    public Map getMap() {
        return map;
    }
}