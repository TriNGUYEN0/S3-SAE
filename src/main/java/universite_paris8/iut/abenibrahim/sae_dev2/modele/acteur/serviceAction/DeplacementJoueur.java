package universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.serviceAction;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Acteur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;

public class DeplacementJoueur implements Deplacement {

    @Override
    public void seDeplacer(Acteur acteur, Direction direction) {
        if (acteur instanceof Joueur) {
            Joueur joueur = (Joueur) acteur;
            Environnement environnement = joueur.getEnvironnement();
            int xTmp = joueur.getX() + direction.getX() * joueur.getVitesse();
            int yTmp = joueur.getY() + direction.getY() * joueur.getVitesse();


            if (environnement.dansTerrain(xTmp, yTmp) && environnement.getMap().verifierCollisions(xTmp, yTmp)) {
                joueur.setX(xTmp);
                joueur.setY(yTmp);
                joueur.setLastDirection(direction);
            }
        }
    }
}
