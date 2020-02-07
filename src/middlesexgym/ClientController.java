/* Gym Application Package */
package middlesexgym;

/* Class Requirements & Dependencies */
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;
import java.sql.Time;
import java.util.Optional;
import java.util.Scanner;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;

/**
 * This class deals with handling & controlling User Interface components and
 * Server
 *
 * @author Lavesh Panjwani (M00692913)
 */
public class ClientController extends ClientView {

    // Indicates the Connection State & Streams
    private Socket socket = null;
    private ObjectOutputStream os;
    private Scanner in;

    // Stores Booking ID for Update & Delete Operations
    private int bookingID;

    // Booking Details required by Major Operations such as Add & Update Booking
    private int clientID;
    private int PTID;
    private Date date;
    private Time startTime;
    private Time endTime;
    private int focus;

    public ClientController() {
        super();

        // Current Bookings Section
        currentClientSearchButton.setOnAction(this::queryCurrentBookingsByClient);
        currentPTSearchButton.setOnAction(this::queryCurrentBookingsByPT);
        currentDateSearchButton.setOnAction(this::queryCurrentBookingsByDate);
        currentResetButton.setOnAction(this::resetBookingsScreen);

        // Booking Operation Section
        createTypeButton.getItems().add("Add Booking");
        createTypeButton.getItems().add("Update/Delete Booking");
        createTypeButton.setOnAction(this::commandChangeEvent);

        // Booking Operation Buttons link Click Handler
        createBookingButton.setOnAction(this::addBooking);
        createUpdateButton.setOnAction(this::updateBooking);
        createDeleteButton.setOnAction(this::deleteBooking);

        // Connection State Button
        currentConnect.setOnAction(this::serverConnectButton);
        currentDisconnect.setOnAction(this::serverDisconnect);
        currentDisconnect.setVisible(false);

        // Add Date Time Restrictions
        addDateTimeRestrictions();

        // Query Server for All Current Bookings
        queryCurrentBookings();
    }

    /*
     * Send Server Request with Class Request
     */
    private String serverRequest(Request request) {
        try {
            // Create New Connection if not connected
            if (socket == null || socket.isClosed()) {
                // Create New Socket on Port 5555
                socket = new Socket("localhost", 5555);
                // Create Object Output Stream for Client-Server Communication
                os = new ObjectOutputStream(socket.getOutputStream());
                // Create Scanner Input Class for Server-Client Communication
                in = new Scanner(socket.getInputStream());
            }
            // Indicate Connection State
            currentConnect.setVisible(false);
            currentDisconnect.setVisible(true);

            // Send Object to Server for Requests Standardization
            os.writeObject(request);

            // Initialize Empty String
            String message = "";
            // Print All String Information in String
            while (in.hasNext()) {
                // Retrieve String Message
                String temp = in.nextLine();
                if (temp.contains("END")) {
                    break;
                }
                message += temp + "\n";
            }

            // Return Message
            return message;
        } catch (IOException ex) {
            actionErrorAlert("I faced some connection issues. Do we have an active network working?");
        }

        return null;
    }

    /*
     * init Connection to Server Button Action
     */
    private void serverConnectButton(ActionEvent event) {
        currentConnect.setVisible(false);
        currentDisconnect.setVisible(true);
        queryCurrentBookings();
    }

    /*
     * Disconnect Connection to Server
     */
    private void serverDisconnect(ActionEvent event) {
        try {
            // Indicate Connection State
            currentConnect.setVisible(true);
            currentDisconnect.setVisible(false);

            // Close Output Stream to free up resources
            os.close();
            // Close Input Stream to free up resources
            in.close();
            // Close Connection with Server to free up server buffers
            socket.close();

        } catch (IOException ex) {
            actionErrorAlert("I faced some connection issues. Do we have an active network working?");
        }
    }

    /*
     * Retrieve Booking Information Entered by the User in GUI Fields
     */
    private void populateBookingFields() throws IllegalArgumentException {
        // Retrieve Client ID from GUI Input Field
        clientID = Integer.parseInt((createClientSelect.getText()));
        // Retrieve Personal Trainer ID from GUI Input Field
        PTID = Integer.parseInt((createPTSelect.getText()));
        // Retrieve Date from GUI Input Field
        date = Date.valueOf(createDateSelect.getValue());
        System.out.println(date);
        // Retrieve Start Time from GUI Input Field
        startTime = Time.valueOf(createStartHours.getSelectionModel().getSelectedItem().toString() + ":"
                + createStartMinutes.getSelectionModel().getSelectedItem().toString() + ":00");
        // Retrieve End Time from GUI Input Field
        endTime = Time.valueOf(createEndHours.getSelectionModel().getSelectedItem().toString() + ":"
                + createEndMinutes.getSelectionModel().getSelectedItem().toString() + ":00");
        // Retrieve Focus ID from GUI Input Field
        focus = Integer.parseInt((createFocusText.getText()));

        // Create Current Date & Time Information for Selection Comparison
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();

        // Compare Current Date Time vs Selected to verify bookings are not in the past
        if (Date.valueOf(localDate).equals(date)) {
            if (Integer.parseInt(createStartHours.getSelectionModel().getSelectedItem().toString()) <= localTime
                    .getHour()
                    || Integer.parseInt(createEndHours.getSelectionModel().getSelectedItem().toString()) <= localTime
                            .getHour()) {
                if (Integer.parseInt(createStartMinutes.getSelectionModel().getSelectedItem().toString()) < localTime
                        .getMinute()
                        || Integer.parseInt(createEndMinutes.getSelectionModel().getSelectedItem()
                                .toString()) < localTime.getMinute()) {
                    throw new IllegalArgumentException();
                }
            }
        }

        // Compare Difference between Start Time & End Time for Verification
        int diff = endTime.compareTo(startTime);
        // Throw Error if Time is incorrect
        if (diff < 0 || diff == 0) {
            // Throw Illegal Argument
            throw new IllegalArgumentException();
        }

    }

