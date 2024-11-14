package universite_paris8.iut.abenibrahim.sae_dev2.modele.strategie.combat;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Acteur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Ennemi;

public class CombatEnnemi implements Combat {
    private Ennemi ennemi;

    public CombatEnnemi(Ennemi ennemi) {
        this.ennemi = ennemi;
    }

    @Override
    public void attaquer(Acteur cible) {
        int distanceAttaque = 50;
        double distance = calculerDistance(ennemi.getX(), ennemi.getY(), cible.getX(), cible.getY());
        if (distance <= distanceAttaque) {
            cible.recevoirDegats(ennemi.getEpee().getPointAttaque());
        }
    }

    @Override
    public void recevoirDegats(int degats) {
        ennemi.setPv(ennemi.getPv() - degats);
    }

    private double calculerDistance(int x1, int y1, int x2, int y2) {
        int deltaX = x1 - x2;
        int deltaY = y1 - y2;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}
