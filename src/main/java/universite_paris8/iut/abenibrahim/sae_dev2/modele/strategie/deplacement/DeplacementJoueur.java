package universite_paris8.iut.abenibrahim.sae_dev2.modele.strategie.deplacement;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Acteur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;

public class DeplacementJoueur implements Deplacement {
    @Override
    public void seDeplacer(Acteur acteur, Direction direction) {

        Joueur joueur = (Joueur) acteur;
        int xTmp = joueur.getX() + direction.getX() * joueur.getVitesse();
        int yTmp = joueur.getY() + direction.getY() * joueur.getVitesse();

        if (joueur.getEnvironnement().dansTerrain(xTmp, yTmp) && joueur.getEnvironnement().getMap().verifierCollisions(xTmp, yTmp)) {
            joueur.setX(xTmp);
            joueur.setY(yTmp);
            joueur.setLastDirection(direction);
        }
    }
}
