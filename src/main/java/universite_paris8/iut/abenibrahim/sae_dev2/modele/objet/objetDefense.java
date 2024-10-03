package universite_paris8.iut.abenibrahim.sae_dev2.modele.objet;

import javafx.scene.image.Image;

public class objetDefense {
    private int x;
    private int y;
    private Image image;
    private int defDonner;

    public objetDefense() {
        this.x = 375;
        this.y =375 ;
        this.image = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/pointDEf.png").toString());
        this.defDonner = 100;
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDefDonner() {
        return defDonner;
    }
}
