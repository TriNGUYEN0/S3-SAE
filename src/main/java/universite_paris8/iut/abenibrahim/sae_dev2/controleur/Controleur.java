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
import universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Projectile;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.SaveData;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Ennemi;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.EnnemiProjectile;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Pnj;
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
    private List<Circle> enemyProjectilesSprites  = new ArrayList<>();
    private ProjectileVue projectileVue;
    private ProjectileVueEnnemie projectileVueEnnemie;
    private PnjVue pnjVue;
    private List<HBox> slots;
    private Timeline gameLoop;
    private int temps;
    private static ImageView joueurSprite;
    private List<EnnemiVue> ennemiVues;
    private static final double ENNEMI_ATTACK_RANGE = 100.0;
    private List<EnnemieProjectilesVue> ennemiProjectilesVues;
    private MapVue mapVue;
    private PvVue pvVue;
    private JoueurVue joueurVue;
    private InventaireVue inventaireVue;
    private SoinVue soinVue;
    private DialogueVue dialogueVue;
    private ObjetDefVue objetDefVue;
    @FXML
    private Label dialogueBox;
    @FXML
    private Label dialogueBox2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        paneMap.setScaleX(ZOOM_FACTOR);
        paneMap.setScaleY(ZOOM_FACTOR);

        tilePaneMap.setPrefTileWidth(50);
        tilePaneMap.setPrefTileHeight(50);


        Environnement env = Main.getEnvironnement();
        if (env == null) {
            this.environnement = Environnement.getInstance();
            Main.setEnvironnement(this.environnement);
        } else {
            this.environnement = env;
        }

        // Khởi tạo MapVue
        this.mapVue = new MapVue(
                this.environnement.getTerrainManager().getMap().getTab(),
                this.environnement.getTerrainManager().getMap().getTab2(),
                this.environnement.getTerrainManager().getMap().getTab3(),
                tilePaneMap, premierPlanMap, tilePaneMap2);
        this.mapVue.remplirMap();

        // Lấy Joueur
        Joueur joueur = environnement.getActeurManager().getJoueur();
        if (joueur == null) {
            System.err.println("Lỗi: Joueur chưa được khởi tạo trong Environnement.");
            return;
        }


        this.pvVue = new PvVue(this.paneMap);
        joueur.pvProperty().addListener((obs, oldValue, newValue) ->
                pvVue.updatePvJoueurImage(joueur.getPv()));


        Image joueurImage = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/boy_right_1.png").toExternalForm());
        joueurSprite = new ImageView(joueurImage);
        joueurSprite.setFitWidth(50);
        joueurSprite.setFitHeight(50);


        joueurSprite.translateXProperty().bind(joueur.xProperty());
        joueurSprite.translateYProperty().bind(joueur.yProperty());

        if (!paneMap.getChildren().contains(joueurSprite)) {
            paneMap.getChildren().add(joueurSprite);
        } else {
            System.out.println("JoueurSprite déjà dans paneMap.");
        }


        List<EnnemiVue> ennemiVues = new ArrayList<>();
        for (Ennemi ennemi : environnement.getActeurManager().getEnnemis()) {
            EnnemiVue ennemiVue = new EnnemiVue(ennemi, paneMap);
            ennemiVue.creerSpriteEnnemi();
            ennemiVues.add(ennemiVue);
        }
        this.ennemiVues = ennemiVues;


        this.ennemiProjectilesVues = new ArrayList<>();

        for (EnnemiProjectile ennemiProjectile : environnement.getActeurManager().getEnnemiProjectiles()) {
            EnnemieProjectilesVue ennemiProjectileVue = new EnnemieProjectilesVue(
                    paneMap,
                    ennemiProjectile.xProperty(),
                    ennemiProjectile.yProperty()
            );
            ennemiProjectileVue.creerSpritePnj();
            ennemiProjectileVue.initialiserEnnemieProjectiles(ennemiProjectileVue.getEnnemieSpriteView(), paneMap);
            this.ennemiProjectilesVues.add(ennemiProjectileVue);

        }



        this.projectileVue = new ProjectileVue();
        this.projectileVueEnnemie = new ProjectileVueEnnemie();


        this.soinVue = new SoinVue(this.paneMap, this.nbSoin, this.environnement);
        this.soinVue.afficherSoinsSurCarte();


        slots = Arrays.asList(slot1, slot2);
        this.inventaireVue = new InventaireVue(this.paneMap, this.tilePaneMap, this.environnement,
                inventairePane, slot1, slot2, titre, armeChoisie, phrase, slots, premierPlanMap);
        this.inventaireVue.afficherArmesSurCarte();


        this.dialogueVue = new DialogueVue(dialogueBox, environnement, dialogueBox2);


        for (Pnj pnj : environnement.getActeurManager().getPnjs()) {
            PnjVue pnjVue = new PnjVue(paneMap, pnj.xProperty(), pnj.yProperty());
            pnjVue.creerSpritePnj();
            pnjVue.initialiserPnj(pnjVue.getPnjSpriteView(), paneMap);
        }

        // Khởi tạo ObjetDefVue
        this.objetDefVue = new ObjetDefVue(environnement, paneMap);
        this.objetDefVue.afficherObjetDefSurCarte();


        this.joueurVue = new JoueurVue(joueur, this.paneMap);
        this.joueurVue.creerSpriteJoueur(this);


        joueur.xProperty().addListener((obs, oldVal, newVal) -> ajusterCameraSuiviJoueur());
        joueur.yProperty().addListener((obs, oldVal, newVal) -> ajusterCameraSuiviJoueur());

        // game loop
        initAnimation();
        gameLoop.play();

        // GameOverVue
        gameOverVue = new GameOverVue(this.paneMap);
        gameOverVue.getImageView().setVisible(false);

        System.out.println("Initialization complete.");
    }


    public Environnement getEnvironnement() {
        return environnement;
    }

    public InventaireVue getInventaireVue() {
        return this.inventaireVue;
    }

    public SoinVue getSoinVue() {
        return this.soinVue;
    }

    public DialogueVue getDialogueVue() {
        return this.dialogueVue;
    }

    public MapVue getMapVue() {
        return this.mapVue;
    }

    public ObjetDefVue getObjetDefVue() {
        return this.objetDefVue;
    }


    private void initAnimation() {
        temps = 0;
        gameLoop = new Timeline();

        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.200),
                ev -> {
                    try {
                        if (temps >= 10000) {
                            System.out.println("Animation terminé");
                            gameLoop.stop();
                            return;
                        }

                        Joueur joueur = environnement.getActeurManager().getJoueur();


                        for (Iterator<EnnemiVue> it = ennemiVues.iterator(); it.hasNext(); ) {
                            EnnemiVue ennemiVue = it.next();
                            Ennemi ennemi = ennemiVue.getEnnemi();

                            if (ennemi.estVivant()) {
                                ennemi.seDeplacer(ennemi.getDirection());
                                ennemi.attaquer(joueur);
                                ennemiVue.animerEnnemi(ennemi.getDirection());
                            } else {

                                paneMap.getChildren().remove(ennemiVue.getEnnemiSprite());
                                it.remove();
                            }
                        }


                        for (Iterator<EnnemieProjectilesVue> it = ennemiProjectilesVues.iterator(); it.hasNext(); ) {
                            EnnemieProjectilesVue ennemiProjectileVue = it.next();
                            EnnemiProjectile ennemiProjectile = environnement.getActeurManager().getEnnemiProjectiles().stream()
                                    .filter(e -> e.xProperty().get() == (int) ennemiProjectileVue.getEnnemieSpriteView().getTranslateX() &&
                                            e.yProperty().get() == (int) ennemiProjectileVue.getEnnemieSpriteView().getTranslateY())
                                    .findFirst()
                                    .orElse(null);

                            if (ennemiProjectile != null && ennemiProjectile.estVivant()) {

                                ennemiProjectile.seDeplacer(ennemiProjectile.getDirection());


                                double distance = Math.sqrt(
                                        Math.pow(joueur.getX() - ennemiProjectile.getX(), 2) +
                                                Math.pow(joueur.getY() - ennemiProjectile.getY(), 2)
                                );

                                if (distance <= ENNEMI_ATTACK_RANGE) {
                                    ennemiProjectile.attaquer(joueur);

                                    Projectile projectile = new Projectile(
                                            ennemiProjectile.getX(),
                                            ennemiProjectile.getY(),
                                            ennemiProjectile.getDirection(),
                                            10,
                                            15
                                    );
                                    ennemiProjectile.getProjectiles().add(projectile);
                                }
                            } else {

                                paneMap.getChildren().remove(ennemiProjectileVue.getEnnemieSpriteView());
                                it.remove();
                            }
                        }


                        for (EnnemiProjectile ennemiProjectile : environnement.getActeurManager().getEnnemiProjectiles()) {

                            projectileVueEnnemie.updateProjectiles(
                                    ennemiProjectile.getProjectiles(),
                                    joueur,
                                    enemyProjectilesSprites,
                                    paneMap
                            );

                        }


                        if (!joueur.estVivant()) {
                            System.out.println("Game Over: Joueur out !");
                            gameLoop.stop();
                            paneMap.getChildren().remove(joueurSprite);
                            if (gameOverVue != null) {
                                gameOverVue.updatePosition(joueur.getX(), joueur.getY());
                                gameOverVue.getImageView().setVisible(true);
                            }
                        }

                        temps++;
                    } catch (Exception ex) {
                        System.err.println("error gameLoop: " + ex.getMessage());
                        ex.printStackTrace();
                        gameLoop.stop();
                    }
                }
        );

        gameLoop.getKeyFrames().add(kf);
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }




    public void ajusterCameraSuiviJoueur() {
        Joueur joueur = environnement.getActeurManager().getJoueur();
        double joueurX = joueur.getX();
        double joueurY = joueur.getY();


        double sceneWidth = paneMap.getScene().getWidth();
        double sceneHeight = paneMap.getScene().getHeight();


        double offsetX = -joueurX * ZOOM_FACTOR + (sceneWidth / 2) - (joueurSprite.getFitWidth() / 2) * ZOOM_FACTOR;
        double offsetY = -joueurY * ZOOM_FACTOR + (sceneHeight / 2) - (joueurSprite.getFitHeight() / 2) * ZOOM_FACTOR;

        paneMap.setLayoutX(offsetX);
        paneMap.setLayoutY(offsetY);
    }

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
}
