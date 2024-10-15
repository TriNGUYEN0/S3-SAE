package universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.serviceAction.Combat;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Epée;
import universite_paris8.iut.abenibrahim.sae_dev2.objet.Arme;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.serviceAction.DeplacementEnnemi;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.serviceAction.Combat;

public class Ennemi extends Acteur implements Combat{
    private Arme epée;
    private Direction direction;
    private static int DISTANCE_DETECTION = 100;
    private DeplacementEnnemi deplacementEnnemi;

    public Ennemi(Environnement e, int x, int y, int v, int pv) {
        super(e, x, y, v, pv);
        this.epée = new Epée();
        this.direction = Direction.EST;
        this.deplacementEnnemi = new DeplacementEnnemi();
    }

    public void seDeplaceVersJoueur() {
        deplacementEnnemi.seDeplacer(this, this.direction);
    }

    // Méthodes attaquer et detecterJoueur restent inchangées
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public Arme getEpée() {
        return this.epée;
    }

    @Override
    public void attaquer(Acteur cible) {
        // Logic de l'attaque, chỉ có trong Ennemi
        if (cible instanceof Combat) {
            ((Combat) cible).recoisDegat(this.epée.getPointAttaque());
        }
    }

    @Override
    public void recoisDegat(int degat) {
        int newPv = getPv() - degat;
        setPv(Math.max(0, newPv));
    }
}
