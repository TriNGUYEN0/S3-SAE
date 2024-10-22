package universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.serviceAction;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.BFS;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Noeud;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Acteur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Ennemi;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;

public class DeplacementEnnemi implements Deplacement {

    public static final int DISTANCE_DETECTION = 100; // Phạm vi phát hiện

    @Override
    public void seDeplacer(Acteur acteur, Direction direction) {
        if (acteur instanceof Ennemi) {
            Ennemi ennemi = (Ennemi) acteur;
            if (detecterJoueur(ennemi)) {
                suivreJoueur(ennemi);
            }
        }
    }

    public boolean detecterJoueur(Ennemi ennemi) {
        Environnement environnement = ennemi.getEnvironnement();
        Joueur joueur = environnement.getGuts();

        int distanceX = Math.abs(joueur.getX() - ennemi.getX());
        int distanceY = Math.abs(joueur.getY() - ennemi.getY());
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        return distance <= DISTANCE_DETECTION;
    }

    public void suivreJoueur(Ennemi ennemi) {
        Environnement environnement = ennemi.getEnvironnement();
        Joueur joueur = environnement.getGuts();
        int xDepart = ennemi.getX() / 50;
        int yDepart = ennemi.getY() / 50;
        int xCible = joueur.getX() / 50;
        int yCible = joueur.getY() / 50;

        Noeud noeudCible = BFS.bfs(environnement.getMap().getTab(), xDepart, yDepart, xCible, yCible);

        if (noeudCible != null) {
            Noeud noeudCourant = noeudCible;
            while (noeudCourant.parent != null) {
                int nouvelleX = noeudCourant.x * 50;
                int nouvelleY = noeudCourant.y * 50;

                if (environnement.getMap().verifierCollisions(nouvelleX, nouvelleY)) {
                    int deltaX = (nouvelleX - ennemi.getX()) / 5;
                    int deltaY = (nouvelleY - ennemi.getY()) / 5;
                    ennemi.setX(ennemi.getX() + deltaX);
                    ennemi.setY(ennemi.getY() + deltaY);


                    if (deltaX > 0) {
                        ennemi.setDirection(Direction.EST);
                    } else if (deltaX < 0) {
                        ennemi.setDirection(Direction.OUEST);
                    } else if (deltaY > 0) {
                        ennemi.setDirection(Direction.SUD);
                    } else {
                        ennemi.setDirection(Direction.NORD);
                    }
                    break;
                }
                noeudCourant = noeudCourant.parent;
            }
        }
    }
}
