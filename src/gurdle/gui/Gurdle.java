package gurdle.gui;

import gurdle.Model;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import util.Observer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * The graphical user interface to the Wordle game model in
 * {@link Model}.
 *
 * @author Tiffany Lee
 */
public class Gurdle extends Application
        implements Observer< Model, String > {

    private Model model;
    private Label[][] charGuess;

    @Override public void init() {
        this.charGuess = new Label[5][6];
        System.out.println( "TODO - init code here" );
    }

    @Override
    public void start( Stage mainStage ) {
        BorderPane gurdle = new BorderPane();

        gurdle.setTop(this.makeTop());
        gurdle.setBottom(this.makeBottom());
        gurdle.setCenter(this.makeCenter());


        Scene scene = new Scene(gurdle);
        mainStage.setScene(scene);
        mainStage.setTitle("Gurdle");
        mainStage.setWidth(600);
        mainStage.setHeight(800);
        mainStage.show();
    }

    private HBox makeTop(){
        HBox top = new HBox();
        Label guesses = new Label("#guesses: ");
        guesses.setStyle( "-fx-font: 18px Menlo" );

        Label title = new Label("Make a guess!");
        title.setStyle( "-fx-font: 18px Menlo" );

        Label secret = new Label("secret: ");
        secret.setStyle( "-fx-font: 18px Menlo" );

        top.getChildren().addAll(guesses, title, secret);
        top.setSpacing(120);
        return top;
    }

    private BorderPane makeBottom(){
        BorderPane keypadButtons = new BorderPane();
        // KEYBOARD
        GridPane alphabet = new GridPane();
        char ch = 'A';
        for ( int r = 0; r < 3; ++r ) {
            for ( int c = 0; c < 10; ++c ) {
                if(ch <= 'Z'){
                    Button letter = new Button(Character.toString(ch));
                    letter.setStyle( "-fx-font: 18px Menlo" );
                    alphabet.add(letter, c, r);
                    ch++;
                }
            }
        }
        alphabet.setAlignment(Pos.CENTER);
        // ENTER
        Button enter = new Button("Enter");
        enter.setStyle( "-fx-font: 18px Menlo" );
        keypadButtons.setRight(enter);
        // Two bottom buttons
        HBox gameCheat = new HBox();
        // NEW GAME
        Button newGame = new Button("New Game");
        newGame.setStyle( "-fx-font: 18px Menlo" );
        // CHEAT
        Button cheat = new Button("Cheat");
        cheat.setStyle( "-fx-font: 18px Menlo" );
        gameCheat.getChildren().add(newGame);
        gameCheat.getChildren().add(cheat);
        gameCheat.setAlignment(Pos.BOTTOM_CENTER);


        keypadButtons.setBottom(gameCheat);
        keypadButtons.setCenter(alphabet);
        return keypadButtons;
    }

    private GridPane makeCenter(){
        // CENTER
        GridPane charGuesses = new GridPane();
        for(int r = 0; r < 5; r++){
            for(int c = 0; c < 6; c++){
                this.charGuess[r][c] = new Label("");
                this.charGuess[r][c].setStyle("""
                            -fx-padding: 2;
                            -fx-border-style: solid inside;
                            -fx-border-width: 2;
                            -fx-border-insets: 10;
                            -fx-border-radius: 2;
                            -fx-border-color: black;
                """);
                charGuesses.add(this.charGuess[r][c], r, c);
            }
        }
        charGuesses.setAlignment(Pos.CENTER);
        return charGuesses;
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
