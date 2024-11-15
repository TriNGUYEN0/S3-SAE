package universite_paris8.iut.abenibrahim.sae_dev2.modele;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Arme;
public class InventaireObjets {
    private Arme arme;
    private ImageView image;
    private int id = 0;
    public InventaireObjets(ImageView image,Arme arme) {
        this.arme = arme;
        this.image = image;
    }

    public ImageView getImage() {
        return image;
    }

    public int getId() {
        return id;
    }



    public Arme getArme() {
        return arme;
    }
}