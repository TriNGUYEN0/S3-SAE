package universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.strategie.combat.Combat;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.strategie.deplacement.Deplacement;

public abstract class Acteur {
    private IntegerProperty x, y;
    private IntegerProperty pv;
    private int vitesse;
    private String id;
    protected Environnement environnement;


    protected Combat combatStrategy;


    protected Deplacement deplacementStrategy;

    public Acteur(Environnement e, int x, int y, int v, int pv) {
        this.environnement = e;
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.vitesse = v;
        this.pv = new SimpleIntegerProperty(pv);
        this.id = String.valueOf(1);
    }


    public int getX() {
        return x.get();
    }

    public void setX(int x) {
        this.x.set(x);
    }

    public IntegerProperty xProperty() {
        return x;
    }

    public int getY() {
        return y.get();
    }

    public void setY(int y) {
        this.y.set(y);
    }

    public IntegerProperty yProperty() {
        return y;
    }

    public int getPv() {
        return pv.get();
    }

    public void setPv(int pv) {
        this.pv.set(pv);
    }

    public IntegerProperty pvProperty() {
        return pv;
    }

    public int getVitesse() {
        return vitesse;
    }

    public Environnement getEnvironnement() {
        return environnement;
    }

    public String getId() {
        return id;
    }

    public boolean estVivant() {
        return getPv() > 0;
    }


    public void setCombatStrategy(Combat combatStrategy) {
        this.combatStrategy = combatStrategy;
    }

    public void attaquer(Acteur cible) {
        if (combatStrategy != null) {
            combatStrategy.attaquer(cible);
        }
    }

    public void recevoirDegats(int degats) {
        if (combatStrategy != null) {
            combatStrategy.recevoirDegats(degats);
        }
    }


    public void setDeplacementStrategy(Deplacement deplacementStrategy) {
        this.deplacementStrategy = deplacementStrategy;
    }

    public void seDeplacer(Direction direction) {
        if (deplacementStrategy != null) {
            deplacementStrategy.seDeplacer(this, direction);
        }
    }
}
