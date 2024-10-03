package universite_paris8.iut.abenibrahim.sae_dev2.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.abenibrahim.sae_dev2.controleur.Controleur;

public class AnimatedSprite  {
    public Image[] images;
    public int frameActuel;
    public int nombreFrames;
    public int delaiFrame;
    public int compteurDelaiFrame;
    protected String[][][][] cheminsImage;
    protected ImageView imageView;
    protected double largeur;
    protected double hauteur;

    public AnimatedSprite(double x, double y, String[] cheminsImages, int delaiFrame) {
        chargerImage(cheminsImages);
        this.delaiFrame = delaiFrame;
        this.compteurDelaiFrame = 0;
        this.frameActuel = 0;
        this.nombreFrames = images.length;
        this.imageView = new ImageView(images[0]);
        this.imageView.setX(x);
        this.imageView.setY(y);
    }

    public void chargerImage(String[] cheminsImages){
        images = new Image[cheminsImages.length];
        imageView = new ImageView();

        for(int i=0;i<cheminsImages.length;i++){
            images[i] = new Image(cheminsImages[i]);
        }
        imageView.setImage(images[0]);
        largeur = images[0].getWidth();
        hauteur = images[0].getHeight();
    }

    public ImageView getImageView(){
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setFrameActuel(int frameActuel) {
        this.frameActuel = frameActuel;
    }

    public void mettreAJour() {
        if (compteurDelaiFrame >= delaiFrame) {
            setFrameActuel((frameActuel + 1) % nombreFrames);
            getImageView().setImage(images[frameActuel]);
            Controleur.setGSprite(images[frameActuel]);
            compteurDelaiFrame = 0;
        } else {
            compteurDelaiFrame++;
        }
    }
    public void definirFrames(String[] frames) {
        chargerImage(frames);
        nombreFrames = frames.length;
        mettreAJour();
    }
}