package universite_paris8.iut.abenibrahim.sae_dev2.vue;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import universite_paris8.iut.abenibrahim.sae_dev2.controleur.Controleur;
import universite_paris8.iut.abenibrahim.sae_dev2.controleur.ControleurTouche;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;

public class JoueurVue {
    private final Joueur joueur;
    private final ImageView joueurSprite;
    private final Pane paneMap;

    private final Image[] framesUp;
    private final Image[] framesDown;
    private final Image[] framesLeft;
    private final Image[] framesRight;

    private int currentFrameIndex = 0;

    public JoueurVue(Joueur joueur, Pane paneMap) {
        this.joueur = joueur;
        this.paneMap = paneMap;

        // Khởi tạo các khung hình cho từng hướng
        framesUp = loadFrames("/universite_paris8/iut/abenibrahim/sae_dev2/up");
        framesDown = loadFrames("/universite_paris8/iut/abenibrahim/sae_dev2/down");
        framesLeft = loadFrames("/universite_paris8/iut/abenibrahim/sae_dev2/left");
        framesRight = loadFrames("/universite_paris8/iut/abenibrahim/sae_dev2/right");

        // Tạo và cấu hình sprite
        this.joueurSprite = new ImageView(framesRight[0]); // Khung hình mặc định
        this.joueurSprite.setFitWidth(50);
        this.joueurSprite.setFitHeight(50);

        // Thêm sprite vào pane và ràng buộc vị trí
        paneMap.getChildren().add(joueurSprite);
        bindSpriteToJoueur();
    }

    /**
     * Ràng buộc vị trí của sprite với vị trí của `Joueur`
     */
    private void bindSpriteToJoueur() {
        joueurSprite.translateXProperty().bind(joueur.xProperty());
        joueurSprite.translateYProperty().bind(joueur.yProperty());
    }

    /**
     * Thêm điều khiển và tương tác thông qua `ControleurTouche`
     */
    public void creerSpriteJoueur(Controleur controleur) {
        ControleurTouche controleurTouche = new ControleurTouche(
                joueur,
                controleur.getEnvironnement(),
                joueurSprite,
                this
        );

        Scene scene = paneMap.getScene();
        if (scene != null) {
            scene.addEventHandler(KeyEvent.KEY_PRESSED, controleurTouche);
        } else {
            paneMap.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    newScene.addEventHandler(KeyEvent.KEY_PRESSED, controleurTouche);
                }
            });
        }
    }

    /**
     * Cập nhật khung hình của sprite dựa trên hướng
     */
    public void updateFrame(String direction) {
        Image[] frames = switch (direction) {
            case "UP" -> framesUp;
            case "DOWN" -> framesDown;
            case "LEFT" -> framesLeft;
            case "RIGHT" -> framesRight;
            default -> null;
        };

        if (frames != null) {
            currentFrameIndex = (currentFrameIndex + 1) % frames.length;
            joueurSprite.setImage(frames[currentFrameIndex]);
        }
    }


    /**
     * Trả về sprite của Joueur
     */
    public ImageView getJoueurSprite() {
        return joueurSprite;
    }

    /**
     * Xóa sprite khỏi Pane khi cần thiết
     */
    public void removeSprite() {
        paneMap.getChildren().remove(joueurSprite);
    }

    /**
     * Tải các khung hình từ thư mục
     */
    private Image[] loadFrames(String basePath) {
        return new Image[]{
                new Image(getClass().getResource(basePath + "1.png").toExternalForm()),
                new Image(getClass().getResource(basePath + "2.png").toExternalForm()),
                new Image(getClass().getResource(basePath + "3.png").toExternalForm())
        };
    }
}
