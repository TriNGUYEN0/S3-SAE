package universite_paris8.iut.abenibrahim.sae_dev2.vue;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.objet.Soin;


import java.util.ArrayList;
import java.util.List;

public class SoinVue {
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

    public SoinVue(Pane paneMap, Label nbSoin, Environnement environnement) {
        this.paneMap = paneMap;
        this.environnement = environnement;
        this.nbSoin = nbSoin;
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
        this.soin = new Soin();
        afficherSoin();
    }
    public void afficherSoin(){
       nbSoin.textProperty().bind(this.environnement.getGuts().nbSoinProperty().asString());
    }
    public Node getsoinStackPane() {
        return this.soinStackPane;
    }
    public Node getnbsoinStackPane() {
        return this.nbsoinStackPane;
    }
    public void ajouterSoinMap() {
        for (Soin soin : this.environnement.getSoinMap()) {
            Image image = soin.getImage(); // Obtenir l'image de l'arme
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);// Créer l'ImageView avec l'image
            paneMap.getChildren().add(imageView); // Ajouter l'ImageView au PaneMap
            imageView.setTranslateX(soin.getX()); // Ajuster la position X
            imageView.setTranslateY(soin.getY()); // Ajuster la position Y
            soins.add(soin);
            soinImageViews.add(imageView);
        }
    }
    public void supprimerSoinDeLaCarte(Soin soin) {
        int index = soins.indexOf(soin);
        if (index >= 0) {
            ImageView imageView = soinImageViews.get(index);
            soins.remove(soin);
            soinImageViews.remove(imageView);
            paneMap.getChildren().remove(imageView);
        }
    }
    public void ajouterSoin(Soin soin){
        this.environnement.getSoinMap().add(soin);
    }
    public void afficherSoinMap() {
        ajouterSoin(soin);
    }
}
