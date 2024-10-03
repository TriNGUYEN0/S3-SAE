package universite_paris8.iut.abenibrahim.sae_dev2.objet;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Arme {
    protected int attaque;
    private StringProperty nom;
    private int x;
    private int y;
    private Image image;
    private static int id = 0;

    public Arme(int a,String nom){
        this.attaque=a;
        this.image = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/epée-removebg-preview.png").toString());
        this.x=350;
        this.y=350;
        this.nom=new SimpleStringProperty(nom);
    }
    public int getPointAttaque() {
        return attaque;
    }

    public void setAttaque(int attaque) {
        this.attaque = attaque;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public String getNom(){
        return this.nom.getValue();
    }

    public StringProperty nomProperty(){
        return this.nom;
    }

    public int getId() {
        return id;
    }




}