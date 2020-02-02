/* Gym Application Package */
package middlesexgym;

/* Class Requirements & Dependencies */
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

/**
 * This class deals with placing & layout of User Interface Components. The
 * class is the abstracted for security measures.
 * 
 * @author Lavesh Panjwani (M00692913)
 */
public abstract class ClientUIView extends VBox {

    protected final SplitPane splitPane;
    protected final AnchorPane currentAnchorPane;
    protected final Label currentBookingLabel;
    protected final Label currentSearchLabel;
    protected final TextField currentSearchText;
    protected final Button currentClientSearchButton;
    protected final TextArea mainTextArea;
    protected final Button currentPTSearchButton;
    protected final Button currentDateSearchButton;
    protected final Button currentResetButton;
    protected final AnchorPane createAnchorPane;
    protected final Label createBookingLabel;
    protected final Label createPTLabel;
    protected final Label createClientLabel;
    protected final DatePicker createDateSelect;
    protected final Label createDateLabel;
    protected final Label createStartTimeLabel;
    protected final Label createEndTimeLabel;
    protected final Label createFocusLabel;
    protected final TextField createFocusText;
    protected final TextField createStartTimeText;
    protected final Button createBookingButton;
    protected final Button createClientMore;
    protected final Button createPTMore;
    protected final Button createDeleteButton;
    protected final Button createUpdateButton;
    protected final ChoiceBox createTypeButton;
    protected final Label createTypeLabel;
    protected final Line line;
    protected final TextField createPTSelect;
    protected final TextField createClientSelect;
    protected final TextField createEndTimeText;

