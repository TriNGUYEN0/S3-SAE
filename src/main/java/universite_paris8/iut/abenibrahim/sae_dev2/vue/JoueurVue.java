package universite_paris8.iut.abenibrahim.sae_dev2.vue;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import universite_paris8.iut.abenibrahim.sae_dev2.controleur.Controleur;
import universite_paris8.iut.abenibrahim.sae_dev2.controleur.ControleurTouche;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;

import java.net.URL;

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

        // Nạp các khung hình
        framesUp = loadFrames("universite_paris8/iut/abenibrahim/sae_dev2/Up");
        framesDown = loadFrames("universite_paris8/iut/abenibrahim/sae_dev2/Down");
        framesLeft = loadFrames("universite_paris8/iut/abenibrahim/sae_dev2/Left");
        framesRight = loadFrames("universite_paris8/iut/abenibrahim/sae_dev2/Right");

        // Khung hình mặc định
        if (framesRight.length > 0) {
            this.joueurSprite = new ImageView(framesRight[0]);
        } else {
            System.err.println("Lỗi: Không có hình ảnh khung mặc định. Sử dụng hình ảnh mặc định.");
            this.joueurSprite = new ImageView(); // Tạo ImageView rỗng để tránh lỗi
        }

        // Thiết lập kích thước
        this.joueurSprite.setFitWidth(50);
        this.joueurSprite.setFitHeight(50);

        // Thêm sprite vào Pane
        paneMap.getChildren().add(joueurSprite);
        System.out.println("Đã thêm Joueur vào paneMap.");

        // Ràng buộc vị trí sprite với vị trí của Joueur
        bindSpriteToJoueur();
    }

    /**
     * Ràng buộc vị trí của ImageView với tọa độ của Joueur
     */
    private void bindSpriteToJoueur() {
        if (joueur.xProperty() != null && joueur.yProperty() != null) {
            joueurSprite.translateXProperty().bind(joueur.xProperty());
            joueurSprite.translateYProperty().bind(joueur.yProperty());
            System.out.println("Đã ràng buộc vị trí của JoueurSprite với Joueur.");
        } else {
            System.err.println("Lỗi: Joueur không có tọa độ hợp lệ.");
        }
    }

    /**
     * Tạo và xử lý sự kiện bàn phím cho Joueur
     */
    public void creerSpriteJoueur(Controleur controleur) {
        ControleurTouche controleurTouche = new ControleurTouche(
                joueur, // Joueur
                controleur.getEnvironnement(), // Environnement
                this, // JoueurVue
                controleur.getInventaireVue(), // InventaireVue
                controleur.getSoinVue(), // SoinVue
                controleur.getMapVue(), // MapVue
                controleur.getObjetDefVue(), // ObjetDefVue
                controleur.getDialogueVue() // DialogueVue
        );

        // Gán đối tượng Controleur cho ControleurTouche
        controleurTouche.setControleur(controleur);

        Scene scene = paneMap.getScene();
        if (scene != null) {
            scene.addEventHandler(KeyEvent.KEY_PRESSED, controleurTouche);
            System.out.println("Đã gắn sự kiện bàn phím vào Scene.");
        } else {
            paneMap.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    newScene.addEventHandler(KeyEvent.KEY_PRESSED, controleurTouche);
                    System.out.println("Đã gắn sự kiện bàn phím vào Scene mới.");
                }
            });
        }
    }





    /**
     * Cập nhật khung hình của sprite dựa trên hướng
     */
    public void updateFrame(String direction) {
        Image[] frames = switch (direction) {
            case "NORD" -> framesUp;
            case "SUD" -> framesDown;
            case "OUEST" -> framesLeft;
            case "EST" -> framesRight;
            default -> null;
        };

        if (frames == null || frames.length == 0) {
            System.err.println("Lỗi: Không tìm thấy khung hình cho hướng: " + direction);
            return;
        }

        currentFrameIndex = (currentFrameIndex + 1) % frames.length;
        joueurSprite.setImage(frames[currentFrameIndex]); // Cập nhật khung hình

    }



    /**
     * Trả về ImageView của Joueur
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
    private Image[] loadFrames(String folderPath) {
        try {
            System.out.println("Đang tải hình ảnh từ thư mục: " + folderPath);
            Image[] images = new Image[3];
            for (int i = 0; i < 3; i++) {
                String imagePath = "/" + folderPath + "/" + (i + 1) + ".png";
                URL imageURL = getClass().getResource(imagePath);
                if (imageURL == null) {
                    System.err.println("Lỗi: Không tìm thấy tệp hình ảnh tại " + imagePath);
                } else {
                    images[i] = new Image(imageURL.toExternalForm());
                    System.out.println("Đã tải hình ảnh: " + imagePath);
                }
            }
            return images;
        } catch (Exception e) {
            System.err.println("Lỗi khi tải hình ảnh từ thư mục: " + folderPath);
            e.printStackTrace();
            return new Image[0];
        }
    }

}
