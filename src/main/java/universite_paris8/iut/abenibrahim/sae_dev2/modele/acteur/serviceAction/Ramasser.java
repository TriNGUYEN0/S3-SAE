package universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.serviceAction;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack.Environnement;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.InventaireObjets;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Arme;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Soin;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.ObjetDefense;

public class Ramasser {

    private static final int DISTANCE_RAMASSAGE = 40; // Phạm vi nhặt đồ

    public Arme ramasserArme(Joueur joueur, Environnement environnement) {
        int posX = joueur.getX();
        int posY = joueur.getY();
        for (Arme arme : environnement.getArmeMap()) {
            double distance = Math.sqrt(Math.pow(posX - arme.getX(), 2) + Math.pow(posY - arme.getY(), 2));
            if (distance <= DISTANCE_RAMASSAGE) {
                joueur.ajouterArme(new InventaireObjets(arme.getImage(), arme));
                environnement.getArmeMap().remove(arme);
                return arme;
            }
        }
        return null;
    }

    public Soin ramasserSoin(Joueur joueur, Environnement environnement) {
        int posX = joueur.getX();
        int posY = joueur.getY();
        for (Soin soin : environnement.getSoinMap()) {
            double distance = Math.sqrt(Math.pow(posX - soin.getX(), 2) + Math.pow(posY - soin.getY(), 2));
            if (distance <= DISTANCE_RAMASSAGE) {
                joueur.incrementerNbSoin(1);
                environnement.getSoinMap().remove(soin);
                return soin;
            }
        }
        return null;
    }

    public ObjetDefense ramasserObjetDefense(Joueur joueur, Environnement environnement) {
        int posX = joueur.getX();
        int posY = joueur.getY();
        for (ObjetDefense objDef : environnement.getObjetDefenseList()) {
            double distance = Math.sqrt(Math.pow(posX - objDef.getX(), 2) + Math.pow(posY - objDef.getY(), 2));
            if (distance <= DISTANCE_RAMASSAGE) {
                joueur.setPointDef(objDef.getDefDonner());
                environnement.getObjetDefenseList().remove(objDef);
                return objDef;
            }
        }
        return null;
    }
}
