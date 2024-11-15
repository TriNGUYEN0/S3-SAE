package universite_paris8.iut.abenibrahim.sae_dev2.modele.objet;

public class ArmeDistanceFactory extends ArmeFactory{
    @Override
    public Arme createArme() {
        return new ArmeDistance();
    }
}