package universite_paris8.iut.abenibrahim.sae_dev2.controleur;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
public class ControleurToucheMenu implements EventHandler<KeyEvent> {
    private ControleurMenu controleurMenu;
    public ControleurToucheMenu(ControleurMenu controleurMenu) {
        this.controleurMenu = controleurMenu;
    }

    public void handle(KeyEvent event) {
        KeyCode k = event.getCode();

        switch (k) {
            case SPACE -> {
                controleurMenu.skipAnimation();
            }
        }
    }

}
