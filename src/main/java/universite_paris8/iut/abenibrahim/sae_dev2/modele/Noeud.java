package universite_paris8.iut.abenibrahim.sae_dev2.modele;

public class Noeud {
    public int x;
    public int y;
    public Noeud parent;

    Noeud(int x, int y, Noeud parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }
}
