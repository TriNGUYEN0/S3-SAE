package universite_paris8.iut.abenibrahim.sae_dev2.vue;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.ObjetDefense;
import universite_paris8.iut.abenibrahim.sae_dev2.vue.ImageObjet;

import java.util.ArrayList;
import java.util.List;

public class ObjetDefVue {
    private Pane paneMap;
    private Environnement environnement;
    private List<ObjetDefense> objetDefenses;
    private List<ImageView> objetDefImageViews;

    public ObjetDefVue(Environnement environnement, Pane paneMap) {
        this.environnement = environnement;
        this.paneMap = paneMap;
        this.objetDefenses = new ArrayList<>();
        this.objetDefImageViews = new ArrayList<>();
    }

    public void afficherObjetDefSurCarte() {
        for (ObjetDefense objetDefense : environnement.getObjetManager().getObjetDefenseList()) {
            ImageView imageView = new ImageView(ImageObjet.IMAGE_OBJET_DEF);
            imageView.setTranslateX(objetDefense.getX());
            imageView.setTranslateY(objetDefense.getY());
            paneMap.getChildren().add(imageView);
            objetDefenses.add(objetDefense);
            objetDefImageViews.add(imageView);
        }
    }

    public void supprimerObjetDefDeLaCarte(ObjetDefense objetDefense) {
        for (int i = objetDefenses.size() - 1; i >= 0; i--) {
            if (objetDefenses.get(i).equals(objetDefense)) {
                paneMap.getChildren().remove(objetDefImageViews.get(i));
                objetDefenses.remove(i);
                objetDefImageViews.remove(i);
                break;
            }
        }
    }
}
