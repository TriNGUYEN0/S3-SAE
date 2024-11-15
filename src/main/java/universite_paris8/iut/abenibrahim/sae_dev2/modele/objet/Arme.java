package universite_paris8.iut.abenibrahim.sae_dev2.modele.objet;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Arme {
    protected int attaque;
    private int x;
    private int y;
    private final StringProperty nom;

    public Arme(int attaque, String nom, int x, int y) {
        this.attaque = attaque;
        this.nom = new SimpleStringProperty(nom);
        this.x = x;
        this.y = y;
    }

    public int getPointAttaque() {
        return attaque;
    }

    public String getNom() {
        return this.nom.getValue();
    }

    public StringProperty nomProperty() {
        return this.nom;
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
}
