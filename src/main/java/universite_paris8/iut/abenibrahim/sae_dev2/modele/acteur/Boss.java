package universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.*;

public class Boss extends Acteur{
    private ObservableList<Projectile> projectiles;
    private Direction direction;
    private static int DISTANCE_DETECTION = 150;
    private int pointDefense;
    private int pointAttaque;

    public Boss(Environnement e, int x, int y, int v, int pv) {
        super(e, x, y, v, pv);
        this.projectiles = FXCollections.observableArrayList();
        this.direction = Direction.EST;
        this.pointDefense = 100;
        this.pointAttaque = 200;
    }
    public boolean detecterJoueur(){
        Joueur joueur = environnement.getGuts();
        int distanceX = Math.abs(joueur.getX() - this.getX());
        int distanceY = Math.abs(joueur.getY() - this.getY());
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        return distance <= DISTANCE_DETECTION;
    }


    @Override
    public void attaquer() {
        int distanceAttaque = 50;
        Joueur joueur = environnement.getGuts();
        int distanceX = Math.abs(joueur.getX() - this.getX());
        int distanceY = Math.abs(joueur.getY() - this.getY());
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        if (distance <= distanceAttaque) {
            joueur.recoisDegat(this.pointAttaque);
        }
    }

    @Override
    public void recoisDegat(int degat) {
        int newPv = (getPv() + pointDefense) - degat;
        setPv(newPv);
    }

    public void suivreJoueur() {
        Joueur joueur = environnement.getGuts();
        int xDepart = this.getX() / 50;
        int yDepart = this.getY() / 50;
        int xCible = joueur.getX() / 50;
        int yCible = joueur.getY() / 50;

        Noeud noeudCible = BFS.bfs(environnement.getMap().getTab(), xDepart, yDepart, xCible, yCible);

        if (noeudCible != null) {
            Noeud noeudCourant = noeudCible;
            while (noeudCourant.parent != null) {
                int nouvelleX = noeudCourant.x * 50;
                int nouvelleY = noeudCourant.y * 50;

                if (environnement.getMap().verifierCollisions(nouvelleX, nouvelleY)) {
                    int deltaX = (nouvelleX - this.getX()) / 3;
                    int deltaY = (nouvelleY - this.getY()) / 3;
                    this.setX(this.getX() + deltaX);
                    this.setY(this.getY() + deltaY);
                    if (deltaX > 0) {
                        direction = Direction.EST;
                    } else if (deltaX < 0) {
                        direction = Direction.OUEST;
                    } else if (deltaY > 0) {
                        direction = Direction.SUD;
                    } else {
                        direction = Direction.NORD;
                    }
                    break;
                }


                noeudCourant = noeudCourant.parent;
            }
        }
    }
}
