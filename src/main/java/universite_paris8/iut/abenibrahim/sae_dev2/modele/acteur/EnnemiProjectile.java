package universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Projectile;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.ArmeDistance;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.strategie.combat.CombatEnnemiProjectile;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.strategie.deplacement.DeplacementEnnemiProjectile;

public class EnnemiProjectile extends Acteur {
    private ArmeDistance armeDistance;
    private ObservableList<Projectile> projectiles;
    private Direction direction;


    private static final int DISTANCE_DETECTION = 500;

    public EnnemiProjectile(Environnement e, int x, int y, int v, int pv) {
        super(e, x, y, v, pv);
        this.armeDistance = new ArmeDistance();
        this.projectiles = FXCollections.observableArrayList();
        this.direction = Direction.EST;

        this.setCombatStrategy(new CombatEnnemiProjectile(this));
        this.setDeplacementStrategy(new DeplacementEnnemiProjectile());
    }

    public boolean detecterJoueur() {
        Joueur joueur = this.getEnvironnement().getActeurManager().getJoueur();
        int distanceX = Math.abs(joueur.getX() - this.getX());
        int distanceY = Math.abs(joueur.getY() - this.getY());
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        return distance <= DISTANCE_DETECTION;
    }


    public ArmeDistance getArmeDistance() {
        return armeDistance;
    }

    public ObservableList<Projectile> getProjectiles() {
        return projectiles;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
