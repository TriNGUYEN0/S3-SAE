package universite_paris8.iut.abenibrahim.sae_dev2.modele.objet;

public abstract class ArmeDecorator extends Arme {
    protected Arme arme;

    public ArmeDecorator(Arme arme) {
        super(arme.getPointAttaque(), arme.getNom(), arme.getX(), arme.getY());
        this.arme = arme;
    }

    @Override
    public int getPointAttaque() {
        return arme.getPointAttaque();
    }

    @Override
    public String getNom() {
        return arme.getNom();
    }
}
