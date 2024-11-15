package universite_paris8.iut.abenibrahim.sae_dev2.modele.strategie.combat;


import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Projectile;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Acteur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.EnnemiProjectile;

public class CombatEnnemiProjectile implements Combat {
    private EnnemiProjectile ennemiProjectile;

    // Constructeur qui reçoit l'ennemi projectile associé
    public CombatEnnemiProjectile(EnnemiProjectile ennemiProjectile) {
        this.ennemiProjectile = ennemiProjectile;
    }

    @Override
    public void attaquer(Acteur cible) {
        // Vérifie si la cible est détectée
        if (detecterCible(cible)) {
            // Calcule la direction vers la cible
            Direction directionVersCible = calculerDirection(
                    ennemiProjectile.getX(),
                    ennemiProjectile.getY(),
                    cible.getX(),
                    cible.getY()
            );

            // Crée un nouveau projectile
            Projectile projectile = new Projectile(
                    ennemiProjectile.getX(),
                    ennemiProjectile.getY(),
                    directionVersCible,
                    10, // Vitesse du projectile
                    ennemiProjectile.getArmeDistance().getPointAttaque()
            );

            // Ajoute le projectile à la liste des projectiles de l'ennemi
            ennemiProjectile.getProjectiles().add(projectile);

            System.out.println("EnnemiProjectile a tiré un projectile vers " + cible.getClass().getSimpleName());
        }
    }

    @Override
    public void recevoirDegats(int degats) {
        // Réduit les PV de l'ennemi projectile
        int pvRestants = ennemiProjectile.getPv() - degats;
        ennemiProjectile.setPv(pvRestants);
        System.out.println("EnnemiProjectile a reçu " + degats + " points de dégâts.");
    }

    // Méthode pour détecter si la cible est à portée
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

    // Calcule la direction vers la cible
    private Direction calculerDirection(int x1, int y1, int x2, int y2) {
        int deltaX = x2 - x1;
        int deltaY = y2 - y1;

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            return deltaX > 0 ? Direction.EST : Direction.OUEST;
        } else {
            return deltaY > 0 ? Direction.SUD : Direction.NORD;
        }
    }

    // Calcule la distance entre deux points
    private double calculerDistance(int x1, int y1, int x2, int y2) {
        int deltaX = x1 - x2;
        int deltaY = y1 - y2;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}

