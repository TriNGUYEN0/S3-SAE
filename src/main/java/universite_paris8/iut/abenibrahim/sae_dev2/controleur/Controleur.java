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
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.EnnemiProjectile;
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
    private List<EnnemiVue> ennemiVues;
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
        System.out.println("Controleur đã được khởi tạo.");

        // Kiểm tra và thiết lập `paneMap`
        if (paneMap == null) {
            System.err.println("Lỗi: paneMap chưa được tiêm (injected). Kiểm tra fx:id trong FXML.");
            return;
        } else {
            paneMap.setStyle("-fx-background-color: lightblue;"); // Đặt màu nền kiểm tra
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

        // Khởi tạo và hiển thị các Ennemis
        List<EnnemiVue> ennemiVues = new ArrayList<>();
        for (Ennemi ennemi : environnement.getActeurManager().getEnnemis()) {
            EnnemiVue ennemiVue = new EnnemiVue(ennemi, paneMap);
            ennemiVue.creerSpriteEnnemi();
            ennemiVues.add(ennemiVue);
        }
        this.ennemiVues = ennemiVues;

        // Khởi tạo danh sách EnnemieProjectilesVue
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
            System.out.println("Đã thêm EnnemiProjectile vào paneMap.");
        }


        // Khởi tạo ProjectileVue
        this.projectileVue = new ProjectileVue();
        this.projectileVueEnnemie = new ProjectileVueEnnemie();

        // Khởi tạo SoinVue
        this.soinVue = new SoinVue(this.paneMap, this.nbSoin, this.environnement);
        this.soinVue.afficherSoinsSurCarte();

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
                Duration.seconds(0.200), // Cập nhật mỗi 200ms
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

                        // Cập nhật các kẻ thù (Ennemi)
                        for (Iterator<EnnemiVue> it = ennemiVues.iterator(); it.hasNext(); ) {
                            EnnemiVue ennemiVue = it.next();
                            Ennemi ennemi = ennemiVue.getEnnemi();

                            if (ennemi.estVivant()) {
                                ennemi.seDeplacer(ennemi.getDirection());
                                ennemi.attaquer(joueur);
                                ennemiVue.animerEnnemi(ennemi.getDirection());
                            } else {
                                // Xóa sprite của Ennemi khỏi paneMap
                                paneMap.getChildren().remove(ennemiVue.getEnnemiSprite());
                                it.remove();
                                System.out.println("Ennemi đã bị loại bỏ khỏi paneMap.");
                            }
                        }

                        // Cập nhật các EnPro (EnnemiProjectile)
                        for (Iterator<EnnemieProjectilesVue> it = ennemiProjectilesVues.iterator(); it.hasNext(); ) {
                            EnnemieProjectilesVue ennemiProjectileVue = it.next();
                            EnnemiProjectile ennemiProjectile = environnement.getActeurManager().getEnnemiProjectiles().stream()
                                    .filter(e -> e.xProperty().get() == ennemiProjectileVue.getEnnemieSpriteView().getTranslateX() &&
                                            e.yProperty().get() == ennemiProjectileVue.getEnnemieSpriteView().getTranslateY())

                                    .findFirst()
                                    .orElse(null);

                            if (ennemiProjectile != null && ennemiProjectile.estVivant()) {
                                ennemiProjectile.seDeplacer(ennemiProjectile.getDirection());
                            } else {
                                // Xóa sprite của EnPro khỏi paneMap
                                paneMap.getChildren().remove(ennemiProjectileVue.getEnnemieSpriteView());
                                it.remove();
                                System.out.println("EnnemiProjectile đã bị loại bỏ khỏi paneMap.");
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
                        System.err.println("Lỗi xảy ra trong gameLoop: " + ex.getMessage());
                        ex.printStackTrace();
                        gameLoop.stop();
                    }
                }
        );

        gameLoop.getKeyFrames().add(kf);
        gameLoop.setCycleCount(Timeline.INDEFINITE); // Lặp vô hạn
        gameLoop.play(); // Bắt đầu vòng lặp
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
