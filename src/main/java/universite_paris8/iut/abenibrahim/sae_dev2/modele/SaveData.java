package universite_paris8.iut.abenibrahim.sae_dev2.modele;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Ennemi;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;

import java.io.Serializable;
import java.util.List;

public class SaveData implements Serializable {
    private int joueurX, joueurY, joueurPv;
    private List<EnnemiData> ennemisData;
    private int[][] mapData;
    private int[][] mapData2;


    public static class EnnemiData implements Serializable {
        private final int ennemiX;
        private final int ennemiY;
        private final int ennemiPv;

        public EnnemiData(int x, int y, int pv) {
            this.ennemiX = x;
            this.ennemiY = y;
            this.ennemiPv = pv;
        }

        public int getEnnemiX() {
            return ennemiX;
        }

        public int getEnnemiY() {
            return ennemiY;
        }

        public int getEnnemiPv() {
            return ennemiPv;
        }
    }

    public SaveData(Environnement env) {
        Joueur joueur = env.getActeurManager().getJoueur();
        this.joueurX = joueur.getX();
        this.joueurY = joueur.getY();
        this.joueurPv = joueur.getPv();


        this.ennemisData = env.getActeurManager().getEnnemis().stream()
                .map(ennemi -> new EnnemiData(ennemi.getX(), ennemi.getY(), ennemi.getPv()))
                .toList();


        this.mapData = env.getTerrainManager().getMap().getTab();
        this.mapData2 = env.getTerrainManager().getMap().getTab2();
    }


    public int getJoueurX() {
        return joueurX;
    }

    public int getJoueurY() {
        return joueurY;
    }

    public int getJoueurPv() {
        return joueurPv;
    }


    public List<EnnemiData> getEnnemisData() {
        return ennemisData;
    }


    public int[][] getMapData() {
        return mapData;
    }

    public int[][] getMapData2() {
        return mapData2;
    }
}
