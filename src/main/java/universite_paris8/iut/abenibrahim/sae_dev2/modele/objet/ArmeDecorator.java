package universite_paris8.iut.abenibrahim.sae_dev2.modele.objet;

public class ArmeDecorator extends Arme{
    protected Arme arme;

    public ArmeDecorator(Arme arme) {
        super(arme.getPointAttaque() + 10, arme.getNom());
        this.arme = arme;
    }

    @Override
    public int getPointAttaque() {
        return arme.getPointAttaque();
    }


}