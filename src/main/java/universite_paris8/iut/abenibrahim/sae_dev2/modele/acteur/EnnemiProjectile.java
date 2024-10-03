package universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Projectile;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.ArmeDistance;

public class EnnemiProjectile extends Acteur{
    private ObservableList<Projectile> projectiles;
    private ArmeDistance armeDistance;
    private Direction direction;
    private Direction lastDirection;
    private static int DISTANCE_DETECTION = 100;
    public EnnemiProjectile(Environnement e, int x, int y, int v, int pv) {
        super(e, x, y, v, pv);
        this.projectiles = FXCollections.observableArrayList();
        this.armeDistance = new ArmeDistance();
        this.lastDirection = Direction.EST;
    }

    public boolean detecterJoueur(){
        Joueur joueur = environnement.getGuts();
        int distanceX = Math.abs(joueur.getX() - this.getX());
        int distanceY = Math.abs(joueur.getY() - this.getY());
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        return distance <= DISTANCE_DETECTION;
    }

    public void setLastDirection(Direction direction){
        this.lastDirection = direction;
    }

    public void setDistanceDetection(int distanceDetection){
        DISTANCE_DETECTION = distanceDetection;
    }


    @Override
    public void attaquer() {
        if(detecterJoueur()){
            Joueur joueur = environnement.getGuts();
            int projectileX = getX();
            int projectileY = getY();
            int vitesseProjectile = 10; // Ajustez selon vos besoins
            int degatProjectile = armeDistance.getPointAttaque();
            Direction directionVersJoueur = calculerDirection(joueur.getX(), joueur.getY());
            Projectile projectile = new Projectile(projectileX, projectileY, directionVersJoueur, vitesseProjectile, degatProjectile);
            projectiles.add(projectile);
            System.out.println("Projectile lancé à : " + projectileX + ", " + projectileY + " en direction " + lastDirection);
        }

    }
    @Override
    public void recoisDegat(int degat) {
        int newPv = getPv() - degat;
        setPv(newPv);
    }

    public ObservableList<Projectile> getProjectileList() {
        return projectiles;
    }
    private Direction calculerDirection(int joueurX, int joueurY) {
        int deltaX = joueurX - getX();
        int deltaY = joueurY - getY();

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            return deltaX > 0 ? Direction.EST : Direction.OUEST;
        } else {
            return deltaY > 0 ? Direction.SUD : Direction.NORD;
        }
    }
}



