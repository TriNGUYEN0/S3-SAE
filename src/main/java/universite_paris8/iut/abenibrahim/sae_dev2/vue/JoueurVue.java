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

        framesUp = loadFrames("universite_paris8/iut/abenibrahim/sae_dev2/Up");
        framesDown = loadFrames("universite_paris8/iut/abenibrahim/sae_dev2/Down");
        framesLeft = loadFrames("universite_paris8/iut/abenibrahim/sae_dev2/Left");
        framesRight = loadFrames("universite_paris8/iut/abenibrahim/sae_dev2/Right");

        // Khung hình mặc định
        if (framesRight.length > 0) {
            this.joueurSprite = new ImageView(framesRight[0]);
        } else {
            this.joueurSprite = new ImageView();
        }

        this.joueurSprite.setFitWidth(50);
        this.joueurSprite.setFitHeight(50);

        paneMap.getChildren().add(joueurSprite);

        bindSpriteToJoueur();
    }


    private void bindSpriteToJoueur() {
        if (joueur.xProperty() != null && joueur.yProperty() != null) {
            joueurSprite.translateXProperty().bind(joueur.xProperty());
            joueurSprite.translateYProperty().bind(joueur.yProperty());
        } else {
            System.err.println("Error: joueur a mauvaise position.");
        }
    }


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

        controleurTouche.setControleur(controleur);

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
            case "NORD" -> framesUp;
            case "SUD" -> framesDown;
            case "OUEST" -> framesLeft;
            case "EST" -> framesRight;
            default -> null;
        };

        if (frames == null || frames.length == 0) {
            System.err.println("Error ne trouve pas frame pour direction " + direction);
            return;
        }

        currentFrameIndex = (currentFrameIndex + 1) % frames.length;
        joueurSprite.setImage(frames[currentFrameIndex]); // Cập nhật khung hình

    }



    public ImageView getJoueurSprite() {
        return joueurSprite;
    }


    public void removeSprite() {
        paneMap.getChildren().remove(joueurSprite);
    }


    private Image[] loadFrames(String folderPath) {
        try {
            Image[] images = new Image[3];
            for (int i = 0; i < 3; i++) {
                String imagePath = "/" + folderPath + "/" + (i + 1) + ".png";
                URL imageURL = getClass().getResource(imagePath);
                if (imageURL == null) {
                    System.err.println("Errorrrr ne trouve pas image  " + imagePath);
                } else {
                    images[i] = new Image(imageURL.toExternalForm());
                }
            }
            return images;
        } catch (Exception e) {
            e.printStackTrace();
            return new Image[0];
        }
    }

}
