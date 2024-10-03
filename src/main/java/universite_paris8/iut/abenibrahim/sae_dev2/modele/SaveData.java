package universite_paris8.iut.abenibrahim.sae_dev2.modele;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Ennemi;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;

import java.io.Serializable;

public class SaveData implements Serializable {
    private int joueurX, joueurY, joueurPv;
    private int ennemiX, ennemiY, ennemiPv;
    private int[][] mapData;
    private int[][] mapData2;

    public SaveData(Environnement env){
        Joueur joueur = env.getGuts();
        this.joueurX = joueur.getX();
        this.joueurY = joueur.getY();
        this.joueurPv = joueur.getPv();

        Ennemi ennemi = env.getEnnemi();
        this.ennemiX = ennemi.getX();
        this.ennemiY = ennemi.getY();
        this.ennemiPv = ennemi.getPv();

        this.mapData = env.getMap().getTab();
        this.mapData2 = env.getMap().getTab2();
    }

    public int getEnnemiPv() {
        return ennemiPv;
    }

    public int getEnnemiX() {
        return ennemiX;
    }

    public int getEnnemiY() {
        return ennemiY;
    }

    public int getGutsPv() {
        return joueurPv;
    }

    public int getGutsX() {
        return joueurX;
    }

    public int getGutsY() {
        return joueurY;
    }

    public int[][] getMapData() {
        return mapData;
    }

    public int[][] getMapData2() {
        return mapData2;
    }

}
