package universite_paris8.iut.abenibrahim.sae_dev2.modele.objet;

import javafx.scene.image.Image;
import universite_paris8.iut.abenibrahim.sae_dev2.vue.ImageObjet;

public class Soin {
    private int x;
    private int y;
    private int pointPvDonner;

    public Soin() {
        this.x = Constante.POSITION_X_SOIN;
        this.y = Constante.POSITION_Y_SOIN;
        this.pointPvDonner = Constante.PV_DONNER_AVEC_SOIN;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }



}