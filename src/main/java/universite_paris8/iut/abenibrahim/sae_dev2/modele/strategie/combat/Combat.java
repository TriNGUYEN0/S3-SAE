package universite_paris8.iut.abenibrahim.sae_dev2.modele.strategie.combat;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Acteur;

public interface Combat {
    void attaquer(Acteur cible);
    void recevoirDegats(int degats);
}
