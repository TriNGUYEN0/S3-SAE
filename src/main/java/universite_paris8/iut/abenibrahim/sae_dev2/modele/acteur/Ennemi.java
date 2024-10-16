package universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.serviceAction.CombatEnnemi;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Epée;
import universite_paris8.iut.abenibrahim.sae_dev2.objet.Arme;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.serviceAction.DeplacementEnnemi;

public class Ennemi extends Acteur {

    private Arme epée;
    private Direction direction;
    private DeplacementEnnemi deplacementEnnemi; // Dịch vụ di chuyển
    private CombatEnnemi combatEnnemi; // Dịch vụ chiến đấu

    public Ennemi(Environnement e, int x, int y, int v, int pv) {
        super(e, x, y, v, pv);
        this.epée = new Epée();
        this.direction = Direction.EST;
        this.deplacementEnnemi = new DeplacementEnnemi(); // Khởi tạo dịch vụ di chuyển
        this.combatEnnemi = new CombatEnnemi(this); // Khởi tạo dịch vụ chiến đấu
    }

    // Phương thức điều khiển di chuyển về phía người chơi
    public void seDeplaceVersJoueur() {
        deplacementEnnemi.seDeplacer(this, this.direction); // Chỉ di chuyển khi phát hiện người chơi
    }

    // Gọi dịch vụ chiến đấu để tấn công
    public void attaquer(Acteur cible) {
        combatEnnemi.attaquer(cible);
    }

    // Gọi dịch vụ chiến đấu để nhận sát thương
    public void recoisDegat(int degat) {
        combatEnnemi.recoisDegat(degat);
    }

    // Getter và Setter cho các thuộc tính
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public Arme getEpée() {
        return this.epée;
    }
}
