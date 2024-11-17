package universite_paris8.iut.abenibrahim.sae_dev2.modele.objet;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Acteur;

public interface StrategieAttaque {
    public Acteur attaquer(Acteur attaquant, Acteur cible);
}
