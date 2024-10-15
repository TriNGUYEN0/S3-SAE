package universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.serviceAction;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Acteur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Ennemi;

public class CombatEnnemi implements Combat {

    private Ennemi ennemi;

    public CombatEnnemi(Ennemi ennemi) {
        this.ennemi = ennemi;
    }

    @Override
    public void attaquer(Acteur cible) {
        if (cible instanceof Combat) {
            int distanceAttaque = 50;
            int ennemiX = ennemi.getX();
            int ennemiY = ennemi.getY();
            int cibleX = cible.getX();
            int cibleY = cible.getY();

            double distance = Math.sqrt(Math.pow(ennemiX - cibleX, 2) + Math.pow(ennemiY - cibleY, 2));

            if (distance <= distanceAttaque) {
                Combat cibleCombat = (Combat) cible;
                cibleCombat.recoisDegat(ennemi.getEpée().getPointAttaque());
                System.out.println("Ennemi a attaqué la cible.");
            } else {
                System.out.println("La cible est hors de portée.");
            }
        } else {
            System.out.println("La cible ne peut pas être attaquée.");
        }
    }

    @Override
    public void recoisDegat(int degat) {
        int newPv = ennemi.getPv() - degat;
        ennemi.setPv(Math.max(0, newPv));
        System.out.println("Ennemi a reçu " + degat + " de dégâts. PV restant: " + ennemi.getPv());
    }
}