    /*
     * Include Date & Time Restrictions to disallow Previous Days
     */
    private void addDateTimeRestrictions() {

        // Date Restrictions to stop past dates bookings
        createDateSelect.setValue(LocalDate.now());
        createDateSelect.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        // Link Hours Fields
        ObservableList startHour = createStartHours.getItems();
        ObservableList endHour = createEndHours.getItems();

        // Create Hour Entries in Fields
        for (int i = 7; i < 24; i++) {
            startHour.add(i);
            endHour.add(i);
        }

        // Link Minutes Fields
        ObservableList startMin = createStartMinutes.getItems();
        ObservableList endMin = createEndMinutes.getItems();

        // Create Minute Entries
        for (int i = 0; i < 60; i += 15) {
            startMin.add(i);
            endMin.add(i);
        }

    }

    /*
     * Retrieve Search Query Information from the Graphical User Interface
     */
    private int getSearchQuery() {
        // Convert String to Integer for Validation
        int query = Integer.parseInt(currentSearchText.getText());
        return query;
    }

    /*
     * Send Request to Server via Request Object, Handle & Process Responses in the
     * Graphical User Interface
     */
    private void serverSearchQuery(String command, int query) {
        // Clear Current Bookings Screen
        clearCurrentBookings();

        // Create Class for Client-Server Communication
        Request request = new Request(command, query);

        // Retrieve String Information in Scanner Class
        String res = serverRequest(request);

        // Output Response in Main Text Area
        mainTextArea.setText(mainTextArea.getText() + res + "\n");
    }

    /*
     * Send Request to Server with Date based Query, Handle & Process Responses in
     * the Graphical User Interface
     */
    private void serverSearchQueryDate(String command, Date query) {
        // Clear Current Bookings Screen
        // clearCurrentBookings();
        // Create Class for Client-Server Communication
        Request request = new Request(command);
        // Save Date Information in Class
        request.saveDate(query);
        // Retrieve String Information in Scanner Class
        String res = serverRequest(request);

        // Output Response in Main Text Area
        mainTextArea.setText(res + "\n");
    }

    /*
     * Reset Bookings Button Action Handler
     */
    private void resetBookingsScreen(ActionEvent event) {
        // Clear Current Booking Screen
        clearCurrentBookings();
        // Fetch All Bookings from Server
        queryCurrentBookings();
    }

    /*
     * Clears Bookings Text Area
     */
    private void clearCurrentBookings() {
        // Clear Current Search Query Text
        currentSearchText.setText("");
        // Clear Main Bookings Area Text
        mainTextArea.setText("");
    }

    /*
     * Handles Views & Buttons Visibility Depending on State Selected by User
     */
    private void commandChangeEvent(Event event) {
        // Check Selection Index
        int index = createTypeButton.getSelectionModel().getSelectedIndex();

        // Flags for Easier State Management
        boolean createButton = false;
        boolean updateDeleteButtons = false;

        // Conditional State Handling
        if (index == 0) {
            // Indicate New Booking Button Visibility
            createButton = true;
        } else {
            // Show Text Input Dialog to check Booking ID
            TextInputDialog dialog = new TextInputDialog();
            // Set Dialog Title
            dialog.setTitle("Update/Delete Bookings");
            // Set Dialog Header Text
            dialog.setHeaderText("Enter Booking ID");
            // Set Dialog Content Text
            dialog.setContentText("Please enter the booking ID to be updated/deleted:");
            // Show Dialog
            Optional<String> result = dialog.showAndWait();

            // Check for Input
            if (result.isPresent()) {
                try {
                    // Retrieve Booking ID
                    bookingID = Integer.parseInt(result.get());
                    // Clear Booking Screen
                    clearCurrentBookings();
                    // Set Search Query Text
                    currentSearchText.setText(Integer.toString(bookingID));
                    // Query Booking Information
                    queryCurrentBookingsByID(bookingID);

                    // Indicate Update & Delete Booking Button Visibility
                    updateDeleteButtons = true;
                } catch (NumberFormatException ex) {
                    // Throw Error if Booking ID is not a number
                    actionErrorAlert("Please enter valid Booking ID");
                }
            } else {
                // Indicate New Booking Button Visibility
                createButton = false;
            }
        }

        // Set Buttons Visibility
        createBookingButton.setVisible(createButton);
        createUpdateButton.setVisible(updateDeleteButtons);
        createDeleteButton.setVisible(updateDeleteButtons);
    }

