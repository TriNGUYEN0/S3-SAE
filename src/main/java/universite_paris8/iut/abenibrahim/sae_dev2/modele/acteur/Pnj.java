package universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack.Environnement;

public class Pnj extends Acteur {
    private StringProperty dialogue;

    public Pnj(Environnement environnement, int x, int y, int pv) {
        super(environnement, x, y, 0, pv); // vitesse est 0 pour un PNJ statique
        this.dialogue = new SimpleStringProperty(
                "Je vois que vous voulez tuer le Cleric beast. Mais malheureusement il vous faut un objet qui augmente vos points de défense."
        );
    }

    // Implémentation des méthodes abstraites d'Acteur


    // Gestion du dialogue
    public StringProperty dialogueProperty() {
        return dialogue;
    }

    public String getDialogue() {
        return dialogue.get();
    }

    public void setDialogue(String dialogue) {
        this.dialogue.set(dialogue);
    }
}
