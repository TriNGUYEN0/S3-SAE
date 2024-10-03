package universite_paris8.iut.abenibrahim.sae_dev2.objet;

import javafx.scene.image.Image;

public class Soin {
    private int x;
    private int y;
    private int pointPvDonner;
    private Image image;
    public Soin() {
        this.x = 150;
        this.y = 150;
        this.image = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/items.png").toString());
        this.pointPvDonner = 25;
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
    public int getPointPvDonner() {
        return pointPvDonner;
    }
    public void setPointPvDonner(int pointPvDonner) {
        this.pointPvDonner = pointPvDonner;
    }
    public void setImage(Image image) {
        this.image = image;
    }
    public Image getImage() {
        return image;
    }


}
