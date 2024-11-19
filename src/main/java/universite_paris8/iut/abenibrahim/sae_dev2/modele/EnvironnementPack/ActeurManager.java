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
    private ObservableList<Acteur> acteurs;
    private List<Ennemi> ennemis;
    private List<Pnj> pnjs;
    private List<EnnemiProjectile> ennemiProjectiles;
    private Joueur joueur;

    public ActeurManager(Environnement environnement) {
        this.acteurs = FXCollections.observableArrayList();
        this.ennemis = new ArrayList<>();
        this.pnjs = new ArrayList<>();
        this.ennemiProjectiles = new ArrayList<>();


        this.joueur = new Joueur(environnement, 200, 200, Constants.vitesse, Constants.pvJoueur);
        acteurs.add(joueur);


        Ennemi ennemi = new Ennemi(environnement, 300, 300, 50, 100);
        ajouterEnnemi(ennemi);


        EnnemiProjectile ennemiProjectile = new EnnemiProjectile(environnement, 1200, 600, 0, 100);
        ajouterEnnemiProjectile(ennemiProjectile);


        Pnj pnj = new Pnj(environnement, 1410, 100, "Je vois que vous voulez tuer le Cleric beast...");
        ajouterPnj(pnj);
    }

    public void ajouterEnnemi(Ennemi ennemi) {
        ennemis.add(ennemi);
        acteurs.add(ennemi);
    }

    public void ajouterPnj(Pnj pnj) {
        pnjs.add(pnj);
        acteurs.add(pnj);
    }

    public void ajouterEnnemiProjectile(EnnemiProjectile ennemiProjectile) {
        ennemiProjectiles.add(ennemiProjectile);
        acteurs.add(ennemiProjectile);
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public ObservableList<Acteur> getActeurs() {
        return acteurs;
    }

    public List<Ennemi> getEnnemis() {
        return ennemis;
    }

    public List<Pnj> getPnjs() {
        return pnjs;
    }

    public List<EnnemiProjectile> getEnnemiProjectiles() {
        return ennemiProjectiles;
    }
}

