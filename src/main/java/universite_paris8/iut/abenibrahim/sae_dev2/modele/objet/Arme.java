package universite_paris8.iut.abenibrahim.sae_dev2.modele.objet;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Arme {
    protected int attaque;
    private final StringProperty nom;
    public Arme(int a,String nom){
        this.attaque=a;
        this.nom=new SimpleStringProperty(nom);
    }
    public int getPointAttaque() {
        return attaque;
    }

    public String getNom(){
        return this.nom.getValue();
    }

    public StringProperty nomProperty(){
        return this.nom;
    }
}