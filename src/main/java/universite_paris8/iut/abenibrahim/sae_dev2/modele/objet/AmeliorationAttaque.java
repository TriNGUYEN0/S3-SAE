package universite_paris8.iut.abenibrahim.sae_dev2.modele.objet;

public class AmeliorationAttaque extends ArmeDecorator{
    public AmeliorationAttaque(Arme arme) {
        super(arme);
    }

    @Override
    public int getPointAttaque() {
        return arme.getPointAttaque() + 10;
    }
}
