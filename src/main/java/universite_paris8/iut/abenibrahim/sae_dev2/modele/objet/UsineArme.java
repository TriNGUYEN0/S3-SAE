package universite_paris8.iut.abenibrahim.sae_dev2.modele.objet;

public class UsineArme {

    public static Arme creerArme(String nom) {
        return switch (nom){
            case "Epée" -> new Epée();
            case "Hache" -> new Hache();
            case "Pistolet" -> new ArmeDistance();
            default -> null;
        };
    }

}
