package universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.Constants;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.ArrayList;

public class ActeurManager {
    private Joueur joueur;
    private List<Ennemi> ennemis;
    private List<Pnj> pnjs;
    private ObservableList<Acteur> acteurs;

    public ActeurManager(Environnement environnement) {
        this.acteurs = FXCollections.observableArrayList();
        this.joueur = new Joueur(environnement, 10, 10, Constants.vitesse, Constants.pvJoueur);
        this.ennemis = new ArrayList<>();
        this.pnjs = new ArrayList<>();
        acteurs.add(joueur);
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public List<Ennemi> getEnnemis() {
        return ennemis;
    }

    public List<Pnj> getPnjs() {
        return pnjs;
    }

    public ObservableList<Acteur> getActeurs() {
        return acteurs;
    }

    public void ajouterEnnemi(Ennemi ennemi) {
        ennemis.add(ennemi);
        acteurs.add(ennemi);
    }

    public void ajouterPnj(Pnj pnj) {
        pnjs.add(pnj);
        acteurs.add(pnj);
    }

    public void unTour() {
        for (Acteur acteur : new ArrayList<>(acteurs)) {
            if (acteur instanceof Ennemi) {
                Ennemi ennemi = (Ennemi) acteur;
                ennemi.seDeplaceVersJoueur();
                ennemi.attaquer(joueur);
            } else if (acteur instanceof EnnemiProjectile) {
                EnnemiProjectile ennemiProjectile = (EnnemiProjectile) acteur;
                ennemiProjectile.attaquer(joueur);
            }
        }

        acteurs.removeIf(acteur -> !acteur.estVivant());
    }
}
