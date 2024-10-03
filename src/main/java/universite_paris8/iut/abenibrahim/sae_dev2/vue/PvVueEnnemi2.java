package universite_paris8.iut.abenibrahim.sae_dev2.vue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class PvVueEnnemi2 {
    private Pane paneMap;

    private StackPane pvStackPane;
    private Image vie5Image;
    private Image vie4Image;
    private Image vie3Image;
    private Image vie2Image;
    private Image vie1Image;
    private Image vie0Image;
    private ImageView pvImageView;
    public PvVueEnnemi2(Pane paneMap) {
        this.paneMap = paneMap;
        pvStackPane = new StackPane();
        pvImageView = new ImageView();
        pvStackPane.getChildren().add(pvImageView);
        pvStackPane.setLayoutX(10);
        pvStackPane.setLayoutY(10);
        paneMap.getChildren().add(pvStackPane);
        vie5Image = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/barre6.png").toString());
        vie4Image = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/barre5.png").toString());
        vie3Image = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/barre4.png").toString());
        vie2Image = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/barre3.png").toString());
        vie1Image = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/barre2.png").toString());
        vie0Image = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/barre1.png").toString());
    }

    public void updatePvEnnemieImage(int pv) {
        pvImageView.setFitWidth(50);
        pvImageView.setFitHeight(25);
        if (pv > 110) {
            pvImageView.setImage(vie0Image);
        } else if (pv > 80) {
            pvImageView.setImage(vie1Image);
        } else if (pv > 60) {
            pvImageView.setImage(vie2Image);
        } else if (pv > 30) {
            pvImageView.setImage(vie3Image);
        } else if (pv > 15) {
            pvImageView.setImage(vie4Image);
        } else {
            pvImageView.setImage(vie5Image);
            paneMap.getChildren().remove(pvStackPane);
        }
    }

    public Node getPvStackPane() {
        return this.pvStackPane;
    }
}
