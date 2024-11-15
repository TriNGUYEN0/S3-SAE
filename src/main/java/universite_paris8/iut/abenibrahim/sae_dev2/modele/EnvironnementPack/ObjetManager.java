package universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Arme;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.ObjetDefense;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.Soin;

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


}