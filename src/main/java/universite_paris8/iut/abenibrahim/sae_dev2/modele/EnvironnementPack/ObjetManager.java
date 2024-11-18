package universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.*;

import java.util.List;


import java.util.ArrayList;
import java.util.List;

public class ObjetManager {
    private List<Arme> armeMap;
    private List<ObjetDefense> objetDefenseList;
    private List<Soin> soinMap;


    public ObjetManager() {
        this.armeMap = new ArrayList<>();
        this.objetDefenseList = new ArrayList<>();
        this.soinMap = new ArrayList<>();

        ArmeFactory epeeFactory = new EpeeFactory();
        Arme epee1 = epeeFactory.createArme(500, 500);

        armeMap.add(epee1);
    }

    public List<Arme> getArmeMap() {
        return armeMap;
    }

    public List<ObjetDefense> getObjetDefenseList() {
        return objetDefenseList;
    }

    public List<Soin> getSoinMap() {
        return soinMap;
    }

    public void ajouterArme(Arme arme) {
        armeMap.add(arme);
    }

    public void ajouterSoin(Soin soin) {
        soinMap.add(soin);
    }

    public void ajouterObjetDefense(ObjetDefense objetDefense) {
        objetDefenseList.add(objetDefense);
    }

    public void supprimerArme(Arme arme) {
        if (armeMap.contains(arme)) {
            armeMap.remove(arme);
            System.out.println("Vũ khí đã bị loại bỏ khỏi armeMap: " + arme.getNom());
        } else {
            System.err.println("Vũ khí không tồn tại trong armeMap: " + arme.getNom());
        }
    }


}