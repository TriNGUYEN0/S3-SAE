package universite_paris8.iut.abenibrahim.sae_dev2.modele.objet;

public class ObjetDefense {
    private final int x;
    private final int y;
    private final int defDonner;

    // Constructor private sử dụng Builder
    private ObjetDefense(Builder builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.defDonner = builder.defDonner;
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDefDonner() {
        return defDonner;
    }

    // Lớp lồng Builder
    public static class Builder {
        private int x = Constante.POSITION_X_OBJETDEF;
        private int y = Constante.POSITION_Y_OBJETDEF;
        private int defDonner = Constante.POINT_DONNER_AVEC_OBJETDEF;

        public Builder withX(int x) {
            this.x = x;
            return this;
        }

        public Builder withY(int y) {
            this.y = y;
            return this;
        }

        public Builder withDefDonner(int defDonner) {
            this.defDonner = defDonner;
            return this;
        }

        public ObjetDefense build() {
            return new ObjetDefense(this);
        }
    }
}
