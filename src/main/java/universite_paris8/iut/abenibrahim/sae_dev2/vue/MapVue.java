package universite_paris8.iut.abenibrahim.sae_dev2.vue;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Environnement;

public class MapVue {

    private int[][] tab;
    private int joueurX;
    private int joueurY;
    private int[][] tab2;
    private int[][] tab3;
    @FXML
    private TilePane tilePaneMap;
    @FXML
    private TilePane premierPlanMap;
    @FXML
    private TilePane tilePaneMap2;

    @FXML
    private TilePane murMap;

    public MapVue(int[][] tab, int[][] tab2,int[][] tab3, TilePane tilePaneMap, TilePane premierPlanMap,TilePane tilePaneMap2){
        this.tab = tab;
        this.tab3= tab3;
        this.tab2 = tab2;
        this.tilePaneMap = tilePaneMap;
        this.tilePaneMap2 = tilePaneMap2;
        this.premierPlanMap = premierPlanMap;
    }

    Image herbeImage = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/Sol.png").toString());
    Image sol2 = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/solv2-removebg-preview.png").toString());
    Image Mur = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/mu_r.png").toString());
    Image Mur2 = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/mur2.png").toString());
    Image arbre = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/arbre1.png").toString());
    Image arbre2 = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/arbre2.png").toString());
    Image murD = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/murDroite.png").toString());
    Image murG = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/mur_gauche.png").toString());
    Image lum = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/lum-removebg-preview.png").toString());
    Image porte = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/porte.png").toString());
    Image statut = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/décor1.png").toString());
    Image statut2 = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/décor2.png").toString());
    // Image décor3 = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/porte.png").toString());

    public void remplirMap() {
        for (int i = 0; i < this.tab.length; i++) {
            for (int j = 0; j < this.tab[i].length; j++) {
                ImageView imageView = new ImageView();
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
                switch (this.tab[i][j]) {
                    case 0 -> imageView.setImage(sol2);
                    case 1 -> imageView.setImage(herbeImage);
                }
                this.tilePaneMap.getChildren().add(imageView);
            }
        }
        for (int i = 0; i < this.tab2.length; i++) {
            for (int j = 0; j < this.tab2[i].length; j++) {
                ImageView imageView = new ImageView();
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
                switch (this.tab2[i][j]) {
                    //   case 0 -> imageView.setImage(murImage);
                    case 1 -> imageView.setImage(Mur);
                    case 2 -> imageView.setImage(Mur2);
                    case 3 -> imageView.setImage(arbre);
                    case 4 -> imageView.setImage(sol2);
                    case 5 -> imageView.setImage(lum);
                    case 6 -> imageView.setImage(arbre2);
                    case 7 -> imageView.setImage(murD);
                    case 8 -> imageView.setImage(murG);
                    case 9 -> imageView.setImage(porte);
                    case 10 -> imageView.setImage(statut);
                    case 11 -> imageView.setImage(statut2);
                }
                this.premierPlanMap.getChildren().add(imageView);
            }
        }
    }

    public void remplirMap2(){
        Image herbeImage = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/Sol.png").toString());
        for (int i = 0; i < this.tab3.length; i++) {
            for (int j = 0; j < this.tab3[i].length; j++) {
                ImageView imageView = new ImageView();
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
                switch (this.tab3[i][j]){
                    case 0 -> imageView.setImage(herbeImage);
                    case 1 -> imageView.setImage(Mur);
                    case 2 -> imageView.setImage(Mur2);
                    case 3 -> imageView.setImage(arbre);
                    case 4 -> imageView.setImage(sol2);
                    case 7 -> imageView.setImage(murD);
                    case 8 -> imageView.setImage(murG);
                    case 9 -> imageView.setImage(porte);

                }
                this.tilePaneMap2.getChildren().add(imageView);
            }
        }
    }
    public void updatePlayerPosition(int x, int y) {
        this.joueurX = x;
        this.joueurY = y;
        checkPlayerPosition();
    }

    private void checkPlayerPosition() {
        if (joueurX == 1440 && joueurY == 0){
            tilePaneMap.setVisible(false);
            premierPlanMap.setVisible(false);
            tilePaneMap2.setVisible(true);
            remplirMap2();
        }
    }

}
