package universite_paris8.iut.abenibrahim.sae_dev2.modele.strategie.deplacement;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Acteur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.EnnemiProjectile;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;

public class DeplacementEnnemiProjectile implements Deplacement {
    @Override
    public void seDeplacer(Acteur acteur, Direction direction) {
        EnnemiProjectile ennemiProjectile = (EnnemiProjectile) acteur;
        Joueur joueur = ennemiProjectile.getEnvironnement().getJoueur();

        if (ennemiProjectile.detecterJoueur()) {
            Direction directionVersJoueur = calculerDirection(ennemiProjectile.getX(), ennemiProjectile.getY(), joueur.getX(), joueur.getY());
            int xTmp = ennemiProjectile.getX() + directionVersJoueur.getX() * ennemiProjectile.getVitesse();
            int yTmp = ennemiProjectile.getY() + directionVersJoueur.getY() * ennemiProjectile.getVitesse();

            if (ennemiProjectile.getEnvironnement().dansTerrain(xTmp, yTmp) && ennemiProjectile.getEnvironnement().getMap().verifierCollisions(xTmp, yTmp)) {
                ennemiProjectile.setX(xTmp);
                ennemiProjectile.setY(yTmp);
                ennemiProjectile.setDirection(directionVersJoueur);
            }
        }
    }

    private Direction calculerDirection(int x1, int y1, int x2, int y2) {
        int deltaX = x2 - x1;
        int deltaY = y2 - y1;

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            return deltaX > 0 ? Direction.EST : Direction.OUEST;
        } else {
            return deltaY > 0 ? Direction.SUD : Direction.NORD;
        }
    }
}
