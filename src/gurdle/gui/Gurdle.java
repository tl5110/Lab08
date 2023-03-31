package gurdle.gui;

import gurdle.Model;
import util.Observer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * The graphical user interface to the Wordle game model in
 * {@link Model}.
 *
 * @author YOUR NAME HERE
 */
public class Gurdle extends Application
        implements Observer< Model, String > {

    private Model model;

    @Override public void init() {
        System.out.println( "TODO - init code here" );
    }

    @Override
    public void start( Stage mainStage ) {
        Scene scene =
                new Scene( new Label( "TODO GUI setup code goes here." ) );
        mainStage.setScene( scene );
        mainStage.show();
    }

    @Override
    public void update( Model model, String message ) {
        System.out.println( """
                TODO
                Here is where the model is queried
                and the view is updated."""
        );
    }

    public static void main( String[] args ) {
        if ( args.length > 1 ) {
            System.err.println( "Usage: java Gurdle [1st-secret-word]" );
        }
        Application.launch( args );
    }
}
