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
import universite_paris8.iut.abenibrahim.sae_dev2.modele.SaveData;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Ennemi;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;
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

    private Timeline gameLoop;
    private int temps;
    private static ImageView joueurSprite;
    private MapVue mapVue;
    private PvVue pvVue;
    private JoueurVue joueurVue;
    private EnnemiVue ennemiVue;
    private InventaireVue inventaireVue;
    private PvVueEnnemi pvVueEnnemi;
    private List<HBox> slots;
    private ImageView ennemiSprite;
    private AnimatedEnnemiSprite animationTimer;
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
            this.environnement = new Environnement();
        } else {
            this.environnement = env;
        }

        this.mapVue = new MapVue(
                this.environnement.getTerrainManager().getMap().getTab(),
                this.environnement.getTerrainManager().getMap().getTab2(),
                this.environnement.getTerrainManager().getMap().getTab3(),
                tilePaneMap, premierPlanMap, tilePaneMap2);
        this.mapVue.remplirMap();

        this.pvVue = new PvVue(this.paneMap);
        Joueur joueur = environnement.getActeurManager().getJoueur();
        joueur.pvProperty().addListener((obs, oldValue, newValue) ->
                pvVue.updatePvJoueurImage(joueur.getPv()));

        Image joueurImage = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/boy_right_1.png").toExternalForm());
        joueurSprite = new ImageView(joueurImage);

        List<Ennemi> ennemis = environnement.getActeurManager().getEnnemis();
        if (!ennemis.isEmpty()) {
            Ennemi premierEnnemi = ennemis.get(0); // Giả sử lấy kẻ thù đầu tiên
            Image ennemiImage = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/ennemi-droite1-removebg-preview.png").toExternalForm());
            ennemiSprite = new ImageView(ennemiImage);

            this.ennemiVue = new EnnemiVue(premierEnnemi.XProperty(), premierEnnemi.YProperty(), this.paneMap, ennemiSprite);
            this.ennemiVue.creerSpriteEnnemi();
            this.ennemiVue.initialiserEnnemi(ennemiSprite, paneMap);

            this.pvVueEnnemi = new PvVueEnnemi(this.paneMap);
            premierEnnemi.pvProperty().addListener((obs, oldValue, newValue) ->
                    pvVueEnnemi.updatePvEnnemieImage(premierEnnemi.getPv()));
        }

        this.projectileVue = new ProjectileVue();
        this.projectileVueEnnemie = new ProjectileVueEnnemie();

        this.soinVue = new SoinVue(this.paneMap, this.nbSoin, this.environnement);
        this.soinVue.afficherSoinsSurCarte();
        soinVue.getSoinStackPane().layoutXProperty().bind(joueur.XProperty().add(-400));
        soinVue.getSoinStackPane().layoutYProperty().bind(joueur.YProperty().add(-100));
        soinVue.getNbSoinStackPane().layoutXProperty().bind(joueur.XProperty().add(-325));
        soinVue.getNbSoinStackPane().layoutYProperty().bind(joueur.YProperty().add(-95));

        slots = Arrays.asList(slot1, slot2);
        this.inventaireVue = new InventaireVue(this.paneMap, this.tilePaneMap, this.environnement,
                inventairePane, slot1, slot2, titre, armeChoisie, phrase, slots, premierPlanMap);
        this.inventaireVue.afficherArmesSurCarte();

        this.dialogueVue = new DialogueVue(dialogueBox, environnement, dialogueBox2);

        this.objetDefVue = new ObjetDefVue(environnement, paneMap);
        this.objetDefVue.afficherObjetDefSurCarte();

        this.joueurVue = new JoueurVue(joueur,
                this.paneMap, inventaireVue, soinVue, dialogueVue, mapVue, objetDefVue);

        this.joueurVue.initialiserJoueur(joueurSprite, paneMap);
        this.joueurVue.creerSpriteJoueur(this);

        // Khởi tạo PNJ
        if (!environnement.getActeurManager().getPnjs().isEmpty()) {
            this.pnjVue = new PnjVue(paneMap,
                    environnement.getActeurManager().getPnjs().get(0).XProperty(),
                    environnement.getActeurManager().getPnjs().get(0).YProperty());
            this.pnjVue.creerSpritePnj();
            this.pnjVue.initialiserPnj(pnjVue.getPnjSpriteView(), paneMap);
        }

        initAnimation();
        gameLoop.play();

        gameOverVue = new GameOverVue(this.paneMap);
        gameOverVue.getImageView().setVisible(false);

        this.projectilesSprites = new ArrayList<>();
        this.enemyProjectilesSprites = new ArrayList<>();

        // Hiển thị các vật phẩm trên bản đồ
        this.inventaireVue.afficherArmesSurCarte();
        this.soinVue.afficherSoinsSurCarte();
        this.objetDefVue.afficherObjetDefSurCarte();
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
                        Joueur joueur = environnement.getActeurManager().getJoueur();
                        List<Ennemi> ennemis = environnement.getActeurManager().getEnnemis();

                        // Cập nhật projectiles của người chơi
                        this.projectileVue.updateProjectiles(joueur.getProjectiles(), ennemis, projectilesSprites, this.paneMap);

                        // Cập nhật projectiles của kẻ thù
                        List<Projectile> enemyProjectiles = new ArrayList<>();
                        for (Ennemi ennemi : ennemis) {
                            enemyProjectiles.addAll(ennemi.getProjectiles());
                        }
                        this.projectileVueEnnemie.updateProjectiles(enemyProjectiles, joueur, enemyProjectilesSprites, this.paneMap);

                        // Hành động của kẻ thù
                        for (Ennemi ennemi : ennemis) {
                            if (!ennemi.estMort()) {
                                ennemi.attaquer();
                                // Cập nhật hoạt ảnh kẻ thù nếu cần
                                this.ennemiVue.animerEnnemi(animationTimer, ennemi.getDirection());
                            } else {
                                paneMap.getChildren().remove(ennemiSprite);
                            }
                        }

                        if (joueur.estMort()) {
                            gameLoop.stop();
                            paneMap.getChildren().remove(joueurSprite);
                            gameOverVue.updatePosition(joueur.getX(), joueur.getY());
                            gameOverVue.getImageView().setVisible(true);
                        }

                        temps++;
                    }
                }
        );
        gameLoop.getKeyFrames().add(kf);
        gameLoop.setCycleCount(Timeline.INDEFINITE);
    }

    public void ajusterCameraSuiviJoueur() {
        Joueur joueur = environnement.getActeurManager().getJoueur();
        double joueurX = joueur.getX();
        double joueurY = joueur.getY();

        // Ajuster pour le zoom
        double offsetX = -joueurX * ZOOM_FACTOR + (paneMap.getWidth() / 2) - (25 * ZOOM_FACTOR);
        double offsetY = -joueurY * ZOOM_FACTOR + (paneMap.getHeight() / 2) - (25 * ZOOM_FACTOR);

        paneMap.setLayoutX(offsetX);
        paneMap.setLayoutY(offsetY);
    }

    public static void setJoueurSprite(Image i) {
        Controleur.joueurSprite.setImage(i);
    }

    public ImageView getJoueurSprite() {
        return joueurSprite;
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
