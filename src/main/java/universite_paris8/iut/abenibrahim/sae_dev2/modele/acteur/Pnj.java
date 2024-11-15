package universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur;

import universite_paris8.iut.abenibrahim.sae_dev2.modele.EnvironnementPack.Environnement;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Pnj extends Acteur {
    private StringProperty dialogue;

    public Pnj(Environnement e, int x, int y, String dialogue) {
        super(e, x, y, 0, 100);
        this.dialogue = new SimpleStringProperty(dialogue);


        this.setCombatStrategy(null);
        this.setDeplacementStrategy(null);
    }

    // Getter và Setter cho dialogue
    public String getDialogue() {
        return dialogue.get();
    }

    public void setDialogue(String dialogue) {
        this.dialogue.set(dialogue);
    }

    public StringProperty dialogueProperty() {
        return dialogue;
    }
}
