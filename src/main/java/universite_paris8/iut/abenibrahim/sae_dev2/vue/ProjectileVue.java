package universite_paris8.iut.abenibrahim.sae_dev2.vue;

import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Projectile;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Ennemi;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;

import java.util.Iterator;
import java.util.List;

public class ProjectileVue {
    public ProjectileVue() {
    }

    public void updateEnnemiProjectiles(
            ObservableList<Projectile> projectiles,
            Joueur joueur,
            List<Circle> enemyProjectilesSprites,
            Pane paneMap
    ) {

        for (int i = enemyProjectilesSprites.size() - 1; i >= projectiles.size(); i--) {
            paneMap.getChildren().remove(enemyProjectilesSprites.get(i));
            enemyProjectilesSprites.remove(i);
        }


        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.deplacer();


            if (checkCollisionWithJoueur(projectile, joueur)) {
                joueur.recevoirDegats(projectile.getDegat());
                System.out.println("Joueur a été touché par un projectile !");
                iterator.remove();
                continue;
            }
        }


        for (int i = 0; i < projectiles.size(); i++) {
            Projectile projectile = projectiles.get(i);
            Circle projectileCircle;
            if (i < enemyProjectilesSprites.size()) {
                projectileCircle = enemyProjectilesSprites.get(i);
            } else {
                projectileCircle = new Circle(5, Color.RED); // Màu đỏ cho đạn `EnnemiProjectile`
                enemyProjectilesSprites.add(projectileCircle);
                paneMap.getChildren().add(projectileCircle);
            }
            projectileCircle.setCenterX(projectile.getX());
            projectileCircle.setCenterY(projectile.getY());
        }
    }

    private boolean checkCollisionWithJoueur(Projectile projectile, Joueur joueur) {
        int projectileX = projectile.getX();
        int projectileY = projectile.getY();
        int joueurX = joueur.getX();
        int joueurY = joueur.getY();
        int collisionDistance = 10;

        int distanceX = Math.abs(projectileX - joueurX);
        int distanceY = Math.abs(projectileY - joueurY);

        return (distanceX < collisionDistance && distanceY < collisionDistance);
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
