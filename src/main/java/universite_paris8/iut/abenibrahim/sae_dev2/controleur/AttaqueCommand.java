package universite_paris8.iut.abenibrahim.sae_dev2.controleur;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Acteur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Ennemi;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Joueur;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.objet.StrategieAttaqueRapprocher;

public class AttaqueCommand implements Command{
    private Joueur joueur;
    private Acteur ennemi;
    private int ptDeVie ;
    private int i = 0;
    public AttaqueCommand(Joueur j){
        this.ennemi = null;
        this.joueur = j;
        this.ptDeVie=joueur.getArmeEquipee().getPointAttaque();
        i=0;
    }
    public AttaqueCommand(Joueur j,Acteur ennemi){
        this.ennemi = ennemi;
        this.joueur = j;
        this.ptDeVie=ennemi.getArmeEquipee().getPointAttaque();
        i=1;
        //si l'ennemie attaque le joueur
    }
    public void execute(){
        if(i==0){
            this.ennemi = joueur.attaquer();

        }
        if(i==1){
            this.joueur = (Joueur) ennemi.attaquer();

        }
    }
    public void undo(){
        if(i==0){//le joueur a attaqué l'ennemie
        if(this.ennemi != null){
        this.ennemi.recoisDegat(ptDeVie*-1);}
        }
       if(i==1){
           if(this.joueur != null){
               this.joueur.recoisDegat(ptDeVie*-1);}
       }

    }
public void print(){

    if(i==0){//le joueur a attaqué l'ennemie
        if(this.ennemi != null){
            System.out.println("Le joueur a enlevé : "+ptDeVie+" a l'ennemi");}
    }
    if(i==1){
        if(this.joueur != null){
            System.out.println("L'ennemi a enlevé : "+ptDeVie+" au joueur");}
    }
    }

}

