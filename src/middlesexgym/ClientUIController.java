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
        currentResetButton.setOnAction(this::clearCurrentBookings);

        // Booking Actions Section
        createTypeButton.getItems().add("Add Booking");
        createTypeButton.getItems().add("Update/Delete Booking");
        createTypeButton.setOnAction(this::commandChangeEvent);

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
    private void inputBookingFields() {
        // Retrieve Client ID from GUI Input Field
        clientID = Integer.parseInt((createClientSelect.getText()));
        // Retrieve Personal Trainer ID from GUI Input Field
        PTID = Integer.parseInt((createPTSelect.getText()));
        // Retrieve Date from GUI Input Field
        date = Date.valueOf(createDateSelect.getValue());
        // Retrieve Start Time from GUI Input Field
        startTime = Time.valueOf(createStartTimeText.getText());
        // Retrieve End Time from GUI Input Field
        endTime = Time.valueOf(createEndTimeText.getText());
        // Retrieve Focus ID from GUI Input Field
        focus = Integer.parseInt((createFocusText.getText()));
    }

    private int getSearchQuery() {
        int query = Integer.parseInt(currentSearchText.getText());
        return query;
    }

    private void serverSearchQuery(String command, int query) {
        BackendRequest request = new BackendRequest(command, query);
        Scanner res = serverRequest(request);

        clearCurrentBookings();
        while (res.hasNext()) {
            mainTextArea.setText(mainTextArea.getText() + res.nextLine() + "\n");
        }
        ;
    }

    private void serverSearchQueryDate(String command, Date query) {
        BackendRequest request = new BackendRequest(command);
        request.saveDate(query);
        Scanner res = serverRequest(request);

        clearCurrentBookings();
        while (res.hasNext()) {
            mainTextArea.setText(mainTextArea.getText() + res.nextLine() + "\n");
        }
        ;
    }

    private void clearCurrentBookings() {
        currentSearchText.setText("");
        mainTextArea.setText("");
        // queryCurrentBookings();
    }

    private void clearCurrentBookings(ActionEvent event) {
        currentSearchText.setText("");
        mainTextArea.setText("");
        // queryCurrentBookings();
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
        serverSearchQuery("LISTPT", getSearchQuery());
    }

    private void queryCurrentBookingsByPT(ActionEvent event) {
        serverSearchQuery("LISTCLIENT", getSearchQuery());
    }

    private void queryCurrentBookingsByDate(ActionEvent event) {
        serverSearchQueryDate("LISTDAY", Date.valueOf(currentSearchText.getText()));
    }

    /*
     * Send Add Booking Command to Server with Required Details & Handle Responses
     */
    private void addBooking() {
        try {
            BackendRequest request = new BackendRequest("ADD");
            request.setAdditionalData(clientID, PTID, date, startTime, endTime, focus);
            Scanner res = serverRequest(request);
        } catch (Exception ex) {

        }
    }

    /*
     * Send Update Booking Command to Server with Required Details & Handle
     * Responses
     */
    private void updateBooking() {
        try {
            BackendRequest request = new BackendRequest("UPDATE", actionID);
            request.setAdditionalData(clientID, PTID, date, startTime, endTime, focus);
            Scanner res = serverRequest(request);
        } catch (Exception ex) {

        }
    }

    /*
     * Send Delete Booking Command to Server with Required Details & Handle
     * Responses
     */
    private void deleteBooking() {
        try {
            inputBookingFields();
            BackendRequest request = new BackendRequest("DELETE", actionID);
            request.setAdditionalData(clientID, PTID, date, startTime, endTime, focus);
            Scanner res = serverRequest(request);
        } catch (Exception ex) {

        }
    }

}
