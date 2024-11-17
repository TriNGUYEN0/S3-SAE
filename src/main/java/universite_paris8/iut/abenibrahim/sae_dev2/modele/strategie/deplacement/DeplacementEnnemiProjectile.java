package universite_paris8.iut.abenibrahim.sae_dev2.modele.strategie.deplacement;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Acteur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.EnnemiProjectile;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;

public class DeplacementEnnemiProjectile implements Deplacement {
    @Override
    public void seDeplacer(Acteur acteur, Direction direction) {
        if (!(acteur instanceof EnnemiProjectile)) {
            return;
        }

        EnnemiProjectile ennemiProjectile = (EnnemiProjectile) acteur;
        Environnement environnement = ennemiProjectile.getEnvironnement();
        Joueur joueur = environnement.getActeurManager().getJoueur();

        if (ennemiProjectile.detecterJoueur()) {
            Direction directionVersJoueur = calculerDirection(ennemiProjectile.getX(), ennemiProjectile.getY(), joueur.getX(), joueur.getY());
            int xTmp = ennemiProjectile.getX() + directionVersJoueur.getX() * ennemiProjectile.getVitesse();
            int yTmp = ennemiProjectile.getY() + directionVersJoueur.getY() * ennemiProjectile.getVitesse();

            // Sử dụng `dansTerrain` từ `Map`
            if (environnement.getTerrainManager().getMap().dansTerrain(xTmp, yTmp)
                    && environnement.getTerrainManager().getMap().verifierCollisions(xTmp, yTmp)) {
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
