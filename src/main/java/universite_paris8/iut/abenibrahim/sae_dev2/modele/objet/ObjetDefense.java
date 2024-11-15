package universite_paris8.iut.abenibrahim.sae_dev2.modele.objet;

import javafx.scene.image.Image;
import universite_paris8.iut.abenibrahim.sae_dev2.vue.ImageObjet;

public class ObjetDefense {
    private final int x;
    private final int y;
    private final int defDonner;

    // Constructeur privé pour ObjetDefense, prenant un Builder
    private ObjetDefense(Builder builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.defDonner = builder.defDonner;
    }

    // Getters pour accéder aux valeurs de x, y, et defDonner
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDefDonner() {
        return defDonner;
    }

    // Classe Builder imbriquée
    public static class Builder {
        // Valeurs par défaut
        private int x = Constante.POSITION_X_OBJETDEF;
        private int y = Constante.POSITION_Y_OBJETDEF;
        private int defDonner = Constante.POINT_DONNER_AVEC_OBJETDEF;

        // Méthode pour définir x
        public Builder withX(int x) {
            this.x = x;
            return this;
        }

        // Méthode pour définir y
        public Builder withY(int y) {
            this.y = y;
            return this;
        }

        // Méthode pour définir defDonner
        public Builder withDefDonner(int defDonner) {
            this.defDonner = defDonner;
            return this;
        }

        // Méthode de construction qui retourne une nouvelle instance d'ObjetDefense
        public ObjetDefense build() {
            return new ObjetDefense(this);
        }
    }
}