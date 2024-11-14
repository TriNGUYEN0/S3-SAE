package universite_paris8.iut.abenibrahim.sae_dev2.modele.strategie.combat;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Acteur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;

public class CombatJoueur implements Combat {
    private Joueur joueur;

    public CombatJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    @Override
    public void attaquer(Acteur cible) {
        if (joueur.getArmeEquipee() != null) {
            int distanceAttaque = 50;
            double distance = calculerDistance(joueur.getX(), joueur.getY(), cible.getX(), cible.getY());
            if (distance <= distanceAttaque) {
                cible.recevoirDegats(joueur.getArmeEquipee().getPointAttaque());
            }
        }
    }

    @Override
    public void recevoirDegats(int degats) {
        int degatsReels = Math.max(0, degats - joueur.getPointDef());
        joueur.setPv(joueur.getPv() - degatsReels);
    }

    private double calculerDistance(int x1, int y1, int x2, int y2) {
        int deltaX = x1 - x2;
        int deltaY = y1 - y2;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}
