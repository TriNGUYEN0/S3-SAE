package universite_paris8.iut.abenibrahim.sae_dev2.vue;

import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Projectile;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Ennemi;

import java.util.Iterator;
import java.util.List;

public class ProjectileVue {
    public ProjectileVue() {
    }

    public void updateProjectiles(
            ObservableList<Projectile> projectiles,
            List<Ennemi> ennemis,
            List<Circle> projectilesSprites,
            Pane paneMap
    ) {

        for (int i = projectilesSprites.size() - 1; i >= projectiles.size(); i--) {
            paneMap.getChildren().remove(projectilesSprites.get(i));
            projectilesSprites.remove(i);
        }


        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.deplacer();


            for (Iterator<Ennemi> ennemiIterator = ennemis.iterator(); ennemiIterator.hasNext(); ) {
                Ennemi ennemi = ennemiIterator.next();
                if (checkCollision(projectile, ennemi)) {
                    ennemi.recevoirDegats(projectile.getDegat());
                    if (!ennemi.estVivant()) {
                        paneMap.getChildren().remove(ennemi.getSprite());
                        ennemiIterator.remove();
                    }
                    iterator.remove();
                    break;
                }
            }
        }


        for (int i = 0; i < projectiles.size(); i++) {
            Projectile projectile = projectiles.get(i);
            Circle projectileCircle;
            if (i < projectilesSprites.size()) {
                projectileCircle = projectilesSprites.get(i);
            } else {
                projectileCircle = new Circle(5, Color.BLUE); // Taille et couleur du projectile
                projectilesSprites.add(projectileCircle);
                paneMap.getChildren().add(projectileCircle);
            }
            projectileCircle.setCenterX(projectile.getX());
            projectileCircle.setCenterY(projectile.getY());
        }
    }

    private boolean checkCollision(Projectile projectile, Ennemi ennemi) {
        int projectileX = projectile.getX();
        int projectileY = projectile.getY();
        int ennemiX = (int) ennemi.getSprite().getX();
        int ennemiY = (int) ennemi.getSprite().getY();
        int collisionDistance = 10;

        int distanceX = Math.abs(projectileX - ennemiX);
        int distanceY = Math.abs(projectileY - ennemiY);

        return (distanceX < collisionDistance && distanceY < collisionDistance);
    }

}
