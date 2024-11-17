package universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Epee;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Arme;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.strategie.combat.CombatEnnemi;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.strategie.deplacement.DeplacementEnnemi;

public class Ennemi extends Acteur {
    private Arme epee;
    private Direction direction;
    private ImageView sprite;

    public Ennemi(Environnement e, int x, int y, int v, int pv) {
        super(e, x, y, v, pv);
        this.epee = new Epee(x, y);
        this.direction = Direction.EST;

        this.setCombatStrategy(new CombatEnnemi(this));
        this.setDeplacementStrategy(new DeplacementEnnemi());

        // Khởi tạo ImageView cho sprite
        Image image = new Image(getClass().getResource("/universite_paris8/iut/abenibrahim/sae_dev2/ennemi-droite1-removebg-preview.png").toExternalForm());
        this.sprite = new ImageView(image);
        this.sprite.setFitWidth(50);
        this.sprite.setFitHeight(50);
        this.sprite.setX(x);
        this.sprite.setY(y);
    }

    public Arme getEpee() {
        return epee;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setSprite(ImageView sprite) {
        this.sprite = sprite;
    }

    public ImageView getSprite() {
        return sprite;
    }

    public void updateSpritePosition() {
        this.sprite.setX(this.getX());
        this.sprite.setY(this.getY());
    }
}
