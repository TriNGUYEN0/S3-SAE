package universite_paris8.iut.abenibrahim.sae_dev2.vue;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Constante;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.ObjetDefense;
import java.util.ArrayList;
import java.util.List;


public class ObjetDefVue {
    private Pane paneMap;
    private final ObjetDefense objetDefense;
    private final ImageView imageView;
    private List<ObjetDefense> objetDefenses = new ArrayList<>();
    private List<ImageView> objetDefImageViews = new ArrayList<>();


    public ObjetDefVue(Pane paneMap) {
        this.paneMap = paneMap;
        this.objetDefense = new ObjetDefense.Builder().build();
        this.imageView = new ImageView(ImageObjet.IMAGE_OBJET_DEF); // Charger l'image depuis ImageObjet
        paneMap.getChildren().add(imageView); // Ajouter l'ImageView au Pane
        updatePosition();
        imageView.setVisible(true);
        this.paneMap = paneMap;
    }

    public void updatePosition() {
        // Mettez à jour la position de l'image ici en fonction de la position de l'arme
        imageView.setTranslateX(Constante.POSITION_X_OBJETDEF); // Ajustez cela si vous ajoutez une méthode getX() dans Arme
        imageView.setTranslateY(Constante.POSITION_Y_OBJETDEF); // Ajustez cela si vous ajoutez une méthode getY() dans Arme
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




    public ObjetDefense getObjetDef() {
        return this.objetDefense;
    }

    public ImageView getImageView() {
        return imageView;
    }

}