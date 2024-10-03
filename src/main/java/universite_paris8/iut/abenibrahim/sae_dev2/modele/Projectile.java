package universite_paris8.iut.abenibrahim.sae_dev2.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Projectile {
    private IntegerProperty x;
    private IntegerProperty y;
    private Direction direction;
    private int vitesse;
    private int degat;

    public Projectile(int x, int y, Direction direction, int vitesse, int degat) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.direction = direction;
        this.vitesse = vitesse;
        this.degat = degat;
    }

    public int getX() {
        return x.get();
    }

    public IntegerProperty xProperty() {
        return x;
    }

    public void setX(int x) {
        this.x.set(x);
    }

    public int getY() {
        return y.get();
    }

    public IntegerProperty yProperty() {
        return y;
    }

    public void setY(int y) {
        this.y.set(y);
    }

    public Direction getDirection() {
        return direction;
    }

    public int getVitesse() {
        return vitesse;
    }

    public int getDegat() {
        return degat;
    }

    public void deplacer() {
        switch (direction) {
            case NORD -> setY(getY() - vitesse);
            case SUD -> setY(getY() + vitesse);
            case OUEST -> setX(getX() - vitesse);
            case EST -> setX(getX() + vitesse);
        }
    }

    public void setDirection(Direction direction) {
        this.direction=direction;
    }
}
