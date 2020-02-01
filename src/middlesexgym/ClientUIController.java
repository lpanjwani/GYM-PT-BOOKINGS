/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlesexgym;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import java.sql.Time;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;

/**
 *
 * @author LaveshPanjwani
 */
public class ClientUIController extends ClientUIView {

    private int actionID;
    private int clientID;
    private int PTID;
    private Date date;
    private Time startTime;
    private Time endTime;
    private int focus;

    public ClientUIController() {
        super();

        // Current Bookings Section
        currentClientSearchButton.setOnAction(this::queryCurrentBookingsByClient);
        currentPTSearchButton.setOnAction(this::queryCurrentBookingsByPT);
        currentDateSearchButton.setOnAction(this::queryCurrentBookingsByDate);
        currentResetButton.setOnAction(this::resetBookingsScreen);

        // Booking Actions Section
        createTypeButton.getItems().add("Add Booking");
        createTypeButton.getItems().add("Update/Delete Booking");
        createTypeButton.setOnAction(this::commandChangeEvent);

        createBookingButton.setOnAction(this::addBooking);

        // Query Server for All Current Bookings
        queryCurrentBookings();
    }

    private Scanner serverRequest(BackendRequest request) {
        try {
            Socket socket = new Socket("localhost", 5555);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            Scanner in = new Scanner(socket.getInputStream());

            os.writeObject(request);

            return in;

        } catch (IOException ex) {
            Logger.getLogger(ClientUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /* Fetch Booking Information from GUI Fields */
    private void populateBookingFields() throws IllegalArgumentException {
        // Retrieve Client ID from GUI Input Field
        clientID = Integer.parseInt((createClientSelect.getText()));
        // Retrieve Personal Trainer ID from GUI Input Field
        PTID = Integer.parseInt((createPTSelect.getText()));
        // Retrieve Date from GUI Input Field
        date = Date.valueOf(createDateSelect.getValue());
        // Retrieve Start Time from GUI Input Field
        startTime = Time.valueOf(createStartTimeText.getText() + ":00");
        // Retrieve End Time from GUI Input Field
        endTime = Time.valueOf(createEndTimeText.getText() + ":00");
        // Retrieve Focus ID from GUI Input Field
        focus = Integer.parseInt((createFocusText.getText()));

        int diff = endTime.compareTo(startTime);
        if (diff < 0 || diff == 0) {
            throw new IllegalArgumentException();
        }

    }

    private int getSearchQuery() {
        int query = Integer.parseInt(currentSearchText.getText());
        return query;
    }

    private void serverSearchQuery(String command, int query) {
        clearCurrentBookings();
        BackendRequest request = new BackendRequest(command, query);
        Scanner res = serverRequest(request);

        while (res.hasNext()) {
            mainTextArea.setText(mainTextArea.getText() + res.nextLine() + "\n");
        }
        ;
    }

    private void serverSearchQueryDate(String command, Date query) {
        clearCurrentBookings();
        BackendRequest request = new BackendRequest(command);
        request.saveDate(query);
        Scanner res = serverRequest(request);

        while (res.hasNext()) {
            mainTextArea.setText(mainTextArea.getText() + res.nextLine() + "\n");
        }
        ;
    }

    public void resetBookingsScreen(ActionEvent event) {
        clearCurrentBookings();
        queryCurrentBookings();
    }

    private void clearCurrentBookings() {
        currentSearchText.setText("");
        mainTextArea.setText("");
    }

    private void commandChangeEvent(Event event) {
        int index = createTypeButton.getSelectionModel().getSelectedIndex();

        boolean createButton = false;
        boolean updateDeleteButtons = false;

        if (index == 0) {
            createButton = true;
        } else {

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Update/Delete Bookings");
            dialog.setHeaderText("Enter Booking ID");
            dialog.setContentText("Please enter the booking ID to be updated/deleted:");
            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                try {
                    actionID = Integer.parseInt(result.get());
                    clearCurrentBookings();
                    currentSearchText.setText(Integer.toString(actionID));
                    queryCurrentBookingsByID(actionID);

                    updateDeleteButtons = true;
                } catch (NumberFormatException ex) {
                }
            } else {
                createButton = false;
            }

        }

        createBookingButton.setVisible(createButton);
        createUpdateButton.setVisible(updateDeleteButtons);
        createDeleteButton.setVisible(updateDeleteButtons);
    }

    private void queryCurrentBookings() {
        serverSearchQuery("LISTALL", 0);
    }

    private void queryCurrentBookingsByID(int id) {
        serverSearchQuery("LISTID", id);
    }

    private void queryCurrentBookingsByClient(ActionEvent event) {
        try {
            serverSearchQuery("LISTPT", getSearchQuery());
        } catch (NumberFormatException ex) {
            actionErrorAlert("Please enter a valid ID!");
        }
    }

    private void queryCurrentBookingsByPT(ActionEvent event) {
        try {
            serverSearchQuery("LISTCLIENT", getSearchQuery());
        } catch (NumberFormatException ex) {
            actionErrorAlert("Please enter a valid ID!");
        }
    }

    private void queryCurrentBookingsByDate(ActionEvent event) {
        try {
            serverSearchQueryDate("LISTDAY", Date.valueOf(currentSearchText.getText()));
        } catch (NumberFormatException ex) {
            actionErrorAlert("Please enter a valid date!");
        }
    }

    public void actionStateHandler(Scanner res) {
        while (res.hasNext()) {
            String message = res.nextLine();
            if (message.contains("Success")) {
                actionSuccessAlert(message);
            } else {
                actionErrorAlert(message);
            }
        }
        ;
    }

    public void actionSuccessAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Copy That! Operation blackhawk is a success.");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void actionErrorAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Ooops Houston, there is a problem!");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /*
     * Send Add Booking Command to Server with Required Details & Handle Responses
     */
    private void addBooking(ActionEvent event) {
        try {
            populateBookingFields();
            BackendRequest request = new BackendRequest("ADD");
            request.setAdditionalData(clientID, PTID, date, startTime, endTime, focus);
            actionStateHandler(serverRequest(request));
        } catch (IllegalArgumentException ex) {
            actionErrorAlert("Start Time & End Time mentioned is incorrect");
        }
    }

    /*
     * Send Update Booking Command to Server with Required Details & Handle
     * Responses
     */
    private void updateBooking() {
        try {
            populateBookingFields();
            BackendRequest request = new BackendRequest("UPDATE", actionID);
            request.setAdditionalData(clientID, PTID, date, startTime, endTime, focus);
            actionStateHandler(serverRequest(request));
        } catch (IllegalArgumentException ex) {
            actionErrorAlert("Start Time & End Time mentioned is incorrect");
        }
    }

    /*
     * Send Delete Booking Command to Server with Required Details & Handle
     * Responses
     */
    private void deleteBooking() {
        try {
            populateBookingFields();
            BackendRequest request = new BackendRequest("DELETE", actionID);
            actionStateHandler(serverRequest(request));
        } catch (Exception ex) {
            // actionErrorAlert("Start Time & End Time mentioned is incorrect");
        }
    }

}
