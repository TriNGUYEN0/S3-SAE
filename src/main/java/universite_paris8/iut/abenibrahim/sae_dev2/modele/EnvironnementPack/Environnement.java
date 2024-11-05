package universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.Constants;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Acteur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Pnj;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Arme;

import java.util.List;

public class Environnement {
    private TerrainManager terrainManager;
    private ObjetManager objetManager;
    private ActeurManager acteurManager;

    public Environnement() {
        this.terrainManager = new TerrainManager(Constants.longueurMax, Constants.largeurMax);
        this.objetManager = new ObjetManager();
        this.acteurManager = new ActeurManager(this);
    }

    public TerrainManager getTerrainManager() {
        return terrainManager;
    }

    public ObjetManager getObjetManager() {
        return objetManager;
    }

    public List<Arme> getArmeMap() {
        return objetManager.getArmeMap();
    }

    public ActeurManager getActeurManager() {
        return acteurManager;
    }

    public List<Pnj> getPnjList() {
        return acteurManager.getPnjs();
    }

    public void unTour() {
        acteurManager.unTour();
    }

    public Joueur getJoueur() {
        return acteurManager.getJoueur();
    }
}
