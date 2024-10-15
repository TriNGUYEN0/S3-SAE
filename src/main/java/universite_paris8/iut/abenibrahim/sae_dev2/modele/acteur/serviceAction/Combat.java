package universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.serviceAction;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Acteur;

public interface Combat {
    void attaquer(Acteur cible);
    void recoisDegat(int degat);
}
