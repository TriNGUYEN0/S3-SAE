package universite_paris8.iut.abenibrahim.sae_dev2.vue;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.InventaireObjets;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Arme;


import java.util.ArrayList;
import java.util.List;

public class InventaireVue {
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

    private List<HBox> slots;
    private ImageView selectedImageView;

    private List<ArmeVue> armeVues = new ArrayList<>();

    public InventaireVue(Pane paneMap, TilePane tilePaneMap, Environnement environnement, TilePane inventaireP, HBox slot1, HBox slot2, Label titre, Label armeChoisie, Label phrase, List<HBox> slots, TilePane premierPlanMap) {
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
        this.premierPlanMap = premierPlanMap;
    }

    public void afficherInventaire() {
        inventairePane.setVisible(true);
        clearSlots();
        int indexSlot = 0;

        for (Arme arme : environnement.getActeurManager().getJoueur().getListeArmes()) {
            ArmeVue armeVue = new ArmeVue(paneMap, arme);
            ImageView imageView = armeVue.getImageView();
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            imageView.setPreserveRatio(true);


            imageView.setOnMouseClicked(event -> handleArmeSelection(arme, imageView));

            if (indexSlot < slots.size()) {
                slots.get(indexSlot).getChildren().add(imageView);
                slots.get(indexSlot).setVisible(true);
                indexSlot++;
            }
        }


        slot1.setVisible(true);
        slot2.setVisible(true);
        titre.setVisible(true);
        armeChoisie.setVisible(true);
        phrase.setVisible(true);
    }


    private void handleArmeSelection(Arme arme, ImageView imageView) {
        if (selectedImageView != null) {
            selectedImageView.setStyle("");
        }

        selectedArme = arme;
        this.environnement.getActeurManager().getJoueur().equiperArme(selectedArme);
        selectedImageView = imageView;
        imageView.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        armeChoisie.setText(selectedArme.getNom());
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
        armeChoisie.setVisible(false);
        phrase.setVisible(false);
    }

    public void afficherArmesSurCarte() {
        for (Arme arme : environnement.getObjetManager().getArmeMap()) {
            ArmeVue armeVue = new ArmeVue(paneMap, arme);
            if (!armeVues.contains(armeVue)) {
                armeVues.add(armeVue);
            }
        }
    }


    public void supprimerArmeDeLaCarte(Arme arme) {
        // Xóa trong armeVues
        for (int i = armeVues.size() - 1; i >= 0; i--) {
            ArmeVue armeVue = armeVues.get(i);
            if (armeVue.getArme().equals(arme)) {
                armeVue.supprimerArmeDeLaCarte(); // Xóa hình ảnh
                armeVues.remove(i); // Xóa khỏi danh sách
                System.out.println("Vũ khí đã được loại bỏ khỏi giao diện: " + arme.getNom());
            }
        }

        // Kiểm tra và xóa hình ảnh dư thừa trong paneMap
        paneMap.getChildren().removeIf(node -> {
            if (node instanceof ImageView) {
                ImageView imageView = (ImageView) node;
                double x = imageView.getTranslateX();
                double y = imageView.getTranslateY();
                return (x == arme.getX() && y == arme.getY()); // Nếu tọa độ trùng khớp
            }
            return false;
        });

        System.out.println("Hoàn thành kiểm tra và xóa hình ảnh của vũ khí trên giao diện.");
    }

}
