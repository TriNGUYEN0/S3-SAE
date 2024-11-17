package universite_paris8.iut.abenibrahim.sae_dev2.controleur;

public interface Command {
    public void execute();
    public void undo();
    public void print();


}
