package universite_paris8.iut.abenibrahim.sae_dev2.vue;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.*;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.ArmeDistance;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Arme;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Epée;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Hache;
import java.util.ArrayList;
import java.util.List;

public class InventaireVue{

    private Pane paneMap;
    private TilePane tilePaneMap;
    @FXML
    public TilePane inventairePane;
    @FXML
    private HBox slot1;
    @FXML
    private Label titre;
    @FXML
    private TilePane premierPlanMap;
    @FXML
    private HBox slot2;

    @FXML
    private Label armeChoisie;
    @FXML
    private Label phrase;
    private Environnement environnement;
    private Arme selectedArme;

    private List<Arme> armes = new ArrayList<>();
    private List<ImageView> armeImages = new ArrayList<>();
    private List<HBox> slots;
    private ImageView selectedImageView;

    private ImageView gSprite;
    private ImageView ennemiSprite;


    private Epée epée;
    private Hache hache;
    private ArmeDistance armeDistance;

    private List<ArmeVue> armeVues = new ArrayList<>();


    public InventaireVue(Pane paneMap, TilePane tilePaneMap, Environnement environnement, TilePane inventaireP, HBox slot1, HBox slot2, Label titre, Label armeChoisie, Label phrase, List<HBox> slots, ImageView g, ImageView eSprite,TilePane premierPlanMap){
        this.paneMap = paneMap;
        this.tilePaneMap = tilePaneMap;
        this.environnement = environnement;
        this.inventairePane = inventaireP;
        this.slot1 = slot1;
        this.slot2 = slot2;
        this.titre = titre;
        this.armeChoisie = armeChoisie;
        this.phrase = phrase;
        this.slots = slots;
        this.gSprite = g;
        this.ennemiSprite = eSprite;
        this.epée = new Epée();
        this.hache = new Hache();
        this.armeDistance = new ArmeDistance();
        this.premierPlanMap = premierPlanMap;
    }

    public void ajouterArme(Arme arme){
        this.environnement.getArmeMap().add(arme);
    }

    public void afficherInventaire() {
        inventairePane.setVisible(true);
        inventairePane.setLayoutX(this.environnement.getGuts().getX());
        inventairePane.setLayoutY(this.environnement.getGuts().getY());

        slot1.setAlignment(Pos.CENTER); // Center items in slot1
        slot1.setPadding(new Insets(10)); // Optional: adjust padding as needed
        slot2.setAlignment(Pos.CENTER); // Center items in slot2
        slot2.setPadding(new Insets(10)); // Optional: adjust padding as needed

        slot1.setLayoutX(this.environnement.getGuts().getX() + 50);
        slot1.setLayoutY(this.environnement.getGuts().getY() + 75);
        slot2.setLayoutY(this.environnement.getGuts().getY() + 75);
        slot2.setLayoutX(this.environnement.getGuts().getX() + 150);
        titre.setLayoutX(this.environnement.getGuts().getX() + 125);
        titre.setLayoutY(this.environnement.getGuts().getY());
        phrase.setLayoutX(this.environnement.getGuts().getX() + 50);
        phrase.setLayoutY(this.environnement.getGuts().getY() + 150);
        this.armeChoisie.setLayoutX(this.environnement.getGuts().getX() + 200);
        this.armeChoisie.setLayoutY(this.environnement.getGuts().getY() + 150);

        clearSlots();
        int indexSlot = 0;

        for (InventaireObjets item : environnement.getGuts().getListeArme()) {
            ImageView imageView = item.getImage();
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);

            // Center the ImageView within the slot
            imageView.setPreserveRatio(true); // Keeps aspect ratio for better centering

            // Click event handler for selecting an item
            imageView.setOnMouseClicked(event -> handleArmeSelection(item.getArme(), imageView));

            if (indexSlot < slots.size()) {
                slots.get(indexSlot).getChildren().add(imageView);
                slots.get(indexSlot).setVisible(true);
                indexSlot++;
            }
        }

        // Visibility settings for other UI components
        paneMap.setVisible(true);
        gSprite.setVisible(false);
        tilePaneMap.setVisible(true);
        slot1.setVisible(true);
        slot2.setVisible(true);
        titre.setVisible(true);
        armeChoisie.setVisible(true);
        phrase.setVisible(true);
        ennemiSprite.setVisible(false);
        premierPlanMap.setVisible(false);
    }


    private void handleArmeSelection(Arme arme, ImageView imageView) {
        if (selectedImageView != null) {
            selectedImageView.setStyle("");
        }

        selectedArme = arme;
        this.environnement.getGuts().equiperArme(selectedArme);
        selectedImageView = imageView;
        imageView.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        System.out.println("Selected weapon nom " + arme.getNom());
        this.armeChoisie.textProperty().bind(selectedArme.nomProperty());
        this.armeChoisie.setLayoutX(this.environnement.getGuts().getX() + 200);
        this.armeChoisie.setLayoutY(this.environnement.getGuts().getY() + 150);

    }
    private void clearSlots() {
        for (HBox slot : slots) {
            slot.getChildren().clear();
            slot.setVisible(false);
        }
    }
    public void masquerInventaire() {
        inventairePane.setVisible(false);
        slot1.setVisible(false);
        slot2.setVisible(false);
        titre.setVisible(false);
        paneMap.setVisible(true);
        tilePaneMap.setVisible(true);
        gSprite.setVisible(true);
        armeChoisie.setVisible(false);
        phrase.setVisible(false);
        ennemiSprite.setVisible(true);
        premierPlanMap.setVisible(true);


        for (ImageView imageView : armeImages){
            imageView.setVisible(true);
        }
    }

    public void afficherArmes() {
        for (Arme arme : this.environnement.getArmeMap()) {
            ArmeVue armeVue = new ArmeVue(paneMap, arme);
            armeVues.add(armeVue);
        }
    }
    public void supprimerArmeDeLaCarte(Arme arme) {
        for (int i = armeVues.size() - 1; i >= 0; i--) {
            ArmeVue armeVue = armeVues.get(i);
            if (armeVue.getArme().equals(arme)) {
                armeVue.supprimerArmeDeLaCarte();
                armeVues.remove(i);  // Suppression sécurisée grâce à l'index
                break;
            }
        }
    }
}