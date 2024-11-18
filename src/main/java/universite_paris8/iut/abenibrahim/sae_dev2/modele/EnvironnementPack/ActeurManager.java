package universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Constants;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Acteur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Ennemi;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.EnnemiProjectile;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Pnj;

import java.util.ArrayList;
import java.util.List;

public class ActeurManager {
    private Joueur joueur;
    private List<Ennemi> ennemis;
    private List<Pnj> pnjs;
    private ObservableList<Acteur> acteurs;
    private List<EnnemiProjectile> ennemiProjectiles;

    public ActeurManager(Environnement environnement) {
        this.acteurs = FXCollections.observableArrayList();
        this.joueur = new Joueur(environnement, 200, 200, Constants.vitesse, Constants.pvJoueur);
        this.ennemis = new ArrayList<>();
        this.pnjs = new ArrayList<>();
        this.ennemiProjectiles = new ArrayList<>();

        Ennemi ennemi = new Ennemi(environnement, 300, 300, 50, 100);
        ennemis.add(ennemi);

        EnnemiProjectile ennemiProjectile = new EnnemiProjectile(environnement, 1200, 600, 0, 100);
        ennemiProjectiles.add(ennemiProjectile);


        acteurs.add(joueur);

        Pnj pnj = new Pnj(environnement, 1410, 100, "Je vois que vous voulez tuer le Cleric beast...");
        pnjs.add(pnj);
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

    public void unTour() {
        for (Acteur acteur : new ArrayList<>(acteurs)) {
            if (acteur instanceof Ennemi) {
                Ennemi ennemi = (Ennemi) acteur;
                ennemi.seDeplacer(ennemi.getDirection());
                ennemi.attaquer(joueur);
            } else if (acteur instanceof EnnemiProjectile) {
                EnnemiProjectile ennemiProjectile = (EnnemiProjectile) acteur;
                ennemiProjectile.seDeplacer(ennemiProjectile.getDirection());
                ennemiProjectile.attaquer(joueur);
            }
        }

        acteurs.removeIf(acteur -> !acteur.estVivant());
    }

    public List<EnnemiProjectile> getEnnemiProjectiles() {
        return ennemiProjectiles;
    }
}
