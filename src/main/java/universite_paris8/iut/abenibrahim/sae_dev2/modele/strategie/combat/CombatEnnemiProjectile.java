package universite_paris8.iut.abenibrahim.sae_dev2.modele.strategie.combat;


import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Projectile;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Acteur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.EnnemiProjectile;

public class CombatEnnemiProjectile implements Combat {
    private EnnemiProjectile ennemiProjectile;


    public CombatEnnemiProjectile(EnnemiProjectile ennemiProjectile) {
        this.ennemiProjectile = ennemiProjectile;
    }

    @Override
    public void attaquer(Acteur cible) {

        if (detecterCible(cible)) {

            Direction directionVersCible = calculerDirection(
                    ennemiProjectile.getX(),
                    ennemiProjectile.getY(),
                    cible.getX(),
                    cible.getY()
            );


            Projectile projectile = new Projectile(
                    ennemiProjectile.getX(),
                    ennemiProjectile.getY(),
                    directionVersCible,
                    10, // Vitesse du projectile
                    ennemiProjectile.getArmeDistance().getPointAttaque()
            );


            ennemiProjectile.getProjectiles().add(projectile);

            System.out.println("EnnemiProjectile a tiré un projectile vers " + cible.getClass().getSimpleName());
        }
    }

    @Override
    public void recevoirDegats(int degats) {

        int pvRestants = ennemiProjectile.getPv() - degats;
        ennemiProjectile.setPv(pvRestants);
        System.out.println("EnnemiProjectile a reçu " + degats + " points de dégâts.");
    }


    private boolean detecterCible(Acteur cible) {
        int distanceDetection = 500; // Distance de détection
        double distance = calculerDistance(
                ennemiProjectile.getX(),
                ennemiProjectile.getY(),
                cible.getX(),
                cible.getY()
        );
        return distance <= distanceDetection;
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


    private double calculerDistance(int x1, int y1, int x2, int y2) {
        int deltaX = x1 - x2;
        int deltaY = y1 - y2;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}

