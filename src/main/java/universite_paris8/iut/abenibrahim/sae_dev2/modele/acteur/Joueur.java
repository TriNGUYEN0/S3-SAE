package universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.InventaireObjets;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Projectile;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Arme;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.ArmeDistance;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.ObjetDefense;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Soin;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.strategie.combat.CombatJoueur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.strategie.deplacement.DeplacementJoueur;

public class Joueur extends Acteur {
    private ObservableList<Arme> listeArmes;
    private Arme armeEquipee;
    private IntegerProperty nbSoin;
    private ObservableList<Projectile> projectiles;
    private Direction lastDirection;
    private int pointDef;

    public Joueur(Environnement e, int x, int y, int v, int pv) {
        super(e, x, y, v, pv);
        this.listeArmes = FXCollections.observableArrayList();
        this.projectiles = FXCollections.observableArrayList();
        this.nbSoin = new SimpleIntegerProperty(0);
        this.lastDirection = Direction.EST;
        this.pointDef = 0;

        this.setCombatStrategy(new CombatJoueur(this));
        this.setDeplacementStrategy(new DeplacementJoueur());
    }

    public void equiperArme(Arme arme) {
        this.armeEquipee = arme;
    }

    public Arme getArmeEquipee() {
        return armeEquipee;
    }

    public void ajouterArme(Arme arme) {
        this.listeArmes.add(arme);
    }

    public ObservableList<Arme> getListeArmes() {
        return listeArmes;
    }

    public void seSoigner() {
        if (peutSeSoigner()) {
            this.setPv(this.getPv() + 25);
            this.nbSoin.set(this.nbSoin.get() - 1);
        }
    }

    public boolean peutSeSoigner() {
        return this.nbSoin.get() > 0;
    }

    public IntegerProperty nbSoinProperty() {
        return nbSoin;
    }

    public void incrementerNbSoin(int valeur) {
        this.nbSoin.set(this.nbSoin.get() + valeur);
    }

    public void lancerProjectile() {
        if (armeEquipee instanceof ArmeDistance) {
            int projectileX = getX();
            int projectileY = getY();
            int vitesseProjectile = 10;
            int degatProjectile = armeEquipee.getPointAttaque();
            Projectile projectile = new Projectile(projectileX, projectileY, lastDirection, vitesseProjectile, degatProjectile);
            projectiles.add(projectile);
        }
    }

    public ObservableList<Projectile> getProjectiles() {
        return projectiles;
    }

    public Direction getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(Direction direction) {
        this.lastDirection = direction;
    }

    public int getPointDef() {
        return pointDef;
    }

    public void setPointDef(int pointDef) {
        this.pointDef = pointDef;
    }


    public Arme ramasserArme() {
        for (Arme arme : getEnvironnement().getObjetManager().getArmeMap()) {
            if (estProche(arme.getX(), arme.getY())) {
                ajouterArme(arme);
                getEnvironnement().getObjetManager().getArmeMap().remove(arme);
                return arme;
            }
        }
        return null;
    }


    public Soin ramasserSoin() {
        for (Soin soin : getEnvironnement().getObjetManager().getSoinMap()) {
            if (estProche(soin.getX(), soin.getY())) {
                incrementerNbSoin(1);
                getEnvironnement().getObjetManager().getSoinMap().remove(soin);
                return soin;
            }
        }
        return null;
    }

    public ObjetDefense ramasserObjetDefense() {
        for (ObjetDefense objetDefense : getEnvironnement().getObjetManager().getObjetDefenseList()) {
            if (estProche(objetDefense.getX(), objetDefense.getY())) {
                setPointDef(objetDefense.getDefDonner());
                getEnvironnement().getObjetManager().getObjetDefenseList().remove(objetDefense);
                return objetDefense;
            }
        }
        return null;
    }

    public boolean peutParler() {
        for (Pnj pnj : getEnvironnement().getActeurManager().getPnjs()) {
            if (estProche(pnj.getX(), pnj.getY())) {
                return true;
            }
        }
        return false;
    }



    private boolean estProche(int xObjet, int yObjet) {
        int distanceX = Math.abs(xObjet - getX());
        int distanceY = Math.abs(yObjet - getY());
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY) <= 50;
    }
}