    /*
     * Retrieve All Bookings from Server Action Handler
     */
    private void queryCurrentBookings() {
        // Send LISTALL Query
        serverSearchQuery("LISTALL", 0);
    }

    /*
     * Search Booking by Booking ID Action Handler
     */
    private void queryCurrentBookingsByID(int id) {
        // Send LISTID Query
        serverSearchQuery("LISTID", id);
    }

    /*
     * Search Bookings by Client ID Action Handler
     */
    private void queryCurrentBookingsByClient(ActionEvent event) {
        try {
            // Send LISTCLIENT Query
            serverSearchQuery("LISTCLIENT", getSearchQuery());
        } catch (NumberFormatException ex) {
            // Throw Error if ID is not a number
            actionErrorAlert("Please enter a valid ID!");
        }
    }

    /*
     * Search Bookings by Personal Trainer ID Action Handler
     */
    private void queryCurrentBookingsByPT(ActionEvent event) {
        try {
            // Send LISTPT Query
            serverSearchQuery("LISTPT", getSearchQuery());
        } catch (NumberFormatException ex) {
            // Throw Error if ID is not a number
            actionErrorAlert("Please enter a valid ID!");
        }
    }

    /*
     * Search Bookings by Date Action Handler
     */
    private void queryCurrentBookingsByDate(ActionEvent event) {
        try {
            // Send LISTDAY Query
            serverSearchQueryDate("LISTDAY", Date.valueOf(currentSearchText.getText()));
        } catch (NumberFormatException ex) {
            // Throw Error if ID is not a number
            actionErrorAlert("Please enter a valid date!");
        }
    }

    /*
     * Conditional State Handler decides whether to show Success or Failure Alert
     * Boxes
     */
    private void actionStateHandler(String message) {
        // Condition State Routing
        if (message.contains("Success")) {
            // Show Success Message
            actionSuccessAlert(message);
        } else {
            // Show Error Message
            actionErrorAlert(message);
        }
    }

    /*
     * Displaying Success Alert Box to User
     */
    private void actionSuccessAlert(String message) {
        // Create Information Alert Box & Display
        Alert alert = new Alert(AlertType.INFORMATION);
        // Set Alert Title
        alert.setTitle("Success");
        // Set Alert Header Text
        alert.setHeaderText("Copy That! Operation blackhawk is a success.");
        // Set Alert Content Text
        alert.setContentText(message);
        // Display Alert Box & Wait until Dismissed
        alert.showAndWait();
    }

    /*
     * Displaying Error Alert Box to User
     */
    private void actionErrorAlert(String message) {
        // Create Error Alert Box & Display
        Alert alert = new Alert(AlertType.ERROR);
        // Set Alert Title
        alert.setTitle("Error");
        // Set Alert Header Text
        alert.setHeaderText("Ooops Houston, there is a problem!");
        // Set Alert Content Text
        alert.setContentText(message);
        // Display Alert Box & Wait until Dismissed
        alert.showAndWait();
    }

    /*
     * Send Add Booking Request to Server with Required Details & Handle Responses
     */
    private void addBooking(ActionEvent event) {
        try {
            // Populate Booking Fields from GUI
            populateBookingFields();
            // Create class for Client-Server Communication
            Request request = new Request("ADD");
            // Populate Class with Details
            request.setAdditionalData(clientID, PTID, date, startTime, endTime, focus);
            // Send Request to Server
            String response = serverRequest(request);
            // Handle Response Conditionally
            actionStateHandler(response);
        } catch (IllegalArgumentException ex) {
            actionErrorAlert("Start Time & End Time entered is incorrect");
        }
    }

    /*
     * Send Update Booking Request to Server with Required Details & Handle
     * Responses
     */
    private void updateBooking(ActionEvent event) {
        try {
            // Populate Booking Fields from GUI
            populateBookingFields();
            // Create class for Client-Server Communication
            Request request = new Request("UPDATE", bookingID);
            // Populate Class with Details
            request.setAdditionalData(clientID, PTID, date, startTime, endTime, focus);
            // Send Request to Server
            String response = serverRequest(request);
            // Handle Response Conditionally
            actionStateHandler(response);
        } catch (IllegalArgumentException ex) {
            // Throw Error via Alert Box
            actionErrorAlert("Start Time & End Time mentioned is incorrect");
        }
    }

    /*
     * Send Delete Booking Request to Server with Required Details & Handle
     * Responses
     */
    private void deleteBooking(ActionEvent event) {
        try {
            // Create class for Client-Server Communication
            Request request = new Request("DELETE", bookingID);
            // Send Request to Server
            String response = serverRequest(request);
            // Handle Response Conditionally
            actionStateHandler(response);
        } catch (Exception ex) {
            // Throw Error via Alert Box
            actionErrorAlert("Error Deleting Booking");
        }
    }

}
