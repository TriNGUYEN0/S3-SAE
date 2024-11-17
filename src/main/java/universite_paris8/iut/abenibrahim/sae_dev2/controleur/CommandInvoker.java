package universite_paris8.iut.abenibrahim.sae_dev2.controleur;

import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;
import universite_paris8.iut.abenibrahim.sae_dev2.modele.Environnement;

import java.util.Stack;



public class CommandInvoker {
    private  Stack<Command> historique = new Stack<Command>();
    private static CommandInvoker uniqueInstance = null;
    private CommandInvoker(){

    }

    public void executeCommand(Command c){
       c.execute();
       c.print();
       historique.push(c);
   }

   public void undoCommand(){
      if(!historique.isEmpty()){
       Command c =historique.pop();
       c.undo();
      }
   }
    public static CommandInvoker getUniqueInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new CommandInvoker();
        }
        return  uniqueInstance;
    }

}
