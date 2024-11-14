package universite_paris8.iut.abenibrahim.sae_dev2.modele.objet;

import javafx.scene.image.Image;
import universite_paris8.iut.abenibrahim.sae_dev2.vue.ImageObjet;

public class Soin {
    private final int x;
    private final int y;
    private final int pointPvDonner;

    private Soin(Builder builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.pointPvDonner = builder.pointPvDonner;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPointPvDonner() {
        return pointPvDonner;
    }

    public static class Builder {
        private int x = Constante.POSITION_X_SOIN;
        private int y = Constante.POSITION_Y_SOIN;
        private int pointPvDonner = Constante.PV_DONNER_AVEC_SOIN;

        public Builder withX(int x) {
            this.x = x;
            return this;
        }

        public Builder withY(int y) {
            this.y = y;
            return this;
        }

        public Builder withPointPvDonner(int pointPvDonner) {
            this.pointPvDonner = pointPvDonner;
            return this;
        }

        public Soin build() {
            return new Soin(this);
        }
    }
}
