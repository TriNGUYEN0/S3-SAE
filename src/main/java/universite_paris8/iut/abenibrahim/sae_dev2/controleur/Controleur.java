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
        System.out.println("Controleur đã được khởi tạo.");

        if (paneMap == null) {
            System.err.println("Lỗi: paneMap chưa được tiêm (injected). Kiểm tra fx:id trong FXML.");
        } else {
            paneMap.setStyle("-fx-background-color: lightblue;"); // Đặt màu nền để kiểm tra
        }

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

// Nếu danh sách kẻ thù trống, thêm một kẻ thù mặc định
        if (ennemis.isEmpty()) {
            Ennemi ennemi = new Ennemi(environnement, 300, 300, 50, 100); // Tạo kẻ thù tại vị trí (300, 300)
            environnement.getActeurManager().ajouterEnnemi(ennemi);       // Thêm vào danh sách
            ennemis = environnement.getActeurManager().getEnnemis();     // Cập nhật danh sách
        }

        if (!ennemis.isEmpty()) {
            Ennemi premierEnnemi = ennemis.get(0);

            if (premierEnnemi.getSprite() != null && !paneMap.getChildren().contains(premierEnnemi.getSprite())) {
                paneMap.getChildren().add(premierEnnemi.getSprite());
                System.out.println("Ennemi sprite added to paneMap.");
            } else {
                System.err.println("Ennemi sprite already exists in paneMap or is null.");
            }
        }



        this.projectileVue = new ProjectileVue();
        this.projectileVueEnnemie = new ProjectileVueEnnemie();

        this.soinVue = new SoinVue(this.paneMap, this.nbSoin, this.environnement);
        this.soinVue.afficherSoinsSurCarte();
        soinVue.getSoinStackPane().layoutXProperty().bind(joueur.xProperty().add(-400));
        soinVue.getSoinStackPane().layoutYProperty().bind(joueur.yProperty().add(-100));
        soinVue.getNbSoinStackPane().layoutXProperty().bind(joueur.xProperty().add(-325));
        soinVue.getNbSoinStackPane().layoutYProperty().bind(joueur.yProperty().add(-95));

        slots = Arrays.asList(slot1, slot2);
        this.inventaireVue = new InventaireVue(this.paneMap, this.tilePaneMap, this.environnement,
                inventairePane, slot1, slot2, titre, armeChoisie, phrase, slots, premierPlanMap);
        this.inventaireVue.afficherArmesSurCarte();

        this.dialogueVue = new DialogueVue(dialogueBox, environnement, dialogueBox2);

        this.objetDefVue = new ObjetDefVue(environnement, paneMap);
        this.objetDefVue.afficherObjetDefSurCarte();


        this.joueurVue = new JoueurVue(
                joueur,
                this.paneMap
        );


        this.joueurVue.creerSpriteJoueur(this);





        if (!environnement.getActeurManager().getPnjs().isEmpty()) {
            this.pnjVue = new PnjVue(paneMap,
                    environnement.getActeurManager().getPnjs().get(0).xProperty(),
                    environnement.getActeurManager().getPnjs().get(0).yProperty());
            this.pnjVue.creerSpritePnj();
            this.pnjVue.initialiserPnj(pnjVue.getPnjSpriteView(), paneMap);
        }

        initAnimation();
        gameLoop.play();

        gameOverVue = new GameOverVue(this.paneMap);
        gameOverVue.getImageView().setVisible(false);

        this.projectilesSprites = new ArrayList<>();
        this.enemyProjectilesSprites = new ArrayList<>();


        this.inventaireVue.afficherArmesSurCarte();
        this.soinVue.afficherSoinsSurCarte();
        this.objetDefVue.afficherObjetDefSurCarte();
    }

    public Environnement getEnvironnement() {
        return this.environnement;
    }

    private void initAnimation() {
        temps = 0;
        gameLoop = new Timeline();
        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.200), // 5 lần chạy mỗi giây
                ev -> {
                    try {
                        if (temps >= 10000) {
                            System.out.println("Animation terminé");
                            gameLoop.stop();
                            return;
                        }

                        // 1. Kiểm tra môi trường và Joueur
                        if (environnement == null || environnement.getActeurManager() == null) {
                            System.err.println("Lỗi: Environnement hoặc ActeurManager chưa được khởi tạo.");
                            gameLoop.stop();
                            return;
                        }

                        Joueur joueur = environnement.getActeurManager().getJoueur();
                        if (joueur == null || joueurVue == null) {
                            System.err.println("Lỗi: Joueur hoặc JoueurVue chưa được khởi tạo.");
                            gameLoop.stop();
                            return;
                        }

                        // 2. Cập nhật vị trí của Joueur và Sprite
                        joueurVue.updateFrame(joueur.getLastDirection().name());
                        joueurVue.getJoueurSprite().setTranslateX(joueur.getX());
                        joueurVue.getJoueurSprite().setTranslateY(joueur.getY());

                        // 3. Camera theo Joueur
                        ajusterCameraSuiviJoueur();

                        // 4. Cập nhật và kiểm tra các projectiles
                        List<Ennemi> ennemis = environnement.getActeurManager().getEnnemis();
                        if (projectileVue != null) {
                            projectileVue.updateProjectiles(
                                    joueur.getProjectiles(),
                                    ennemis,
                                    projectilesSprites,
                                    this.paneMap
                            );
                        } else {
                            System.err.println("Lỗi: ProjectileVue chưa được khởi tạo.");
                        }

                        // 5. Xử lý kẻ thù (Ennemis)
                        if (ennemis != null) {
                            for (Iterator<Ennemi> it = ennemis.iterator(); it.hasNext(); ) {
                                Ennemi ennemi = it.next();
                                if (ennemi.estVivant()) {
                                    ennemi.seDeplacer(ennemi.getDirection());
                                    ennemi.updateSpritePosition(); // Cập nhật vị trí sprite của kẻ thù
                                    ennemi.attaquer(joueur);
                                } else {
                                    // Loại bỏ sprite khi kẻ thù chết
                                    paneMap.getChildren().remove(ennemi.getSprite());
                                    it.remove();
                                }
                            }
                        } else {
                            System.err.println("Lỗi: Danh sách kẻ thù (ennemis) không tồn tại.");
                        }

                        // 6. Kiểm tra Joueur còn sống
                        if (!joueur.estVivant()) {
                            System.out.println("Game Over: Joueur đã chết.");
                            gameLoop.stop();
                            paneMap.getChildren().remove(joueurVue.getJoueurSprite());
                            if (gameOverVue != null) {
                                gameOverVue.updatePosition(joueur.getX(), joueur.getY());
                                gameOverVue.getImageView().setVisible(true);
                            } else {
                                System.err.println("Lỗi: GameOverVue chưa được khởi tạo.");
                            }
                        }

                        // 7. Tăng bộ đếm thời gian
                        temps++;
                    } catch (Exception ex) {
                        System.err.println("Lỗi xảy ra trong vòng lặp gameLoop: " + ex.getMessage());
                        ex.printStackTrace();
                        gameLoop.stop();
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

        // Kích thước cửa sổ hiển thị
        double windowWidth = paneMap.getWidth();
        double windowHeight = paneMap.getHeight();

        // Tính toán vị trí của TilePane để Joueur luôn ở giữa màn hình
        double offsetX = -joueurX * 50 + windowWidth / 2 - 25; // 50 là kích thước ô (tile)
        double offsetY = -joueurY * 50 + windowHeight / 2 - 25;

        // Đặt vị trí TilePane
        tilePaneMap.setLayoutX(offsetX);
        tilePaneMap.setLayoutY(offsetY);

        premierPlanMap.setLayoutX(offsetX);
        premierPlanMap.setLayoutY(offsetY);

        if (tilePaneMap2.isVisible()) {
            tilePaneMap2.setLayoutX(offsetX);
            tilePaneMap2.setLayoutY(offsetY);
        }
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
