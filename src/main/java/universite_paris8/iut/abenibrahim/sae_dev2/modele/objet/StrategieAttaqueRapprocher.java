package universite_paris8.iut.abenibrahim.sae_dev2.modele.objet;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Acteur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;

public class StrategieAttaqueRapprocher implements StrategieAttaque{

    private int distanceAttaque;
    private Environnement env= Environnement.getUniqueInstance();
    public StrategieAttaqueRapprocher(int distanceAT){

        this.distanceAttaque = distanceAT;
    }
    @Override
    public Acteur attaquer(Acteur attaquant, Acteur cible) {
        Joueur joueur = env.getGuts();
        int distanceX = Math.abs( attaquant.getX() - joueur.getX());
        int distanceY = Math.abs( attaquant.getY() - joueur.getY());
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        if (distance <= distanceAttaque) {
            cible.recoisDegat(attaquant.getArmeEquipee().getPointAttaque());
            return cible;
        }
        return null;
        }





}
