package universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.serviceAction.CombatEnnemi;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Epée;
import universite_paris8.iut.abenibrahim.sae_dev2.objet.Arme;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.serviceAction.DeplacementEnnemi;

public class Ennemi extends Acteur {

    private Arme epée;
    private Direction direction;
    private DeplacementEnnemi deplacementEnnemi;
    private CombatEnnemi combatEnnemi;

    public Ennemi(Environnement e, int x, int y, int v, int pv) {
        super(e, x, y, v, pv);
        this.epée = new Epée();
        this.direction = Direction.EST;
        this.deplacementEnnemi = new DeplacementEnnemi();
        this.combatEnnemi = new CombatEnnemi(this);
    }


    public void seDeplaceVersJoueur() {
        deplacementEnnemi.seDeplacer(this, this.direction);
    }


    public void attaquer(Acteur cible) {
        combatEnnemi.attaquer(cible);
    }


    public void recoisDegat(int degat) {
        combatEnnemi.recoisDegat(degat);
    }

    //getter setter
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public Arme getEpée() {
        return this.epée;
    }
}