    public ClientUIView() {

        splitPane = new SplitPane();
        currentAnchorPane = new AnchorPane();
        currentBookingLabel = new Label();
        currentSearchLabel = new Label();
        currentSearchText = new TextField();
        currentClientSearchButton = new Button();
        mainTextArea = new TextArea();
        currentPTSearchButton = new Button();
        currentDateSearchButton = new Button();
        currentResetButton = new Button();
        createAnchorPane = new AnchorPane();
        createBookingLabel = new Label();
        createPTLabel = new Label();
        createClientLabel = new Label();
        createDateSelect = new DatePicker();
        createDateLabel = new Label();
        createStartTimeLabel = new Label();
        createEndTimeLabel = new Label();
        createFocusLabel = new Label();
        createFocusText = new TextField();
        createStartTimeText = new TextField();
        createBookingButton = new Button();
        createClientMore = new Button();
        createPTMore = new Button();
        createDeleteButton = new Button();
        createUpdateButton = new Button();
        createTypeButton = new ChoiceBox();
        createTypeLabel = new Label();
        line = new Line();
        createPTSelect = new TextField();
        createClientSelect = new TextField();
        createEndTimeText = new TextField();

        VBox.setVgrow(splitPane, javafx.scene.layout.Priority.ALWAYS);
        splitPane.setDividerPositions(0.5);
        splitPane.setFocusTraversable(true);
        splitPane.setMaxHeight(Double.MAX_VALUE);
        splitPane.setMaxWidth(Double.MAX_VALUE);
        splitPane.setMinHeight(678.0);
        splitPane.setMinWidth(1057.0);
        splitPane.setPrefHeight(678.0);
        splitPane.setPrefWidth(1367.0);

        currentAnchorPane.setFocusTraversable(true);
        currentAnchorPane.setMaxHeight(Double.MAX_VALUE);
        currentAnchorPane.setMaxWidth(Double.MAX_VALUE);
        currentAnchorPane.setNodeOrientation(javafx.geometry.NodeOrientation.LEFT_TO_RIGHT);
        currentAnchorPane.setPrefHeight(703.0);
        currentAnchorPane.setPrefWidth(986.0);
        currentAnchorPane.setSnapToPixel(false);

        currentBookingLabel.setLayoutX(26.0);
        currentBookingLabel.setLayoutY(9.0);
        currentBookingLabel.setPrefHeight(27.0);
        currentBookingLabel.setPrefWidth(166.0);
        currentBookingLabel.setText("All Bookings");
        currentBookingLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        currentBookingLabel.setTextFill(javafx.scene.paint.Color.valueOf("#9f9f9f"));
        currentBookingLabel.setWrapText(false);
        currentBookingLabel.setFont(new Font("System Bold", 22.0));

        currentSearchLabel.setAlignment(javafx.geometry.Pos.CENTER);
        currentSearchLabel.setLayoutX(435.0);
        currentSearchLabel.setLayoutY(14.0);
        currentSearchLabel.setText("Search:");
        currentSearchLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        currentSearchLabel.setTextFill(javafx.scene.paint.Color.valueOf("#9f9f9f"));
        currentSearchLabel.setWrapText(false);
        currentSearchLabel.setFont(new Font(18.0));

        currentSearchText.setLayoutX(503.0);
        currentSearchText.setLayoutY(15.0);

        currentClientSearchButton.setAlignment(javafx.geometry.Pos.CENTER);
        currentClientSearchButton.setDefaultButton(true);
        currentClientSearchButton.setLayoutX(695.0);
        currentClientSearchButton.setLayoutY(15.0);
        currentClientSearchButton.setMnemonicParsing(false);
        currentClientSearchButton.setStyle("-fx-background-color: #ffa64d;");
        currentClientSearchButton.setText("by Client");
        currentClientSearchButton.setCursor(Cursor.DEFAULT);

        mainTextArea.setLayoutX(25.0);
        mainTextArea.setLayoutY(51.0);
        mainTextArea.setPrefHeight(609.0);
        mainTextArea.setPrefWidth(960.0);

        currentPTSearchButton.setLayoutX(781.0);
        currentPTSearchButton.setLayoutY(15.0);
        currentPTSearchButton.setMnemonicParsing(false);
        currentPTSearchButton.setStyle("-fx-background-color: #4da6ff;");
        currentPTSearchButton.setText("by PT");
        currentPTSearchButton.setCursor(Cursor.DEFAULT);

        currentDateSearchButton.setAlignment(javafx.geometry.Pos.CENTER);
        currentDateSearchButton.setLayoutX(848.0);
        currentDateSearchButton.setLayoutY(15.0);
        currentDateSearchButton.setMnemonicParsing(false);
        currentDateSearchButton.setStyle("-fx-background-color: #4dffa6;");
        currentDateSearchButton.setText("by Date");
        currentDateSearchButton.setCursor(Cursor.DEFAULT);

        currentResetButton.setAlignment(javafx.geometry.Pos.CENTER);
        currentResetButton.setLayoutX(928.0);
        currentResetButton.setLayoutY(15.0);
        currentResetButton.setMnemonicParsing(false);
        currentResetButton.setStyle("-fx-background-color: #ff4da6;");
        currentResetButton.setText("Reset");
        currentResetButton.setCursor(Cursor.DEFAULT);

        createAnchorPane.setFocusTraversable(true);
        createAnchorPane.setMaxHeight(Double.MAX_VALUE);
        createAnchorPane.setMaxWidth(360.0);
        createAnchorPane.setNodeOrientation(javafx.geometry.NodeOrientation.LEFT_TO_RIGHT);
        createAnchorPane.setPrefWidth(372.0);
        createAnchorPane.setSnapToPixel(false);

        createBookingLabel.setAlignment(javafx.geometry.Pos.CENTER);
        createBookingLabel.setLayoutX(14.0);
        createBookingLabel.setLayoutY(14.0);
        createBookingLabel.setPrefHeight(32.0);
        createBookingLabel.setPrefWidth(181.0);
        createBookingLabel.setText("Booking Actions");
        createBookingLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        createBookingLabel.setTextFill(javafx.scene.paint.Color.valueOf("#9f9f9f"));
        createBookingLabel.setWrapText(false);
        createBookingLabel.setFont(new Font("System Bold", 22.0));

        createPTLabel.setAlignment(javafx.geometry.Pos.CENTER);
        createPTLabel.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        createPTLabel.setLayoutX(11.0);
        createPTLabel.setLayoutY(223.0);
        createPTLabel.setPrefHeight(27.0);
        createPTLabel.setPrefWidth(85.0);
        createPTLabel.setText("PT ID:");
        createPTLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        createPTLabel.setWrapText(false);
        createPTLabel.setFont(new Font(18.0));

        createClientLabel.setAlignment(javafx.geometry.Pos.CENTER);
        createClientLabel.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        createClientLabel.setLayoutX(10.0);
        createClientLabel.setLayoutY(165.0);
        createClientLabel.setPrefHeight(27.0);
        createClientLabel.setPrefWidth(85.0);
        createClientLabel.setText("Client ID:");
        createClientLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        createClientLabel.setWrapText(false);
        createClientLabel.setFont(new Font(18.0));

        createDateSelect.setLayoutX(106.0);
        createDateSelect.setLayoutY(287.0);
        createDateSelect.setPrefHeight(31.0);
        createDateSelect.setPrefWidth(240.0);
        createDateSelect.setPromptText("Today");
        createDateSelect.setCursor(Cursor.DEFAULT);

        createDateLabel.setAlignment(javafx.geometry.Pos.CENTER);
        createDateLabel.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        createDateLabel.setLayoutX(11.0);
        createDateLabel.setLayoutY(289.0);
        createDateLabel.setPrefHeight(27.0);
        createDateLabel.setPrefWidth(85.0);
        createDateLabel.setText("Date:");
        createDateLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        createDateLabel.setWrapText(false);
        createDateLabel.setFont(new Font(18.0));

        createStartTimeLabel.setAlignment(javafx.geometry.Pos.CENTER);
        createStartTimeLabel.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        createStartTimeLabel.setLayoutX(10.0);
        createStartTimeLabel.setLayoutY(353.0);
        createStartTimeLabel.setPrefHeight(27.0);
        createStartTimeLabel.setPrefWidth(85.0);
        createStartTimeLabel.setText("Start Time:");
        createStartTimeLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        createStartTimeLabel.setWrapText(false);
        createStartTimeLabel.setFont(new Font(18.0));

        createEndTimeLabel.setAlignment(javafx.geometry.Pos.CENTER);
        createEndTimeLabel.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        createEndTimeLabel.setLayoutX(12.0);
        createEndTimeLabel.setLayoutY(416.0);
        createEndTimeLabel.setPrefHeight(27.0);
        createEndTimeLabel.setPrefWidth(85.0);
        createEndTimeLabel.setText("End Time:");
        createEndTimeLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        createEndTimeLabel.setWrapText(false);
        createEndTimeLabel.setFont(new Font(18.0));

        createFocusLabel.setAlignment(javafx.geometry.Pos.CENTER);
        createFocusLabel.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        createFocusLabel.setLayoutX(14.0);
        createFocusLabel.setLayoutY(484.0);
        createFocusLabel.setPrefHeight(27.0);
        createFocusLabel.setPrefWidth(85.0);
        createFocusLabel.setText("Focus: ");
        createFocusLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        createFocusLabel.setWrapText(false);
        createFocusLabel.setFont(new Font(18.0));

        createFocusText.setLayoutX(106.0);
        createFocusText.setLayoutY(483.0);
        createFocusText.setPrefHeight(31.0);
        createFocusText.setPrefWidth(239.0);

        createStartTimeText.setLayoutX(106.0);
        createStartTimeText.setLayoutY(351.0);
        createStartTimeText.setPrefHeight(31.0);
        createStartTimeText.setPrefWidth(240.0);
        createStartTimeText.setPromptText("HH:MM");

        createBookingButton.setAlignment(javafx.geometry.Pos.CENTER);
        createBookingButton.setDefaultButton(true);
        createBookingButton.setLayoutX(116.0);
        createBookingButton.setLayoutY(559.0);
        createBookingButton.setMnemonicParsing(false);
        createBookingButton.setStyle("-fx-background-color: #4ff4a2;");
        createBookingButton.setText("Add Booking");
        createBookingButton.setFont(new Font(18.0));
        createBookingButton.setCursor(Cursor.DEFAULT);

        createClientMore.setLayoutX(322.0);
        createClientMore.setLayoutY(163.0);
        createClientMore.setMnemonicParsing(false);
        createClientMore.setPrefHeight(31.0);
        createClientMore.setPrefWidth(30.0);
        createClientMore.setText("New");

        createPTMore.setLayoutX(321.0);
        createPTMore.setLayoutY(224.0);
        createPTMore.setMnemonicParsing(false);
        createPTMore.setPrefHeight(31.0);
        createPTMore.setPrefWidth(30.0);
        createPTMore.setText("New");

        createDeleteButton.setAlignment(javafx.geometry.Pos.CENTER);
        createDeleteButton.setLayoutX(190.0);
        createDeleteButton.setLayoutY(606.0);
        createDeleteButton.setMnemonicParsing(false);
        createDeleteButton.setStyle("-fx-background-color: #f44f4f;");
        createDeleteButton.setText("Delete Booking");
        createDeleteButton.setFont(new Font(18.0));
        createDeleteButton.setCursor(Cursor.DEFAULT);

        createUpdateButton.setAlignment(javafx.geometry.Pos.CENTER);
        createUpdateButton.setLayoutX(27.0);
        createUpdateButton.setLayoutY(606.0);
        createUpdateButton.setMnemonicParsing(false);
        createUpdateButton.setStyle("-fx-background-color: #f4a24f;");
        createUpdateButton.setText("Update Booking");
        createUpdateButton.setFont(new Font(18.0));
        createUpdateButton.setCursor(Cursor.DEFAULT);

        createTypeButton.setLayoutX(109.0);
        createTypeButton.setLayoutY(74.0);
        createTypeButton.setPrefHeight(31.0);
        createTypeButton.setPrefWidth(240.0);
        createTypeButton.setOpaqueInsets(new Insets(0.0));

        createTypeLabel.setAlignment(javafx.geometry.Pos.CENTER);
        createTypeLabel.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        createTypeLabel.setLayoutX(12.0);
        createTypeLabel.setLayoutY(76.0);
        createTypeLabel.setPrefHeight(27.0);
        createTypeLabel.setPrefWidth(85.0);
        createTypeLabel.setText("Type:");
        createTypeLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        createTypeLabel.setWrapText(false);
        createTypeLabel.setFont(new Font(18.0));

        line.setEndX(162.4000244140625);
        line.setLayoutX(200.0);
        line.setLayoutY(129.0);
        line.setStartX(-199.99996948242188);

        createPTSelect.setLayoutX(107.0);
        createPTSelect.setLayoutY(224.0);
        createPTSelect.setPrefHeight(31.0);
        createPTSelect.setPrefWidth(207.0);

        createClientSelect.setLayoutX(107.0);
        createClientSelect.setLayoutY(163.0);
        createClientSelect.setPrefHeight(31.0);
        createClientSelect.setPrefWidth(207.0);

        createEndTimeText.setLayoutX(106.0);
        createEndTimeText.setLayoutY(414.0);
        createEndTimeText.setPrefHeight(31.0);
        createEndTimeText.setPrefWidth(240.0);
        createEndTimeText.setPromptText("HH:MM");

        currentAnchorPane.getChildren().add(currentBookingLabel);
        currentAnchorPane.getChildren().add(currentSearchLabel);
        currentAnchorPane.getChildren().add(currentSearchText);
        currentAnchorPane.getChildren().add(currentClientSearchButton);
        currentAnchorPane.getChildren().add(mainTextArea);
        currentAnchorPane.getChildren().add(currentPTSearchButton);
        currentAnchorPane.getChildren().add(currentDateSearchButton);
        currentAnchorPane.getChildren().add(currentResetButton);
        splitPane.getItems().add(currentAnchorPane);
        createAnchorPane.getChildren().add(createBookingLabel);
        createAnchorPane.getChildren().add(createPTLabel);
        createAnchorPane.getChildren().add(createClientLabel);
        createAnchorPane.getChildren().add(createDateSelect);
        createAnchorPane.getChildren().add(createDateLabel);
        createAnchorPane.getChildren().add(createStartTimeLabel);
        createAnchorPane.getChildren().add(createEndTimeLabel);
        createAnchorPane.getChildren().add(createFocusLabel);
        createAnchorPane.getChildren().add(createFocusText);
        createAnchorPane.getChildren().add(createStartTimeText);
        createAnchorPane.getChildren().add(createBookingButton);
        createAnchorPane.getChildren().add(createClientMore);
        createAnchorPane.getChildren().add(createPTMore);
        createAnchorPane.getChildren().add(createDeleteButton);
        createAnchorPane.getChildren().add(createUpdateButton);
        createAnchorPane.getChildren().add(createTypeButton);
        createAnchorPane.getChildren().add(createTypeLabel);
        createAnchorPane.getChildren().add(line);
        createAnchorPane.getChildren().add(createPTSelect);
        createAnchorPane.getChildren().add(createClientSelect);
        createAnchorPane.getChildren().add(createEndTimeText);
        splitPane.getItems().add(createAnchorPane);
        getChildren().add(splitPane);

    }
}
