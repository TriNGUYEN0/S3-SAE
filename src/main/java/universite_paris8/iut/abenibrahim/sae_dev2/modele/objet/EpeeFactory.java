package universite_paris8.iut.abenibrahim.sae_dev2.modele.objet;

public class EpeeFactory extends ArmeFactory {
    @Override
    public Arme createArme(int x, int y) {
        return new Epee(x, y);
    }
}
