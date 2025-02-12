import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private John john = new John();

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/Chad.png"));
    private final Image johnImage = new Image(this.getClass().getResourceAsStream("/images/gigachad.png"));

    /**
     * initialisation function for GUI
     */
    @FXML
    public void initialize() throws IOException {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        printJohnUI();
    }

    /** Injects the Duke instance */
    public void setJohn(John j) {
        john = j;
    }

    /**
     * Prints the initialisation message on the GUI
     * @throws IOException
     */
    public void printJohnUI() throws IOException {
        dialogContainer.getChildren().addAll(
                DialogBox.getJohnDialog(john.returnLogo(), johnImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.S
     */
    @FXML
    public void handleUserInput() throws IOException {
        String input = userInput.getText();
        String response = john.getResponse(input);

        if (response.contains("Bye. Hope to see you again soon!")) {
            Platform.exit();
        } else {
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getJohnDialog(response, johnImage)
            );
            userInput.clear();
        }
    }
}
