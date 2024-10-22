package universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Projectile;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.serviceAction.CombatEnnemi;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.serviceAction.DeplacementEnnemi;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.ArmeDistance;

import static universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.serviceAction.DeplacementEnnemi.DISTANCE_DETECTION;

public class EnnemiProjectile extends Ennemi {

    private ObservableList<Projectile> projectiles;
    private ArmeDistance armeDistance;

    public EnnemiProjectile(Environnement e, int x, int y, int v, int pv) {
        super(e, x, y, v, pv);
        this.projectiles = FXCollections.observableArrayList();
        this.armeDistance = new ArmeDistance();
        setDirection(Direction.EST);
    }


    @Override
    public void attaquer(Acteur cible) {
        if (detecterJoueur()) {
            int projectileX = getX();
            int projectileY = getY();
            Direction directionVersJoueur = calculerDirection(cible.getX(), cible.getY());

            int vitesseProjectile = 10;
            int degatProjectile = armeDistance.getPointAttaque();

            Projectile projectile = new Projectile(projectileX, projectileY, directionVersJoueur, vitesseProjectile, degatProjectile);
            projectiles.add(projectile);

        }
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


    public boolean detecterJoueur() {
        Joueur joueur = getEnvironnement().getGuts();
        int distanceX = Math.abs(joueur.getX() - getX());
        int distanceY = Math.abs(joueur.getY() - getY());
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        return distance <= DISTANCE_DETECTION;
    }

    public ObservableList<Projectile> getProjectiles() {
        return projectiles;
    }
}
