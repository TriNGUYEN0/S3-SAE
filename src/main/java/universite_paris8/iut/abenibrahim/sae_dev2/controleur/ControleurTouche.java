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

public class ControleurTouche implements EventHandler<KeyEvent> {
    private final AnimatedSprite animatedSprite;
    private DialogueVue dialogueVue;
    public Controleur ct;
    private InventaireVue inventaireVue;
    private Joueur joueur;
    private SoinVue soinVue;
    private MapVue mapVue;
    private ObjetDefVue objetDefVue;
    private ArmeVue armeVue;
    public ControleurTouche(Joueur joueur, ImageView v, InventaireVue inventaireVue, SoinVue soinVue, DialogueVue dialogueVue, MapVue mapVue, ObjetDefVue objetDefVue, ArmeVue armeVue) {
        this.animatedSprite = new AnimatedSprite(joueur.getX(), joueur.getY(), JoueurVue.framesDroite, 0);
        this.animatedSprite.setImageView(v);
        this.animatedSprite.setFrameActuel(0);
        this.inventaireVue = inventaireVue;
        this.dialogueVue = dialogueVue;
        this.soinVue = soinVue;
        this.joueur = joueur;
        this.mapVue=mapVue;
        this.objetDefVue=objetDefVue;
        this.armeVue=armeVue;
    }

    @Override
    public void handle(KeyEvent event) {
        KeyCode k = event.getCode();
        Direction direction = null;
        switch (k){
            case S -> {
                if (event.isControlDown()) {
                    ct.saveGame();
                }
            }
            case W -> {
                if (inventaireVue.inventairePane.isVisible()) {
                    inventaireVue.masquerInventaire();
                }
            }
            case C -> {
                if(this.joueur.peutSeSoigner()){
                    this.joueur.seSoigner();
                }
            }
            case A -> {
                this.joueur.attaquer();
            }
            case SPACE -> {
                this.joueur.lancerProjectile();
            }
            case I -> {
                if (!inventaireVue.inventairePane.isVisible()) {
                    inventaireVue.afficherInventaire();
                }
            }
            case R -> {
                Arme ramassee = this.joueur.ramasserArme();
                Soin soin = this.joueur.ramasserSoin();
                ObjetDefense objetDefense = this.joueur.ramasserObjetDefense();
                if (ramassee != null && ct != null && armeVue != null) {
                    System.out.println("ramasseee");
                    this.armeVue.supprimerArmeDeLaCarte();
                }
                if (soin != null && ct != null && soinVue != null) {
                    soinVue.supprimerSoinVue();
                }
                if (objetDefense != null && ct != null && objetDefVue != null) {
                    objetDefVue.supprimerObjetDefVue();
                }
            }
            case P -> {
                if (joueur.peutParler()){
                    dialogueVue.afficherDialoguePnj();
                }
            }
            case ENTER -> {
                if(dialogueVue.dialogueBox.isVisible() || dialogueVue.dialogueBox2.isVisible()){
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
        if (direction != null)
            if (!inventaireVue.inventairePane.isVisible()){
                this.joueur.seDeplace(direction);
                ct.ajusterCameraSuiviJoueur();
                mapVue.updatePlayerPosition(joueur.getX(), joueur.getY());
                System.out.println(joueur.getX() + " " + joueur.getY());
            }
    }
    public void Actualiser(Controleur c){
        this.ct = c;
    }

    public AnimatedSprite getAnimatedSprite() {
        return animatedSprite;
    }
}