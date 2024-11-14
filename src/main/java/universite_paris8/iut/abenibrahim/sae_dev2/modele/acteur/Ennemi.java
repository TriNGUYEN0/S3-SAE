package universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Epee;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Arme;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.strategie.combat.CombatEnnemi;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.strategie.deplacement.DeplacementEnnemi;

public class Ennemi extends Acteur {
    private Arme epee;
    private Direction direction;

    public Ennemi(Environnement e, int x, int y, int v, int pv) {
        super(e, x, y, v, pv);
        this.epee = new Epee();
        this.direction = Direction.EST;


        this.setCombatStrategy(new CombatEnnemi(this));


        this.setDeplacementStrategy(new DeplacementEnnemi());
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
}
