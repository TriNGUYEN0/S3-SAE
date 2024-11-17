package universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.Constants;

public class Environnement {
    private static volatile Environnement instance;

    private  TerrainManager terrainManager;
    private  ObjetManager objetManager;
    private  ActeurManager acteurManager;

    private Environnement() {
        this.terrainManager = new TerrainManager(Constants.longueurMax, Constants.largeurMax);
        this.objetManager = new ObjetManager();
        this.acteurManager = new ActeurManager(this);
    }

    public static Environnement getInstance() {
        if (instance == null) {
            synchronized (Environnement.class) {
                if (instance == null) {
                    instance = new Environnement();
                }
            }
        }
        return instance;
    }

    public TerrainManager getTerrainManager() {
        return terrainManager;
    }

    public ObjetManager getObjetManager() {
        return objetManager;
    }

    public ActeurManager getActeurManager() {
        return acteurManager;
    }


}
