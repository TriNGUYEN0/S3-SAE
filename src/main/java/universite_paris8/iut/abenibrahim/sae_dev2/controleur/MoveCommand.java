package universite_paris8.iut.abenibrahim.sae_dev2.controleur;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;

public class MoveCommand implements Command{
    private Joueur joueur;
    private Direction direction;

    public MoveCommand(Joueur j,Direction d){
        this.joueur = j;
        this.direction = d;
    }
public void print(){
    System.out.println("Le joueur se deplace en direction de :"+ direction);
}
    public void execute(){
        joueur.seDeplace(direction);
    }
    public void undo(){
        joueur.seDeplace(direction.inverse());
    }
}
