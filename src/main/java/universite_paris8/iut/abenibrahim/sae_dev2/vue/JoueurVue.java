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


        framesUp = loadFrames("universite_paris8/iut/abenibrahim/sae_dev2/Up");
        framesDown = loadFrames("universite_paris8/iut/abenibrahim/sae_dev2/Down");
        framesLeft = loadFrames("universite_paris8/iut/abenibrahim/sae_dev2/Left");
        framesRight = loadFrames("universite_paris8/iut/abenibrahim/sae_dev2/Right");


        this.joueurSprite = new ImageView(framesRight[0]); // Khung hình mặc định
        this.joueurSprite.setFitWidth(50);
        this.joueurSprite.setFitHeight(50);


        paneMap.getChildren().add(joueurSprite);
        bindSpriteToJoueur();
    }


    private void bindSpriteToJoueur() {
        joueurSprite.translateXProperty().bind(joueur.xProperty());
        joueurSprite.translateYProperty().bind(joueur.yProperty());
    }


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



    public ImageView getJoueurSprite() {
        return joueurSprite;
    }


    public void removeSprite() {
        paneMap.getChildren().remove(joueurSprite);
    }


    private Image[] loadFrames(String folderPath) {
        try {
            return new Image[]{
                    new Image(getClass().getResource("/" + folderPath + "/1.png").toExternalForm()),
                    new Image(getClass().getResource("/" + folderPath + "/2.png").toExternalForm()),
                    new Image(getClass().getResource("/" + folderPath + "/3.png").toExternalForm())
            };
        } catch (NullPointerException e) {
            System.err.println("Lỗi: Không tìm thấy tệp hình ảnh tại " + folderPath);
            return new Image[0];
        }
    }

}
