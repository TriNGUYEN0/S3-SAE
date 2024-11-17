package universite_paris8.iut.abenibrahim.sae_dev2.modele.strategie.deplacement;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.BFS;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Noeud;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Acteur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Ennemi;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;

public class DeplacementEnnemi implements Deplacement {
    @Override
    public void seDeplacer(Acteur acteur, Direction direction) {
        if (!(acteur instanceof Ennemi)) {
            return;
        }

        Ennemi ennemi = (Ennemi) acteur;
        Environnement environnement = ennemi.getEnvironnement();
        Joueur joueur = environnement.getActeurManager().getJoueur();

        int xDepart = ennemi.getX() / 50;
        int yDepart = ennemi.getY() / 50;
        int xCible = joueur.getX() / 50;
        int yCible = joueur.getY() / 50;

        Noeud noeudCible = BFS.bfs(environnement.getTerrainManager().getMap().getTab(), xDepart, yDepart, xCible, yCible);

        if (noeudCible != null) {
            Noeud noeudCourant = noeudCible;
            while (noeudCourant.parent != null && noeudCourant.parent.parent != null) {
                noeudCourant = noeudCourant.parent;
            }
            int nouvelleX = noeudCourant.x * 50;
            int nouvelleY = noeudCourant.y * 50;

            if (environnement.getTerrainManager().getMap().verifierCollisions(nouvelleX, nouvelleY)) {
                int deltaX = (nouvelleX - ennemi.getX()) / 5;
                int deltaY = (nouvelleY - ennemi.getY()) / 5;
                ennemi.setX(ennemi.getX() + deltaX);
                ennemi.setY(ennemi.getY() + deltaY);

                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    if (deltaX > 0) {
                        ennemi.setDirection(Direction.EST);
                    } else {
                        ennemi.setDirection(Direction.OUEST);
                    }
                } else {
                    if (deltaY > 0) {
                        ennemi.setDirection(Direction.SUD);
                    } else {
                        ennemi.setDirection(Direction.NORD);
                    }
                }
            }
        }
    }
}
