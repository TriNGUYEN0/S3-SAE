package universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.serviceAction;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Acteur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;

public class DeplacementJoueur implements Deplacement {

    @Override
    public void seDeplacer(Acteur acteur, Direction direction) {
        if (acteur instanceof Joueur) {
            Joueur joueur = (Joueur) acteur;
            Environnement environnement = joueur.getEnvironnement();
            int xTmp = joueur.getX() + direction.getX() * joueur.getVitesse();
            int yTmp = joueur.getY() + direction.getY() * joueur.getVitesse();

            // Kiểm tra nếu vị trí mới nằm trong bản đồ và không có va chạm
            if (environnement.dansTerrain(xTmp, yTmp) && environnement.getMap().verifierCollisions(xTmp, yTmp)) {
                joueur.setX(xTmp);
                joueur.setY(yTmp);
                joueur.setLastDirection(direction); // Cập nhật hướng cuối cùng
            }
        }
    }
}
