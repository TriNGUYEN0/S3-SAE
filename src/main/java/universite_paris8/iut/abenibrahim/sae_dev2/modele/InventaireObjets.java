package universite_paris8.iut.abenibrahim.sae_dev2.modele;

import javafx.scene.image.Image;
import universite_paris8.iut.abenibrahim.sae_dev2.objet.Arme;
public class InventaireObjets {
    private Arme arme;
    private Image image;
    private int id = 0;
    public InventaireObjets(Image image,Arme arme) {
        this.arme = arme;
        this.image = image;
    }
    public Image getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Arme getArme() {
        return arme;
    }
}