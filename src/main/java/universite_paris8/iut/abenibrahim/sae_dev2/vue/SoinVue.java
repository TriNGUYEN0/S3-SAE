package universite_paris8.iut.abenibrahim.sae_dev2.vue;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Constante;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Soin;


import java.util.ArrayList;
import java.util.List;

public class SoinVue {
    private final ImageView imageView;
    private Pane paneMap;
    private StackPane soinStackPane;
    private StackPane nbsoinStackPane;
    private ImageView pvImageView;
    private Environnement environnement;
    private List<Soin> soins = new ArrayList<>();
    private List<ImageView> soinImageViews = new ArrayList<>();
    @FXML
    private Label nbSoin;
    private Soin soin;

    public SoinVue(Pane paneMap, Label nbSoin,Environnement environnement) {
        this.paneMap = paneMap;
        this.nbSoin = nbSoin;
        this.environnement = environnement;
        this.soinStackPane = new StackPane();
        this.nbsoinStackPane = new StackPane();
        Image soinImage = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/items.png").toString());
        pvImageView = new ImageView(soinImage);
        pvImageView.setFitWidth(52);
        pvImageView.setFitHeight(50);
        soinStackPane.getChildren().add(pvImageView);
        nbsoinStackPane.getChildren().add(nbSoin);
        paneMap.getChildren().add(soinStackPane);
        paneMap.getChildren().add(nbsoinStackPane);
        this.imageView = new ImageView(ImageObjet.IMAGE_SOIN);
        paneMap.getChildren().add(imageView);
        updatePosition();
        imageView.setVisible(true);
        this.soin = new Soin.Builder().build();
        afficherSoin();
    }
    public void afficherSoin(){
        nbSoin.textProperty().bind(this.environnement.getActeurManager().getJoueur().nbSoinProperty().asString());
    }
    public Node getsoinStackPane() {
        return this.soinStackPane;
    }
    public Node getnbsoinStackPane() {
        return this.nbsoinStackPane;
    }
    public void updatePosition() {
        // Mettez à jour la position de l'image ici en fonction de la position de l'arme
        imageView.setTranslateX(Constante.POSITION_X_SOIN); // Ajustez cela si vous ajoutez une méthode getX() dans Arme
        imageView.setTranslateY(Constante.POSITION_Y_SOIN); // Ajustez cela si vous ajoutez une méthode getY() dans Arme
    }
    public Soin getSoin() {
        return this.soin;
    }

    public ImageView getImageView() {
        return imageView;
    }
    public void afficherSoinsSurCarte() {
        for (Soin soin : environnement.getObjetManager().getSoinMap()) {
            ImageView imageView = new ImageView(ImageObjet.IMAGE_SOIN);
            imageView.setTranslateX(soin.getX());
            imageView.setTranslateY(soin.getY());
            paneMap.getChildren().add(imageView);
            soins.add(soin);
            soinImageViews.add(imageView);
        }
    }


    public void supprimerSoinDeLaCarte(Soin soin) {
        for (int i = soins.size() - 1; i >= 0; i--) {
            if (soins.get(i).equals(soin)) {
                paneMap.getChildren().remove(soinImageViews.get(i));
                soins.remove(i);
                soinImageViews.remove(i);
                break;
            }
        }
    }

}