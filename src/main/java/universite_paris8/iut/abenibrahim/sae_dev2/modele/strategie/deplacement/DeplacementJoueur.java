package universite_paris8.iut.abenibrahim.sae_dev2.modele.strategie.deplacement;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Acteur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;

public class DeplacementJoueur implements Deplacement {
    @Override
    public void seDeplacer(Acteur acteur, Direction direction) {
        if (!(acteur instanceof Joueur)) {
            return;
        }

        Joueur joueur = (Joueur) acteur;
        Environnement environnement = joueur.getEnvironnement();
        int xTmp = joueur.getX() + direction.getX() * joueur.getVitesse();
        int yTmp = joueur.getY() + direction.getY() * joueur.getVitesse();


        if (environnement.getTerrainManager().getMap().dansTerrain(xTmp, yTmp)
                && environnement.getTerrainManager().getMap().verifierCollisions(xTmp, yTmp)) {
            joueur.setX(xTmp);
            joueur.setY(yTmp);
            joueur.setDirection(direction);
        }
    }
}
