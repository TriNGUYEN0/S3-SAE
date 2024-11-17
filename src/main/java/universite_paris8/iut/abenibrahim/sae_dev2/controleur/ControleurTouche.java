package universite_paris8.iut.abenibrahim.sae_dev2.controleur;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;
import universite_paris8.iut.abenibrahim.sae_dev2.vue.JoueurVue;

public class ControleurTouche implements EventHandler<KeyEvent> {
    private final Joueur joueur;
    private final Environnement environnement;
    private final JoueurVue joueurVue;

    public ControleurTouche(Joueur joueur, Environnement environnement, ImageView joueurSprite, JoueurVue joueurVue) {
        this.joueur = joueur;
        this.environnement = environnement;
        this.joueurVue = joueurVue;
    }

    @Override
    public void handle(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        Direction direction = null;

        switch (keyCode) {
            case UP -> direction = Direction.NORD;
            case DOWN -> direction = Direction.SUD;
            case LEFT -> direction = Direction.OUEST;
            case RIGHT -> direction = Direction.EST;
        }

        if (direction != null) {
            joueur.setLastDirection(direction); // Lưu hướng di chuyển cuối cùng
            joueur.seDeplacer(direction); // Di chuyển Joueur
            joueurVue.updateFrame(direction.name()); // Cập nhật khung hình
        }
    }

}
