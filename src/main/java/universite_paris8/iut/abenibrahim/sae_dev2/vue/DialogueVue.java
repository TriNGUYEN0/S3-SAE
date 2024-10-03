package universite_paris8.iut.abenibrahim.sae_dev2.vue;

import javafx.fxml.FXML;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Environnement;
import javafx.scene.control.Label;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.acteur.Pnj;

import java.util.List;

public class DialogueVue {
    @FXML
    public Label dialogueBox;
    @FXML
    public Label dialogueBox2;

    private Environnement environnement;

    public DialogueVue(Label dialogueBox, Environnement environnement, Label dialogueBox2){
        this.dialogueBox = dialogueBox;
        this.environnement = environnement;
        this.dialogueBox2 = dialogueBox2;
    }

    public void afficherDialoguePnj(){
        List<Pnj> pnjs = environnement.getPnjList();
        if (!pnjs.isEmpty()) {
            Pnj pnj = pnjs.get(0);
            if(environnement.getGuts().getPointDef()==100){
                dialogueBox.setText("je vois que vous avez l'objet mais faites attention il met des obstacles que lui seul peut voir.");
            }else{
                dialogueBox.setText(pnj.getDialogue());
            }
            dialogueBox.setVisible(true);
            dialogueBox.setWrapText(true);
            mettreAJourPositionDialogue(pnj);
        }
    }
    private void mettreAJourPositionDialogue(Pnj pnj) {
        dialogueBox.setLayoutX(pnj.getX());
        dialogueBox.setLayoutY(pnj.getY() - dialogueBox.getHeight());
    }

    public void masquerDialogue() {
        dialogueBox.setVisible(false);
        dialogueBox2.setVisible(false);
    }
}
