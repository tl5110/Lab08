package gurdle.gui;

import gurdle.CharChoice;
import gurdle.Model;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
    /** View/Controller access to model */
    private Model model;
    /** Grid of guesses' letters */
    private Label[][] charGuess;
    /** List of all the letter keys/buttons */
    private final Button[] alphabetList = new Button[26];
    /** Number of guesses made */
    private final Label guessNum = new Label();
    /** Message that tells user if they won, lost, or need to keep trying */
    private final Label message = new Label();
    /** List of all the letter keys/buttons */
    private final Label secret = new Label();
    /** Background color gray */
    private static final Background GRAY = new Background(new BackgroundFill(Color.GRAY, null, null));
    /** Background color orange */
    private static final Background ORANGE = new Background(new BackgroundFill(Color.ORANGE, null, null));
    /** Background color green */
    private static final Background GREEN = new Background(new BackgroundFill(Color.GREEN, null, null));
    /** Background color white */
    private static final Background WHITE = new Background(new BackgroundFill(Color.WHITE, null, null));

    /**
     * Create the Wordle/Gurdle model and register this object as an observer
     * of it. Initializes the grid of guesses' letters.
     */
    @Override public void init() {
        this.model = new Model();
        model.newGame();
        model.addObserver(this);
        this.charGuess = new Label[6][5];
    }

    /**
     * Set up the GUI.
     * Here the title and window displaying the guesses made so far, letters
     * entered so far, number of completed attempts, messages like won/lost,
     * secret word if user chose to "cheat", and buttons for letters, starting
     * a new game, and "cheating" are created
     *
     * @param mainStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
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

    /**
     * Sets up the number of completed attempts, message to keep guessing,
     * and the secret word
     *
     * @return HBox containing the information mentioned above
     */
    public HBox makeTop(){
        HBox top = new HBox();
        // GUESSES
        guessNum.setText("#guesses: " + model.numAttempts());
        guessNum.setStyle( "-fx-font: 18px Menlo" );
        // MESSAGE
        message.setText("Make a guess!");
        message.setStyle( "-fx-font: 18px Menlo" );
        // SECRET
        secret.setStyle( "-fx-font: 18px Menlo" );
        top.getChildren().addAll(guessNum, message, secret);
        top.setSpacing(50);
        top.setAlignment(Pos.TOP_CENTER);
        return top;
    }

    /**
     * Sets up and creates the keypad, enter, new game, and cheat buttons
     *
     * @return BorderPane containing buttons mentioned above
     */
    public BorderPane makeBottom(){
        BorderPane keypadButtons = new BorderPane();
        // KEYPAD
        GridPane keypad = new GridPane();
        int row = 0;
        int col = 0;
        int i = 0;
        Character[]  alphabet = {
                'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P',
                'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L',
                'Z', 'X', 'C', 'V', 'B', 'N', 'M'};
        for(char letter : alphabet){
            Button letterKey = new Button(String.valueOf(letter));
            alphabetList[i++]= letterKey;
            letterKey.setBackground(WHITE);
            letterKey.setStyle( """
                            -fx-font: 20px Menlo;
                            -fx-border-style: solid inside;
                            -fx-border-radius: 1;
            """);
            letterKey.setMinSize(30, 35);
            letterKey.setOnAction(event -> this.model.enterNewGuessChar(letter));
            letterKey.setAlignment(Pos.BOTTOM_CENTER);
            keypad.add(letterKey, col, row);
            col++;
            if(col == 10){
                col = 0;
                row++;
            }
        }
        keypad.setAlignment(Pos.CENTER);
        // ENTER
        Button enter = new Button("Enter");
        enter.setStyle( "-fx-font: 18px Menlo" );
        keypadButtons.setRight(enter);
        enter.setOnAction(event -> model.confirmGuess());
        // Two bottom buttons
        HBox gameCheat = new HBox(20);
        // NEW GAME
        Button newGame = new Button("New Game");
        newGame.setStyle( "-fx-font: 18px Menlo" );
        newGame.setOnAction(event -> {
            model.newGame();
            secret.setText("");
        });
        // CHEAT
        Button cheat = new Button("Cheat");
        cheat.setStyle( "-fx-font: 18px Menlo" );
        cheat.setOnAction(event -> secret.setText("secret: " + model.secret()));

        gameCheat.getChildren().add(newGame);
        gameCheat.getChildren().add(cheat);
        gameCheat.setAlignment(Pos.BOTTOM_CENTER);

        keypadButtons.setBottom(gameCheat);
        keypadButtons.setCenter(keypad);
        return keypadButtons;
    }

    /**
     * Sets up and creates the grid of the guesses' letters
     *
     * @return GridPane containing information mentioned above
     */
    public GridPane makeCenter(){
        GridPane gridGuesses = new GridPane();
        for(int r = 0; r < 6; r++){
            for(int c = 0; c < 5; c++){
                this.charGuess[r][c] = new Label();
                this.charGuess[r][c].setBackground(WHITE);
                this.charGuess[r][c].setStyle("""
                            -fx-padding: 25;
                            -fx-border-style: solid inside;
                            -fx-border-width: 2;
                            -fx-border-insets: 10;
                            -fx-border-radius: 2;
                            -fx-border-color: black;
                """);
                gridGuesses.add(this.charGuess[r][c], c, r);
            }
        }
        gridGuesses.setAlignment(Pos.CENTER);
        return gridGuesses;
    }

    /**
     * The model -- the subject -- has some changes.
     * Query the model to find out what's going on and display the current
     * state of all the legitimate guesses.
     * If the user lost, display the secret word.
     *
     * @param model the object that wishes to inform this object
     *                about something that has happened.
     * @param message optional status message the model can send to the observer
     */
    @Override
    public void update(Model model, String message) {
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 5; c++) {
                guessNum.setText("#guesses: " + model.numAttempts());
                CharChoice cc = model.get(r, c);
                final String ch = String.valueOf(cc.getChar());
                final CharChoice.Status ccStatus = cc.getStatus();
                this.charGuess[r][c].setText(ch);

                if(ccStatus.equals(CharChoice.Status.WRONG)) {
                    this.charGuess[r][c].setBackground(GRAY);
                } else if(ccStatus.equals(CharChoice.Status.WRONG_POS)){
                    this.charGuess[r][c].setBackground(ORANGE);
                } else if(ccStatus.equals(CharChoice.Status.RIGHT_POS)){
                    this.charGuess[r][c].setBackground(GREEN);
                } else if(ccStatus.equals(CharChoice.Status.EMPTY)){
                    this.charGuess[r][c].setBackground(WHITE);
                }

                for(Button letter : alphabetList){
                    if(letter.getText().equals(charGuess[r][c].getText())){
//                    if(model.usedLetter(letter.getText().charAt(0))){
                        if(ccStatus.equals(CharChoice.Status.WRONG)) {
                            letter.setBackground(GRAY);
                        } else if(ccStatus.equals(CharChoice.Status.WRONG_POS)){
                            letter.setBackground(ORANGE);
                        } else if(ccStatus.equals(CharChoice.Status.RIGHT_POS)){
                            letter.setBackground(GREEN);
                        } else if(ccStatus.equals(CharChoice.Status.EMPTY)){
                            letter.setBackground(GRAY);
                        }
                    }
                }
            }
        }
        if(model.gameState().equals(Model.GameState.WON)){
            this.message.setText("You won!");
        } else if(model.gameState().equals(Model.GameState.LOST)){
            this.message.setText("You lost!");
            this.secret.setText("secret: " + model.secret());
        }
    }

    /**
     * Launches the application.
     *
     * @param args a single, optional word, to use as the first secret word
     */
    public static void main( String[] args ) {
        if ( args.length > 1 ) {
            System.err.println( "Usage: java Gurdle [1st-secret-word]" );
        }
        Application.launch( args );
    }
}
