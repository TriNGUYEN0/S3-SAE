package universite_paris8.iut.abenibrahim.sae_dev2.vue;

import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Projectile;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;

import java.util.Iterator;
import java.util.List;

public class PRojectileVueEnnemie {
    public PRojectileVueEnnemie() {

    }

    public void updateProjectiles(ObservableList<Projectile> projectiles, Joueur joueur, List<Circle> projectilesSprites, Pane paneMap) {
        // Supprimez les cercles de projectiles dépassés
        for (int i = projectilesSprites.size() - 1; i >= projectiles.size(); i--) {
            paneMap.getChildren().remove(projectilesSprites.get(i));
            projectilesSprites.remove(i);
        }

        // Déplacez les projectiles et vérifiez les collisions
        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.deplacer();
            if (checkCollision(projectile, joueur)) {
                joueur.recoisDegat(projectile.getDegat());
                iterator.remove(); // Supprimez le projectile de la liste
                int index = projectiles.indexOf(projectile);
                if (index != -1 && index < projectilesSprites.size()) {
                    paneMap.getChildren().remove(projectilesSprites.get(index));
                    projectilesSprites.remove(index);
                }
            }
        }

        // Mettez à jour les positions des cercles de projectiles
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile projectile = projectiles.get(i);
            Circle projectileCircle;
            if (i < projectilesSprites.size()) {
                projectileCircle = projectilesSprites.get(i);
            } else {
                projectileCircle = new Circle(5, Color.RED); // Taille et couleur du projectile
                projectilesSprites.add(projectileCircle);
                paneMap.getChildren().add(projectileCircle);
            }
            projectileCircle.setCenterX(projectile.getX());
            projectileCircle.setCenterY(projectile.getY());
        }
    }

    private boolean checkCollision(Projectile projectile, Joueur joueur) {
        int projectileX = projectile.getX();
        int projectileY = projectile.getY();
        int joueurX = joueur.getX();
        int joueurY = joueur.getY();
        int collisionDistance = 10; // Ajustez cette valeur si nécessaire

        int distanceX = Math.abs(projectileX - joueurX);
        int distanceY = Math.abs(projectileY - joueurY);

        return (distanceX < collisionDistance && distanceY < collisionDistance);
    }
}
