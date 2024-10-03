package universite_paris8.iut.abenibrahim.sae_dev2.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.objetDefense;

import java.util.ArrayList;
import java.util.List;

public class objetDefVue {
    private List<objetDefense> objetDefenses;
    private List<ImageView> defenseImage;
    private Pane paneMap;
    private Environnement environnement;
    private objetDefense objetDefense;
    public objetDefVue(Environnement environnement, Pane paneMap) {
        this.environnement = environnement;
        this.paneMap = paneMap;
        this.objetDefenses = new ArrayList<>();
        this.defenseImage = new ArrayList<>();
        this.objetDefense = new objetDefense();

    }
    public void ajouterObjetDefenseDansMap() {
        for(objetDefense objetDefense : environnement.getObjetDefenseList()) {
            Image image = objetDefense.getImage();
            ImageView imageView = new ImageView(image);
            paneMap.getChildren().add(imageView);
            imageView.setTranslateX(objetDefense.getX()); // Ajuster la position X
            imageView.setTranslateY(objetDefense.getY());
            objetDefenses.add(objetDefense);
            defenseImage.add(imageView);
        }
    }
    public void supprimerObjetDefDeLaCarte(objetDefense objetDefense) {
        int index = objetDefenses.indexOf(objetDefense);
        if (index >= 0) {
            ImageView imageView = defenseImage.get(index);
            objetDefenses.remove(objetDefense);
            defenseImage.remove(imageView);
            paneMap.getChildren().remove(imageView);
        }
    }

    public void ajouterObjetDefenseEnv(objetDefense objetDefense) {
        this.environnement.getObjetDefenseList().add(objetDefense);
    }
    public void afficherSoinMap(){
        ajouterObjetDefenseEnv(objetDefense);
    }

}
