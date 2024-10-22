package universite_paris8.iut.abenibrahim.sae_dev2.modele;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.*;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.objetDefense;
import universite_paris8.iut.abenibrahim.sae_dev2.objet.Arme;
import universite_paris8.iut.abenibrahim.sae_dev2.objet.Soin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.ArrayList;

public class Environnement {
    private ArrayList<Arme> armeMap;
    private List<Pnj> pnjList;
    private List<EnnemiProjectile> ennemiProjectileList;
    private ArrayList<Soin> soinMap;
    private List<objetDefense> objetDefenseList;
    private ObservableList<Acteur> acteurs;

    private Joueur guts;
    private Ennemi ennemi;
    private Map map;

    private final int longueur = Constants.longueurMax;
    private final int largeur = Constants.largeurMax;
    private final int pvJoueur = Constants.pvJoueur;
    private final int pvEnnemi = Constants.pvEnnemi;
    private final int x = Constants.positionX;
    private final int y = Constants.positionY;
    private final int vitesse = Constants.vitesse;

    public Environnement() {
        this.map = new Map();
        this.acteurs = FXCollections.observableArrayList();
        this.guts = new Joueur(this, x, y, vitesse, pvJoueur);
        this.ennemi = new Ennemi(this, 20, 30, vitesse, pvEnnemi);
        this.armeMap = new ArrayList<>();
        this.soinMap = new ArrayList<>();
        this.pnjList = new ArrayList<>();
        this.ennemiProjectileList = new ArrayList<>();
        this.objetDefenseList = new ArrayList<>();

        initialiserPnj();
        initialiserEnnemiProjectile();
        acteurs.add(guts);
        acteurs.add(ennemi);
    }

    // Getters
    public ArrayList<Arme> getArmeMap() {
        return armeMap;
    }

    public ArrayList<Soin> getSoinMap() {
        return soinMap;
    }

    public List<objetDefense> getObjetDefenseList() {
        return objetDefenseList;
    }

    public ObservableList<Acteur> getActeurs() {
        return acteurs;
    }

    public List<Pnj> getPnjList() {
        return pnjList;
    }

    public List<EnnemiProjectile> getEnnemiProjectileList() {
        return ennemiProjectileList;
    }

    public Map getMap() {
        return map;
    }

    public Ennemi getEnnemi() {
        return ennemi;
    }

    public Joueur getGuts() {
        return guts;
    }

    // Initialisation des PNJ avec les paramètres requis
    public void initialiserPnj() {
        Pnj pnj = new Pnj(this, 500, 300, 100); // Initialisation avec environnement, x, y, et pv
        pnjList.add(pnj);
        acteurs.add(pnj);
    }

    // Initialisation des ennemis projectiles
    public void initialiserEnnemiProjectile() {
        EnnemiProjectile ennemiProjectile = new EnnemiProjectile(this, 1000, 400, vitesse, pvEnnemi);
        ennemiProjectileList.add(ennemiProjectile);
        acteurs.add(ennemiProjectile);
    }

    // Méthode pour obtenir le premier PNJ
    public Pnj getPnj() {
        if (!pnjList.isEmpty()) {
            return pnjList.get(0); // Retourner le premier PNJ trouvé
        }
        return null;
    }

    // Méthode pour obtenir le premier EnnemiProjectile
    public EnnemiProjectile getEnnemiProjectile() {
        if (!ennemiProjectileList.isEmpty()) {
            return ennemiProjectileList.get(0); // Retourner le premier EnnemiProjectile trouvé
        }
        return null;
    }

    // Ajouter un Acteur
    public void ajouter(Acteur a) {
        acteurs.add(a);
    }

    // Vérification si la position est dans les limites de la carte
    public boolean dansTerrain(int x, int y) {
        return x >= 0 && x < longueur && y >= 0 && y < largeur;
    }

    // Un tour de jeu - déplacement et actions des ennemis
    public void unTour() {
        for (Acteur acteur : new ArrayList<>(acteurs)) {
            if (acteur instanceof Ennemi) {
                Ennemi ennemi = (Ennemi) acteur;
                ennemi.seDeplaceVersJoueur(); // Utilise le service de déplacement
                ennemi.attaquer(guts); // Si joueur est à portée, attaquer
            }
            if (acteur instanceof EnnemiProjectile) {
                EnnemiProjectile ennemiProjectile = (EnnemiProjectile) acteur;
                if (ennemiProjectile.detecterJoueur()) {
                    ennemiProjectile.attaquer(guts); // Tirs de projectile vers le joueur
                }
            }
        }

        // Suppression des acteurs morts
        acteurs.removeIf(acteur -> !acteur.estVivant());
    }
}
