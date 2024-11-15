package universite_paris8.iut.abenibrahim.sae_dev2.controleur;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.*;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.ObjetDefense;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Arme;
import universite_paris8.iut.abenibrahim.sae_dev2.vue.*;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Soin;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack.Environnement;

public class ControleurTouche implements EventHandler<KeyEvent> {
    private final AnimatedSprite animatedSprite;
    private DialogueVue dialogueVue;
    private Controleur controleur;
    private InventaireVue inventaireVue;
    private Joueur joueur;
    private SoinVue soinVue;
    private MapVue mapVue;
    private ObjetDefVue objetDefVue;
    private Environnement environnement;

    public ControleurTouche(Joueur joueur, Environnement environnement, ImageView joueurSprite,
                            InventaireVue inventaireVue, SoinVue soinVue, DialogueVue dialogueVue,
                            MapVue mapVue, ObjetDefVue objetDefVue) {
        this.joueur = joueur;
        this.environnement = environnement;
        this.animatedSprite = new AnimatedSprite(joueur.getX(), joueur.getY(), JoueurVue.framesDroite, 0);
        this.animatedSprite.setImageView(joueurSprite);
        this.animatedSprite.setFrameActuel(0);
        this.inventaireVue = inventaireVue;
        this.dialogueVue = dialogueVue;
        this.soinVue = soinVue;
        this.mapVue = mapVue;
        this.objetDefVue = objetDefVue;
    }

    @Override
    public void handle(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        Direction direction = null;

        switch (keyCode) {
            case S -> {
                if (event.isControlDown()) {
                    if (controleur != null) controleur.saveGame();
                }
            }
            case W -> {
                if (inventaireVue.inventairePane.isVisible()) {
                    inventaireVue.masquerInventaire();
                }
            }
            case C -> {
                if (joueur.peutSeSoigner()) {
                    joueur.seSoigner();
                }
            }
            case A -> {
                joueur.attaquer(environnement.getActeurManager().getJoueur());
            }
            case SPACE -> {
                joueur.lancerProjectile();
            }
            case I -> {
                if (!inventaireVue.inventairePane.isVisible()) {
                    inventaireVue.afficherInventaire();
                }
            }
            case R -> {
                Arme armeRamassee = joueur.ramasserArme();
                Soin soin = joueur.ramasserSoin();
                ObjetDefense objetDefense = joueur.ramasserObjetDefense();

                if (armeRamassee != null) inventaireVue.supprimerArmeDeLaCarte(armeRamassee);
                if (soin != null) soinVue.supprimerSoinDeLaCarte(soin);
                if (objetDefense != null) objetDefVue.supprimerObjetDefDeLaCarte(objetDefense);
            }
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
            case UP -> {
                direction = Direction.NORD;
                animatedSprite.definirFrames(JoueurVue.framesHaut);
            }
            case DOWN -> {
                direction = Direction.SUD;
                animatedSprite.definirFrames(JoueurVue.framesBas);
            }
            case LEFT -> {
                direction = Direction.OUEST;
                animatedSprite.definirFrames(JoueurVue.framesGauche);
            }
            case RIGHT -> {
                direction = Direction.EST;
                animatedSprite.definirFrames(JoueurVue.framesDroite);
            }
        }

        if (direction != null && !inventaireVue.inventairePane.isVisible()) {
            joueur.seDeplacer(direction);
            if (controleur != null) controleur.ajusterCameraSuiviJoueur();
            mapVue.updatePlayerPosition(joueur.getX(), joueur.getY());
        }
    }

    public void actualiserControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    public AnimatedSprite getAnimatedSprite() {
        return animatedSprite;
    }
}
