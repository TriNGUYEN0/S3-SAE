package universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.serviceAction;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Acteur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Environnement;

public class CombatJoueur implements Combat {

    private Joueur joueur;

    public CombatJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    @Override
    public void attaquer(Acteur cible) {
        if (joueur.getArmeEquipee() == null) {
            System.out.println("Le joueur n'a pas d'arme équipée pour attaquer.");
            return;
        }

        int distanceAttaque = 50;
        int joueurX = joueur.getX();
        int joueurY = joueur.getY();
        int cibleX = cible.getX();
        int cibleY = cible.getY();

        double distance = Math.sqrt(Math.pow(joueurX - cibleX, 2) + Math.pow(joueurY - cibleY, 2));

        if (distance <= distanceAttaque && cible instanceof Combat) {
            Combat cibleCombat = (Combat) cible;
            cibleCombat.recoisDegat(joueur.getArmeEquipee().getPointAttaque());
            System.out.println("Le joueur a attaqué l'ennemi.");
        } else {
            System.out.println("La cible est hors de portée ou n'est pas attaquable.");
        }
    }

    @Override
    public void recoisDegat(int degat) {
        int pointDef = joueur.getPointDef();
        int degatEffectif = Math.max(0, degat - pointDef);
        joueur.setPv(joueur.getPv() - degatEffectif);
    }
}
