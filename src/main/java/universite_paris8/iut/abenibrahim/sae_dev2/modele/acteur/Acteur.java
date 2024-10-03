package universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.shape.Rectangle;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Direction;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Environnement;

public abstract class Acteur {
    private IntegerProperty x,y;

    Environnement environnement;
    private String id;

    private IntegerProperty pv;
    private int vitesse = 10;

    public Acteur(Environnement e,int x,int y,int v,int pv){
        this.x=new SimpleIntegerProperty(x);
        this.y=new SimpleIntegerProperty(y);
        this.pv=new SimpleIntegerProperty(pv);
        this.id= String.valueOf(1);
        this.environnement = e;
    }

    public String getId() {
        return id;
    }


    public void seDeplace(Direction direction) {
        int xTmp = getX() + direction.getX() * vitesse;
        int yTmp = getY() + direction.getY() * vitesse;

        if ( this.environnement.dansTerrain(xTmp,yTmp) && this.environnement.getMap().verifierCollisions(xTmp,yTmp)) {
            setX(getX()  + direction.getX() * vitesse);
            setY(getY()  + direction.getY() * vitesse);
        }
    }

    public  int getX() {
                    return this.x.getValue();
                }
    public  IntegerProperty XProprety() {
                    return this.x;
                }
    public  int getY() {
                    return this.y.getValue();
                }
    public IntegerProperty YProprety() {
                    return this.y;
                }
    public  void setX(int n){
                    this.x.setValue(n);
                }
    public  void setY(int n){
                    this.y.setValue(n);
                }
    public IntegerProperty pvProperty(){return this.pv;}
    public int getPv(){return this.pv.getValue();}

    public void setPv(int pv) {
        this.pv.set(pv);
    }

    public boolean estVivant(){
        return this.getPv()> 0;
    }
    public boolean estMort() {
        return getPv() <= 0;
    }
    public abstract void attaquer();

    public abstract void recoisDegat(int degat);

}

