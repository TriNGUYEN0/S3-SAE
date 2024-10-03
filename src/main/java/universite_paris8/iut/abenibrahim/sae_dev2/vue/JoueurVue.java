package universite_paris8.iut.abenibrahim.sae_dev2.vue;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import universite_paris8.iut.abenibrahim.sae_dev2.controleur.Controleur;
import universite_paris8.iut.abenibrahim.sae_dev2.controleur.ControleurTouche;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;

public class JoueurVue {

    private Joueur joueur;
    private ImageView gutsSprite;
    private Pane paneMap;
    private InventaireVue inventaireVue;
    private SoinVue soinVue;
    private DialogueVue dialogueVue;
    private MapVue mapVue;
    private objetDefVue objetDefVue;

    public static String[] framesGauche;
    public static String[] framesDroite;
    public static String[] framesHaut;
    public static String[] framesBas;



    public JoueurVue(Joueur joueur, Pane paneMap, InventaireVue inventaireVue, SoinVue soinVue, DialogueVue dialogueVue, MapVue mapVue,objetDefVue objetDefVue) {
        this.joueur = joueur;
        this.mapVue=mapVue;
        this.paneMap = paneMap;
        this.inventaireVue = inventaireVue;
        this.soinVue = soinVue;
        this.dialogueVue = dialogueVue;
        this.objetDefVue=objetDefVue;
        framesGauche = new String[]{ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/gauche1.png").toExternalForm() , ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/gauche2.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/gauche3.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/gauche4.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/gauche5.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/gauche6.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/gauche7.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/gauche8.png").toExternalForm()};
        framesDroite = new String[]{ ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/right1.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/right2.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/right3.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/right4.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/right5.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/right6.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/right7.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/right8.png").toExternalForm()};
        framesHaut = new String[]{ ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/up1.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/up2.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/up3.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/up4.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/up5.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/up6.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/up7.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/up8.png").toExternalForm()};
        framesBas = new String[]{ ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/down1.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/down2.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/down3.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/down4.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/down5.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/down6.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/down7.png").toExternalForm(), ControleurTouche.class.getResource("/universite_paris8/iut/abenibrahim/sae_dev2/down8.png").toExternalForm()};

    }

    public void initialiserGuts(ImageView imageView, Pane pane){
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        pane.getChildren().add(imageView);
    }


    public void creerSpriteJoueur(Controleur c) {
        gutsSprite = c.getGutsSprite();
        ControleurTouche deplacementFleche = new ControleurTouche(this.joueur, gutsSprite, inventaireVue,soinVue,dialogueVue,mapVue,objetDefVue);
        deplacementFleche.Actualiser(c);
        Scene scene = paneMap.getScene();
        if (scene != null) {
            scene.addEventHandler(KeyEvent.KEY_PRESSED, deplacementFleche);
        } else {
            paneMap.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    newScene.addEventHandler(KeyEvent.KEY_PRESSED, deplacementFleche);
                }
            });
        }
        gutsSprite = deplacementFleche.getAnimatedSprite().getImageView();
        gutsSprite.translateXProperty().bind(this.joueur.XProprety());
        gutsSprite.translateYProperty().bind(this.joueur.YProprety());
    }

}