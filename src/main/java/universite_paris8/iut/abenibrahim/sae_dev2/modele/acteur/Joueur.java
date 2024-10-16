package universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.InventaireObjets;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Projectile;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.serviceAction.CombatJoueur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.serviceAction.DeplacementJoueur;
import universite_paris8.iut.abenibrahim.sae_dev2.objet.Arme;
import universite_paris8.iut.abenibrahim.sae_dev2.objet.Soin;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.ArmeDistance;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.objetDefense;

public class Joueur extends Acteur {

    private ObservableList<InventaireObjets> listeArme;
    private Arme armeEquipee;
    private IntegerProperty nbSoin;
    private ObservableList<Projectile> projectiles;
    private Direction lastDirection;
    private int pointDef;

    private DeplacementJoueur deplacementJoueur;  // Dịch vụ di chuyển
    private CombatJoueur combatJoueur;  // Dịch vụ chiến đấu

    public Joueur(Environnement e, int x, int y, int v, int pv) {
        super(e, x, y, v, pv);
        this.listeArme = FXCollections.observableArrayList();
        this.projectiles = FXCollections.observableArrayList();
        this.nbSoin = new SimpleIntegerProperty(20);
        this.lastDirection = Direction.EST;
        this.pointDef = 0;

        this.deplacementJoueur = new DeplacementJoueur(); // Sử dụng dịch vụ di chuyển
        this.combatJoueur = new CombatJoueur(this); // Sử dụng dịch vụ chiến đấu
    }

    // Di chuyển bằng dịch vụ DeplacementJoueur
    public void seDeplace(Direction direction) {
        deplacementJoueur.seDeplacer(this, direction);
        this.lastDirection = direction;
    }

    // Chiến đấu sử dụng dịch vụ CombatJoueur
    public void attaquer(Acteur cible) {
        combatJoueur.attaquer(cible);
    }

    public void recoisDegat(int degat) {
        combatJoueur.recoisDegat(degat);
    }

    public void equiperArme(Arme arme) {
        this.armeEquipee = arme;
    }

    public void ajouterArme(InventaireObjets arme) {
        this.listeArme.add(arme);
    }

    public Arme getArmeEquipee() {
        return armeEquipee;
    }

    public Arme ramasserArme() {
        int distanceRamassage = 40;
        int posX = getX();
        int posY = getY();
        for (Arme arme : environnement.getArmeMap()) {
            double distance = Math.sqrt(Math.pow(posX - arme.getX(), 2) + Math.pow(posY - arme.getY(), 2));
            if (distance <= distanceRamassage) {
                ajouterArme(new InventaireObjets(arme.getImage(), arme));
                environnement.getArmeMap().remove(arme);
                return arme;
            }
        }
        return null;
    }

    public Soin ramasserSoin() {
        int distanceRamassage = 40;
        int posX = getX();
        int posY = getY();
        for (Soin soin : environnement.getSoinMap()) {
            double distance = Math.sqrt(Math.pow(posX - soin.getX(), 2) + Math.pow(posY - soin.getY(), 2));
            if (distance <= distanceRamassage) {
                this.nbSoin.setValue(this.nbSoin.getValue() + 1);
                environnement.getSoinMap().remove(soin);
                return soin;
            }
        }
        return null;
    }

    public objetDefense ramasserObjetDefense() {
        int distanceRamassage = 40;
        int posX = getX();
        int posY = getY();
        for (objetDefense objDef : environnement.getObjetDefenseList()) {
            double distance = Math.sqrt(Math.pow(posX - objDef.getX(), 2) + Math.pow(posY - objDef.getY(), 2));
            if (distance <= distanceRamassage) {
                this.pointDef = objDef.getDefDonner();
                environnement.getObjetDefenseList().remove(objDef);
                return objDef;
            }
        }
        return null;
    }

    public void seSoigner() {
        if (peutSeSoigner()) {
            setPv(getPv() + 25);
            this.nbSoin.setValue(this.nbSoin.getValue() - 1);
        }
    }

    public boolean peutSeSoigner() {
        return this.nbSoin.get() > 0;
    }

    public boolean peutParler() {
        int distanceMinParler = 50;
        for (Pnj pnj : environnement.getPnjList()) {
            double distance = Math.sqrt(Math.pow(getX() - pnj.getX(), 2) + Math.pow(getY() - pnj.getY(), 2));
            if (distance <= distanceMinParler) {
                return true;
            }
        }
        return false;
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

    public ObservableList<InventaireObjets> getListeArme() {
        return listeArme;
    }

    public ObservableList<Projectile> getProjectiles() {
        return projectiles;
    }

    public int getPointDef() {
        return pointDef;
    }

    public IntegerProperty nbSoinProperty() {
        return nbSoin;
    }

    public Direction getLastDirection() {
        return lastDirection;
    }

    public void setPointDef(int pointDef) {
        this.pointDef = pointDef;
    }

    public void setLastDirection(Direction direction) {
        this.lastDirection = direction;
    }


}
