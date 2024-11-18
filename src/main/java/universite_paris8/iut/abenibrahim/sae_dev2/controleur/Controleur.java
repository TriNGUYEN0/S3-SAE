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
    private List<HBox> slots;
    private Timeline gameLoop;
    private int temps;
    private static ImageView joueurSprite;
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
        System.out.println("Controleur đã được khởi tạo.");

        if (paneMap == null) {
            System.err.println("Lỗi: paneMap chưa được tiêm (injected). Kiểm tra fx:id trong FXML.");
            return;
        } else {
            paneMap.setStyle("-fx-background-color: lightblue;"); // Đặt màu nền để kiểm tra
        }

        paneMap.setScaleX(ZOOM_FACTOR);
        paneMap.setScaleY(ZOOM_FACTOR);

        tilePaneMap.setPrefTileWidth(50);
        tilePaneMap.setPrefTileHeight(50);

        // Sử dụng Singleton Environnement
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

        // Khởi tạo PvVue và ràng buộc với Joueur
        this.pvVue = new PvVue(this.paneMap);
        joueur.pvProperty().addListener((obs, oldValue, newValue) ->
                pvVue.updatePvJoueurImage(joueur.getPv()));

        // Tạo ImageView cho Joueur
        Image joueurImage = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/boy_right_1.png").toExternalForm());
        joueurSprite = new ImageView(joueurImage);
        joueurSprite.setFitWidth(50);
        joueurSprite.setFitHeight(50);

        // Ràng buộc vị trí của JoueurSprite với Joueur
        joueurSprite.translateXProperty().bind(joueur.xProperty());
        joueurSprite.translateYProperty().bind(joueur.yProperty());

        if (!paneMap.getChildren().contains(joueurSprite)) {
            paneMap.getChildren().add(joueurSprite);
            System.out.println("Đã thêm Joueur vào paneMap.");
        } else {
            System.out.println("JoueurSprite đã tồn tại trong paneMap.");
        }

        // Khởi tạo ProjectileVue
        this.projectileVue = new ProjectileVue();
        this.projectileVueEnnemie = new ProjectileVueEnnemie();

        // Khởi tạo SoinVue
        this.soinVue = new SoinVue(this.paneMap, this.nbSoin, this.environnement);
        this.soinVue.afficherSoinsSurCarte();
        soinVue.getSoinStackPane().layoutXProperty().bind(joueur.xProperty().add(-400));
        soinVue.getSoinStackPane().layoutYProperty().bind(joueur.yProperty().add(-100));
        soinVue.getNbSoinStackPane().layoutXProperty().bind(joueur.xProperty().add(-325));
        soinVue.getNbSoinStackPane().layoutYProperty().bind(joueur.yProperty().add(-95));

        // Khởi tạo InventaireVue
        slots = Arrays.asList(slot1, slot2);

        this.inventaireVue = new InventaireVue(this.paneMap, this.tilePaneMap, this.environnement,
                inventairePane, slot1, slot2, titre, armeChoisie, phrase, slots, premierPlanMap);
        this.inventaireVue.afficherArmesSurCarte();

        // Khởi tạo DialogueVue
        this.dialogueVue = new DialogueVue(dialogueBox, environnement, dialogueBox2);

        // Khởi tạo ObjetDefVue
        this.objetDefVue = new ObjetDefVue(environnement, paneMap);
        this.objetDefVue.afficherObjetDefSurCarte();

        // Khởi tạo JoueurVue
        this.joueurVue = new JoueurVue(joueur, this.paneMap);
        this.joueurVue.creerSpriteJoueur(this);

        // Thêm listener để camera di chuyển theo Joueur
        joueur.xProperty().addListener((obs, oldVal, newVal) -> ajusterCameraSuiviJoueur());
        joueur.yProperty().addListener((obs, oldVal, newVal) -> ajusterCameraSuiviJoueur());

        // Khởi tạo game loop
        initAnimation();
        gameLoop.play();

        // Khởi tạo GameOverVue
        gameOverVue = new GameOverVue(this.paneMap);
        gameOverVue.getImageView().setVisible(false);

        // Khởi tạo danh sách Projectile
        this.projectilesSprites = new ArrayList<>();
        this.enemyProjectilesSprites = new ArrayList<>();

        // Hiển thị tất cả các thành phần
        this.inventaireVue.afficherArmesSurCarte();
        this.soinVue.afficherSoinsSurCarte();
        this.objetDefVue.afficherObjetDefSurCarte();

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

                        // Cập nhật projectiles của Joueur
                        if (projectileVue != null) {
                            projectileVue.updateProjectiles(
                                    joueur.getProjectiles(),
                                    environnement.getActeurManager().getEnnemis(),
                                    projectilesSprites,
                                    this.paneMap
                            );
                        }

                        // Xử lý các kẻ thù
                        List<Ennemi> ennemis = environnement.getActeurManager().getEnnemis();
                        if (ennemis != null) {
                            for (Iterator<Ennemi> it = ennemis.iterator(); it.hasNext(); ) {
                                Ennemi ennemi = it.next();
                                if (ennemi.estVivant()) {
                                    ennemi.seDeplacer(ennemi.getDirection());
                                    ennemi.attaquer(joueur);
                                } else {
                                    paneMap.getChildren().remove(ennemi.getSprite());
                                    it.remove();
                                }
                            }
                        }

                        // Kiểm tra nếu Joueur đã chết
                        if (!joueur.estVivant()) {
                            System.out.println("Game Over: Joueur đã chết.");
                            gameLoop.stop();
                            paneMap.getChildren().remove(joueurSprite);
                            if (gameOverVue != null) {
                                gameOverVue.updatePosition(joueur.getX(), joueur.getY());
                                gameOverVue.getImageView().setVisible(true);
                            }
                        }

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

        // Kích thước của Scene
        double sceneWidth = paneMap.getScene().getWidth();
        double sceneHeight = paneMap.getScene().getHeight();

        // Tính toán offset để giữ Joueur ở giữa màn hình
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
