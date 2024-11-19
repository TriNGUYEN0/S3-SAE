package universite_paris8.iut.abenibrahim.sae_dev2.controleur;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Acteur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Ennemi;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.EnnemiProjectile;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Arme;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.ObjetDefense;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Soin;
import universite_paris8.iut.abenibrahim.sae_dev2.vue.*;

public class ControleurTouche implements EventHandler<KeyEvent> {
    private final Joueur joueur;
    private final Environnement environnement;
    private final JoueurVue joueurVue;
    private final InventaireVue inventaireVue;
    private final SoinVue soinVue;
    private final MapVue mapVue;
    private final ObjetDefVue objetDefVue;
    private final DialogueVue dialogueVue;
    private Controleur controleur;

    public ControleurTouche(
            Joueur joueur,
            Environnement environnement,
            JoueurVue joueurVue,
            InventaireVue inventaireVue,
            SoinVue soinVue,
            MapVue mapVue,
            ObjetDefVue objetDefVue,
            DialogueVue dialogueVue
    ) {
        this.joueur = joueur;
        this.environnement = environnement;
        this.joueurVue = joueurVue;
        this.inventaireVue = inventaireVue;
        this.soinVue = soinVue;
        this.mapVue = mapVue;
        this.objetDefVue = objetDefVue;
        this.dialogueVue = dialogueVue;
    }


    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }



    @Override
    public void handle(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        Direction direction = null;

        switch (keyCode) {

            case UP -> direction = Direction.NORD;
            case DOWN -> direction = Direction.SUD;
            case LEFT -> direction = Direction.OUEST;
            case RIGHT -> direction = Direction.EST;


            case S -> {
                if (event.isControlDown() && controleur != null) {
                    controleur.saveGame();
                }
            }


            case I -> {
                if (inventaireVue.inventairePane.isVisible()) {
                    inventaireVue.masquerInventaire();
                } else {
                    inventaireVue.afficherInventaire();
                }
            }

            case R -> {
                Arme arme = joueur.ramasserArme();
                Soin soin = joueur.ramasserSoin();
                ObjetDefense objetDefense = joueur.ramasserObjetDefense();

                if (arme != null) {
                    inventaireVue.supprimerArmeDeLaCarte(arme); // Xóa khỏi giao diện
                    environnement.getObjetManager().supprimerArme(arme);
                }
                if (soin != null) {
                    soinVue.supprimerSoinDeLaCarte(soin);
                }
                if (objetDefense != null) {
                    objetDefVue.supprimerObjetDefDeLaCarte(objetDefense);
                }
            }


            case A -> {
                Ennemi ennemiCible = environnement.getActeurManager().getEnnemis().stream()
                        .filter(Ennemi::estVivant)
                        .min((e1, e2) -> {
                            double distance1 = Math.sqrt(Math.pow(e1.getX() - joueur.getX(), 2) + Math.pow(e1.getY() - joueur.getY(), 2));
                            double distance2 = Math.sqrt(Math.pow(e2.getX() - joueur.getX(), 2) + Math.pow(e2.getY() - joueur.getY(), 2));
                            return Double.compare(distance1, distance2);
                        })
                        .orElse(null);

                if (ennemiCible != null) {
                    joueur.attaquer(ennemiCible);
                }

                EnnemiProjectile cible = environnement.getActeurManager().getEnnemiProjectiles().stream()
                        .filter(e -> Math.abs(e.getX() - joueur.getX()) < 50 && Math.abs(e.getY() - joueur.getY()) < 50)
                        .findFirst()
                        .orElse(null);
                if (cible != null) {
                    joueur.attaquer(cible);
                }
            }


            case C -> {
                if (joueur.peutSeSoigner()) {
                    joueur.seSoigner();
                }
            }


            case SPACE -> joueur.lancerProjectile();


            case P -> {
                if (joueur.peutParler()) {
                    dialogueVue.afficherDialoguePnj();
                }
            }
            case ENTER -> {
                if (dialogueVue.dialogueBox.isVisible() || dialogueVue.dialogueBox2.isVisible()) {
                    dialogueVue.masquerDialogue();
                }
            }
        }


        if (direction != null) {
            joueur.setDirection(direction);
            if (!inventaireVue.inventairePane.isVisible()) {
                joueur.seDeplacer(direction);
                joueurVue.updateFrame(direction.name());
                mapVue.updatePlayerPosition(joueur.getX(), joueur.getY());
                if (controleur != null) {
                    controleur.ajusterCameraSuiviJoueur();
                }
            }
        }
    }
}
