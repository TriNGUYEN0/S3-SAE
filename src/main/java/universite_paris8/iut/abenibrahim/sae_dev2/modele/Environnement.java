package universite_paris8.iut.abenibrahim.sae_dev2.modele;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.*;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.*;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.strategie.combat.*;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.strategie.deplacement.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.ArrayList;

public class Environnement {
    private Map map;
    private ObservableList<Acteur> acteurs;
    private Joueur joueur;
    private List<Ennemi> ennemis;
    private List<EnnemiProjectile> ennemisProjectiles;
    private List<Pnj> pnjs;
    private List<Arme> armes;
    private List<Soin> soins;
    private List<ObjetDefense> objetsDefense;

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
        this.ennemis = new ArrayList<>();
        this.ennemisProjectiles = new ArrayList<>();
        this.pnjs = new ArrayList<>();
        this.armes = new ArrayList<>();
        this.soins = new ArrayList<>();
        this.objetsDefense = new ArrayList<>();

        // Initialiser le joueur
        this.joueur = new Joueur(this, x, y, vitesse, pvJoueur);
        acteurs.add(joueur);

        // Initialiser les ennemis
        initialiserEnnemis();

        // Initialiser les PNJ
        initialiserPnjs();

        // Ajouter les ennemis et PNJ à la liste des acteurs
        acteurs.addAll(ennemis);
        acteurs.addAll(ennemisProjectiles);
        acteurs.addAll(pnjs);
    }

    private void initialiserEnnemis() {
        // Créer un ennemi de mêlée
        Ennemi ennemi = new Ennemi(this, 500, 300, vitesse, pvEnnemi);
        ennemis.add(ennemi);

        // Créer un ennemi à distance
        EnnemiProjectile ennemiProjectile = new EnnemiProjectile(this, 1000, 400, vitesse, pvEnnemi);
        ennemisProjectiles.add(ennemiProjectile);
    }

    private void initialiserPnjs() {
        Pnj pnj = new Pnj();
        pnjs.add(pnj);
    }

    // Méthodes pour gérer les acteurs
    public ObservableList<Acteur> getActeurs() {
        return acteurs;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public List<Ennemi> getEnnemis() {
        return ennemis;
    }

    public List<EnnemiProjectile> getEnnemisProjectiles() {
        return ennemisProjectiles;
    }

    public List<Pnj> getPnjs() {
        return pnjs;
    }

    // Méthodes pour gérer les objets
    public List<Arme> getArmes() {
        return armes;
    }

    public List<Soin> getSoins() {
        return soins;
    }

    public List<ObjetDefense> getObjetsDefense() {
        return objetsDefense;
    }

    // Méthodes pour la mise à jour de l'environnement
    public void unTour() {
        // Mettre à jour chaque acteur
        for (Acteur acteur : acteurs) {
            if (acteur.estVivant()) {
                // Mise à jour selon le type d'acteur
                if (acteur instanceof Ennemi) {
                    Ennemi ennemi = (Ennemi) acteur;
                    ennemi.seDeplacer(ennemi.getDirection());
                    ennemi.attaquer(joueur);
                } else if (acteur instanceof EnnemiProjectile) {
                    EnnemiProjectile ennemiProjectile = (EnnemiProjectile) acteur;
                    ennemiProjectile.seDeplacer(ennemiProjectile.getDirection());
                    ennemiProjectile.attaquer(joueur);
                } else if (acteur instanceof Joueur) {
                    // Le joueur est contrôlé par le joueur, donc pas de mise à jour automatique
                } else if (acteur instanceof Pnj) {
                    // Les PNJ peuvent avoir des comportements spécifiques
                }
            } else {
                // Si l'acteur est mort, le retirer de la liste
                acteurs.remove(acteur);
            }
        }

        // Mettre à jour les projectiles du joueur
        for (Projectile projectile : joueur.getProjectiles()) {
            projectile.seDeplacer();
            // Vérifier les collisions avec les ennemis
            for (Ennemi ennemi : ennemis) {
                if (projectile.collision(ennemi)) {
                    ennemi.recevoirDegats(projectile.getDegats());
                    joueur.getProjectiles().remove(projectile);
                    break;
                }
            }
            for (EnnemiProjectile ennemiProj : ennemisProjectiles) {
                if (projectile.collision(ennemiProj)) {
                    ennemiProj.recevoirDegats(projectile.getDegats());
                    joueur.getProjectiles().remove(projectile);
                    break;
                }
            }
        }

        // Mettre à jour les projectiles des ennemis
        for (EnnemiProjectile ennemiProj : ennemisProjectiles) {
            for (Projectile projectile : ennemiProj.getProjectiles()) {
                projectile.seDeplacer();
                if (projectile.collision(joueur)) {
                    joueur.recevoirDegats(projectile.getDegats());
                    ennemiProj.getProjectiles().remove(projectile);
                    break;
                }
            }
        }
    }

    // Autres méthodes utilitaires
    public boolean dansTerrain(int x, int y) {
        return x >= 0 && x < longueur && y >= 0 && y < largeur;
    }

    public Map getMap() {
        return map;
    }
}
