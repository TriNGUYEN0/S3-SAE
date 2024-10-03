package universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.InventaireObjets;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Projectile;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.ArmeDistance;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.objetDefense;
import universite_paris8.iut.abenibrahim.sae_dev2.objet.Arme;
import universite_paris8.iut.abenibrahim.sae_dev2.objet.Soin;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.objetDefense;

public class Joueur extends Acteur {

    private ObservableList<InventaireObjets> listeArme;
    private Arme armeEquipee;
    private IntegerProperty nbSoin;
    private ObservableList<Projectile> projectiles;
    private Direction lastDirection;
    private int pointDef;

    public Joueur(Environnement e, int x, int y, int v, int pv){
        super(e,x,y,v,pv);
        this.listeArme= FXCollections.observableArrayList();
        this.projectiles = FXCollections.observableArrayList();
        this.armeEquipee = null;
        this.nbSoin= new SimpleIntegerProperty(20);
        this.lastDirection = Direction.EST;
        this.pointDef = 0;
    }

    @Override
    public void attaquer(){
        int playerX, playerY, enemyX, enemyY,enemyProjectilsX,enemyProjectilsY;

        int distanceAttaque = 50;

        playerX = environnement.getGuts().getX();
        playerY = environnement.getGuts().getY();
        enemyX = environnement.getEnnemi().getX();
        enemyY = environnement.getEnnemi().getY();
        enemyProjectilsX=environnement.getEnnemiProjectile().getX();
        enemyProjectilsY=environnement.getEnnemiProjectile().getY();

        int distanceX = Math.abs( enemyX - playerX);
        int distance1x = Math.abs( enemyProjectilsX - playerX);
        int distanceY = Math.abs( enemyY - playerY);
        int distance1Y =  Math.abs( enemyProjectilsY - playerY);
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        double distance1 = Math.sqrt(distance1x * distance1x + distance1Y * distance1Y);
        if (distance <= distanceAttaque) {
            environnement.getEnnemi(). recoisDegat(this.armeEquipee.getPointAttaque());
        }
        if(distance1 <= distanceAttaque){
            environnement.getEnnemiProjectile().recoisDegat(this.armeEquipee.getPointAttaque());
        }
    }

    public void equiperArme(Arme arme){
        this.armeEquipee = arme;
    }
    public void ajouterArme(InventaireObjets arme) {
        this.listeArme.add(arme);
    }

    public Arme ramasserarme(){
        int postionJoueurX, positionJoueurY, positionArmeX, positionArmeY;

        int distanceRamassage = 40;
        postionJoueurX = getX();
        positionJoueurY = getY();
        for (Arme arme : environnement.getArmeMap()) {
            positionArmeX = arme.getX();
            positionArmeY = arme.getY();

            int distanceX = Math.abs(postionJoueurX - positionArmeX);
            int distanceY = Math.abs(positionJoueurY - positionArmeY);
            double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

            if (distance <= distanceRamassage) {
                ajouterArme(new InventaireObjets(arme.getImage(), arme));
                environnement.getArmeMap().remove(arme);
                return arme;
            }
        }
        return null;
    }

    public Soin ramasserSoin(){
        int postionJoueurX, positionJoueurY, positionSoinX, positionSoinY;
        int distanceRamassage = 40;
        postionJoueurX = getX();
        positionJoueurY = getY();
        for (Soin soin : environnement.getSoinMap()) {
            positionSoinX = soin.getX();
            positionSoinY = soin.getY();

            int distanceX = Math.abs(postionJoueurX - positionSoinX);
            int distanceY = Math.abs(positionJoueurY - positionSoinY);
            double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
            if (distance <= distanceRamassage) {
                this.nbSoin.setValue(this.nbSoin.getValue() + 1);
                environnement.getSoinMap().remove(soin);
                return soin;
            }
        }
        return null;
    }
    public objetDefense ramasserObjetDefense(){
        int postionJoueurX, positionJoueurY, positionObjetDefenseX;
        int distanceRamassage = 40;
        postionJoueurX = getX();
        positionJoueurY = getY();
        for (objetDefense objetDefense : environnement.getObjetDefenseList()){
            positionObjetDefenseX = objetDefense.getX();
            positionJoueurY = objetDefense.getY();
            int distanceX = Math.abs(postionJoueurX - positionObjetDefenseX);
            int distanceY = Math.abs(positionJoueurY - objetDefense.getY());
            double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
            if (distance <= distanceRamassage) {
                this.setPointDef(objetDefense.getDefDonner());
                environnement.getObjetDefenseList().remove(objetDefense);
                return objetDefense;
            }
        }
        return null;
    }

    public ObservableList<InventaireObjets> getListeArme() {
        return listeArme;
    }
    public void recoisDegat(int degat) {
        int pointDefJoueur = getPointDef();
        int degatRestant = Math.max(0, degat - pointDefJoueur);
        int newPv = getPv() - degatRestant;
        setPv(newPv);
    }
    public void seSoigner(){
        this.setPv(this.getPv() + 25);
        this.nbSoin.set(this.nbSoin.getValue()-1);
    }
    public IntegerProperty nbSoinProperty() {
        return nbSoin;
    }
    public boolean peutSeSoigner(){
        return this.nbSoin.get() > 0;
    }

    public boolean peutParler(){
        int joueurX, joueurY, pnjX, pnjY;
        int distanceMinParler = 50;
        joueurX = this.getX();
        joueurY = this.getY();

        for(Pnj pnj : environnement.getPnjList()){
            pnjX = pnj.getX();
            pnjY = pnj.getY();
            int distanceX = Math.abs(joueurX - pnjX);
            int distanceY = Math.abs(joueurY - pnjY);
            double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
            return distance <= distanceMinParler;
        }
        return false;
    }

    public void lancerProjectile() {
        if (armeEquipee != null) {
            if(armeEquipee instanceof ArmeDistance){
                int projectileX = getX();
                int projectileY = getY();
                int vitesseProjectile = 10; // Ajustez selon vos besoins
                int degatProjectile = armeEquipee.getPointAttaque(); // Dégâts égaux à ceux de l'arme équipée

                Projectile projectile = new Projectile(projectileX, projectileY, lastDirection, vitesseProjectile, degatProjectile);
                projectiles.add(projectile);
                System.out.println("Projectile lancé à : " + projectileX + ", " + projectileY + " en direction " + lastDirection);
            }

        }
    }

    public void setPointDef(int pointDef) {
        this.pointDef = pointDef;
    }

    public ObservableList<Projectile> getProjectiles() {
        return projectiles;
    }

    @Override
    public void seDeplace(Direction direction) {
        super.seDeplace(direction);
        this.lastDirection = direction;
    }

    public Direction getLastDirection() {
        return lastDirection;
    }

    public int getPointDef() {
        return this.pointDef;
    }
}
