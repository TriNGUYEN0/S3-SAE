package universite_paris8.iut.abenibrahim.sae_dev2.controleur;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import universite_paris8.iut.abenibrahim.sae_dev2.Main;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Projectile;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.SaveData;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Acteur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.EnnemiProjectile;
import universite_paris8.iut.abenibrahim.sae_dev2.vue.*;

import java.net.URL;
import java.util.*;
import java.io.*;


public class Controleur implements Initializable {
    @FXML
    private Pane paneMap;
    private static final double ZOOM_FACTOR = 1.2;
    @FXML
    private TilePane tilePaneMap;
    @FXML
    private TilePane tilePaneMap2;

    private Environnement environnement;

    @FXML
    private TilePane premierPlanMap;

    @FXML
    private TilePane murMap;

    @FXML
    private TilePane inventairePane;
    @FXML
    private HBox slot1;
    @FXML
    private Label titre;

    @FXML
    private HBox slot2;

    @FXML
    private Label armeChoisie;

    @FXML
    private Label phrase;

    @FXML
    private Label nbSoin;
    private GameOverVue gameOverVue;
    private List<Circle> projectilesSprites = new ArrayList<>();
    private List<Circle>enemyProjectilesSprites  = new ArrayList<>();
    private ProjectileVue projectileVue;
    private PRojectileVueEnnemie pRojectileVueEnnemie;
    private PnjVue pnjVue;
    public void saveGame() {
        try {
            SaveData saveData = new SaveData(environnement);
            FileOutputStream fos = new FileOutputStream("savegame.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(saveData);
            oos.close();
            System.out.println("Partie sauvegardée avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Timeline gameLoop;
    private int temps;
    static private ImageView gutsSprite;
    private MapVue mapVue;
    private PvVue pvVue;
    private JoueurVue joueurVue;
    private EnnemiVue ennemiVue;
    private InventaireVue inventaireVue;
    private PvVueEnnemi pvVueEnnemi;
    private PvVueEnnemi2 pvVueEnnemi2;
    private List<ImageView> armeImages = new ArrayList<>();
    private List<HBox> slots;
    private ImageView ennemiSprite;
    private AnimatedEnnemiSprite animationTimer;
    private SoinVue soinvue;
    private DialogueVue dialogueVue;
    private objetDefVue objetDefVue;
    private EnnemieProjectilesVue ennemieProjectilesVue;
    @FXML
    private Label dialogueBox;
    @FXML
    private Label dialogueBox2;


    @Override
    public void initialize(URL location, ResourceBundle resources){
        paneMap.setScaleX(ZOOM_FACTOR);
        paneMap.setScaleY(ZOOM_FACTOR);

        tilePaneMap.setPrefTileWidth(50);
        tilePaneMap.setPrefTileHeight(50);

        this.environnement=new Environnement();
        Environnement env = Main.getEnvironnement();
        if (env == null) {
            this.environnement = new Environnement();
        } else {
            this.environnement = env;
        }

        this.mapVue = new MapVue(this.environnement.getMap().getTab(),this.environnement.getMap().getTab2(),environnement.getMap().getTab3(), tilePaneMap, premierPlanMap,tilePaneMap2);
        this.mapVue.remplirMap();

        this.pvVue = new PvVue(this.paneMap);
        environnement.getGuts().pvProperty().addListener((obs, oldValue, newValue) -> pvVue.updatePvJoueurImage(this.environnement.getGuts().getPv()));

        Image g = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/boy_right_1.png").toExternalForm());
        gutsSprite = new ImageView(g);

        Image e = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/ennemi-droite1-removebg-preview.png").toExternalForm());
        ennemiSprite = new ImageView(e);

        this.projectileVue = new ProjectileVue();
        this.pRojectileVueEnnemie = new PRojectileVueEnnemie();
        this.pvVueEnnemi = new PvVueEnnemi(this.paneMap);
        this.pvVueEnnemi2 = new PvVueEnnemi2(this.paneMap);
        environnement.getEnnemi().pvProperty().addListener((obs, oldValue, newValue) -> pvVueEnnemi.updatePvEnnemieImage(this.environnement.getEnnemi().getPv()));
        environnement.getEnnemiProjectile().pvProperty().addListener((obs, oldValue, newValue) -> pvVueEnnemi2.updatePvEnnemieImage(this.environnement.getEnnemiProjectile().getPv()));
        this.soinvue = new SoinVue(this.paneMap,this.nbSoin,this.environnement);
        this.soinvue.afficherSoinMap();
        soinvue.getsoinStackPane().layoutXProperty().bind(environnement.getGuts().XProprety().add(-400));
        soinvue.getsoinStackPane().layoutYProperty().bind(environnement.getGuts().YProprety().add(-100));
        soinvue.getnbsoinStackPane().layoutXProperty().bind(environnement.getGuts().XProprety().add(-325));
        soinvue.getnbsoinStackPane().layoutYProperty().bind(environnement.getGuts().YProprety().add(-95));

        slots = Arrays.asList(slot1, slot2);
        this.inventaireVue = new InventaireVue(this.paneMap, this.tilePaneMap, this.environnement, inventairePane, slot1, slot2, titre, armeChoisie, phrase, slots, gutsSprite, ennemiSprite,premierPlanMap);
        this.inventaireVue.armeMap();
        this.dialogueVue = new DialogueVue(dialogueBox,environnement,dialogueBox2);
        this.objetDefVue = new objetDefVue(environnement,paneMap);
        this.objetDefVue.afficherSoinMap();
        this.joueurVue = new JoueurVue(this.environnement.getGuts(), this.paneMap, inventaireVue, soinvue, dialogueVue, mapVue,objetDefVue);

        this.joueurVue.initialiserGuts(gutsSprite, paneMap);
        joueurVue.creerSpriteJoueur(this);

        this.ennemiVue = new EnnemiVue(environnement.getEnnemi().XProprety(), environnement.getEnnemi().YProprety(), this.paneMap, ennemiSprite);
        this.ennemiVue.creerSpriteEnnemi();
        this.ennemiVue.initialiserEnnemi(ennemiSprite, paneMap);
        this.pnjVue = new PnjVue(paneMap,environnement.getPnj().getXproperty(),environnement.getPnj().getYproperty());
        this.ennemieProjectilesVue = new EnnemieProjectilesVue(paneMap,environnement.getEnnemiProjectile().XProprety(),environnement.getEnnemiProjectile().YProprety());
        ennemieProjectilesVue.creerSpritePnj();
        ennemieProjectilesVue.initialiserEnnemieProjectiles(ennemieProjectilesVue.getEnnemieSpriteView(),paneMap);
        pnjVue.creerSpritePnj();
        pnjVue.initialiserPnj(pnjVue.getPnjSpriteView(),paneMap);
        this.animationTimer = new AnimatedEnnemiSprite(EnnemiVue.framesDroite, ennemiSprite);
        this.animationTimer.start();


        pvVue.getPvStackPane().layoutXProperty().bind(environnement.getGuts().XProprety().add(-400));
        pvVue.getPvStackPane().layoutYProperty().bind(environnement.getGuts().YProprety().add(-200));
        pvVueEnnemi.getPvStackPane().layoutXProperty().bind(environnement.getEnnemi().XProprety().add(-10));
        pvVueEnnemi.getPvStackPane().layoutYProperty().bind(environnement.getEnnemi().YProprety().add(-50));
        pvVueEnnemi2.getPvStackPane().layoutXProperty().bind(environnement.getEnnemiProjectile().XProprety().add(-10));
        pvVueEnnemi2.getPvStackPane().layoutYProperty().bind(environnement.getEnnemiProjectile().YProprety().add(-50));

        initAnimation();
        gameLoop.play();

        gameOverVue = new GameOverVue(this.paneMap);
        gameOverVue.getImageView().setVisible(false);

        this.projectilesSprites = new ArrayList<>();
        this.enemyProjectilesSprites = new ArrayList<>();


        this.inventaireVue.afficherArmes();
        this.soinvue.ajouterSoinMap();
        this.objetDefVue.ajouterObjetDefenseDansMap();

    }

    private void initAnimation() {
        temps = 0;
        gameLoop = new Timeline();
        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.200),
                ev -> {
                    if (temps == 10000) {
                        System.out.println("fini");
                        gameLoop.stop();
                    } else {
                        this.environnement.unTour();
                        this.projectileVue.updateProjectiles(environnement.getGuts().getProjectiles(), environnement.getEnnemi(), projectilesSprites, this.paneMap);
                        this.pRojectileVueEnnemie.updateProjectiles(environnement.getEnnemiProjectile().getProjectileList(),environnement.getGuts(),enemyProjectilesSprites , this.paneMap);
                        if(!this.environnement.getEnnemiProjectile().estMort()){
                            this.environnement.getEnnemiProjectile().attaquer();
                        }
                        System.out.println("PV JOUEUR : " + environnement.getGuts().getPv());
                        System.out.println("PV ENNEMI : " + environnement.getEnnemi().getPv());
                        System.out.println("pv Ennemi2 : " + environnement.getEnnemiProjectile().getPv());
                        this.environnement.getEnnemi().attaquer();
                        temps++;
                        this.ennemiVue.animerEnnemi(this.animationTimer, this.environnement.getEnnemi().getDirection());
                        if (environnement.getGuts().estMort()) {
                            gameLoop.stop();
                            paneMap.getChildren().remove(gutsSprite);
                            gameOverVue.updatePosition(environnement.getGuts().getX(), environnement.getGuts().getY());
                            gameOverVue.getImageView().setVisible(true);
                        }

                        if (environnement.getEnnemi().estMort()){
                            paneMap.getChildren().remove(ennemiSprite);
                        }
                        if(environnement.getEnnemiProjectile().estMort()){
                            paneMap.getChildren().remove(ennemieProjectilesVue.getEnnemieSpriteView());
                        }

                    }
                }
        );
        gameLoop.getKeyFrames().add(kf);
        gameLoop.setCycleCount(Timeline.INDEFINITE);
    }

    public void ajusterCameraSuiviJoueur() {
        double joueurX = environnement.getGuts().getX();
        double joueurY = environnement.getGuts().getY();

        // Ajuster pour le zoom
        double offsetX = -joueurX * ZOOM_FACTOR + (paneMap.getWidth() / 2) - (25 * ZOOM_FACTOR); // Ajustement pour le centrage horizontal
        double offsetY = -joueurY * ZOOM_FACTOR + (paneMap.getHeight() / 2) - (25 * ZOOM_FACTOR); // Ajustement pour le centrage vertical

        paneMap.setLayoutX(offsetX);
        paneMap.setLayoutY(offsetY);
    }

    static public void setGSprite(Image i){
        Controleur.gutsSprite.setImage(i);
    }

    public ImageView getGutsSprite() {
        return gutsSprite;
    }

}