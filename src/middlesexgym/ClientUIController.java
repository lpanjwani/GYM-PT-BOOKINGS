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

    public ClientUIController() {
        super();

        displayCurrentBookings();

        // Current Bookings Section
        currentClientSearchButton.setOnAction(this::displayCurrentBookingsByClient);
        currentPTSearchButton.setOnAction(this::displayCurrentBookingsByPT);
        currentDateSearchButton.setOnAction(this::displayCurrentBookingsByDate);
        currentResetButton.setOnAction(this::resetCurrentBookings);

        // Booking Actions Section
        createTypeButton.getItems().add("Add Booking");
        createTypeButton.getItems().add("Update/Delete Booking");
        createTypeButton.setOnAction(this::actionTypeChange);
    }

    private Scanner backendLink(BackendRequest req) {
        try {
            Socket socket = new Socket("localhost", 5555);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            Scanner in = new Scanner(socket.getInputStream());

            os.writeObject(req);

            return in;

        } catch (IOException ex) {
            Logger.getLogger(ClientUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void actionTypeChange(Event event) {
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
                    displayCurrentByID(Integer.parseInt(result.get()));
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

    private void displayCurrentBookings() {
        try {
            BackendRequest req = new BackendRequest("LISTALL");
            Scanner res = backendLink(req);

            while (res.hasNext()) {
                mainTextArea.setText(mainTextArea.getText() + res.nextLine() + "\n");
            }
        } catch (Exception ex) {

        }

    }

    private void displayCurrentByID(int id) {
        BackendRequest req = new BackendRequest("LISTID ");
        Scanner res = backendLink(req);

        while (res.hasNext()) {
            mainTextArea.setText(mainTextArea.getText() + res.nextLine() + "\n");
        }
    }

    private void displayCurrentBookingsByClient(ActionEvent event) {
        BackendRequest req = new BackendRequest("LISTPT " + currentSearchText.getText());
        Scanner res = backendLink(req);

        while (res.hasNext()) {
            mainTextArea.setText(mainTextArea.getText() + res.nextLine() + "\n");
        }
    }

    private void displayCurrentBookingsByPT(ActionEvent event) {
        BackendRequest req = new BackendRequest("LISTCLIENT " + currentSearchText.getText());
        Scanner res = backendLink(req);

        while (res.hasNext()) {
            mainTextArea.setText(mainTextArea.getText() + res.nextLine() + "\n");
        }
    }

    private void displayCurrentBookingsByDate(ActionEvent event) {
        BackendRequest req = new BackendRequest("LISTDAY " + currentSearchText.getText());
        Scanner res = backendLink(req);

        while (res.hasNext()) {
            mainTextArea.setText(mainTextArea.getText() + res.nextLine() + "\n");
        }
    }

    private void resetCurrentBookings(ActionEvent event) {
        currentSearchText.setText("");
        mainTextArea.setText("");
        displayCurrentBookings();
    }

    private void addBookings() {
        try {
            // Client ID
            int clientID = Integer.parseInt((createClientSelect.getText()));
            int PTID = Integer.parseInt((createPTSelect.getText()));
            Date date = Date.valueOf(createDateSelect.getValue());
            Time startTime = Time.valueOf(createStartTimeText.getText());
            Time endTime = Time.valueOf(createEndTimeText.getText());
            int focus = Integer.parseInt((createFocusText.getText()));

            BackendRequest req = new BackendRequest("ADD  ");
            // BackendRequest.setAdditionalData(clientID, PTID);
            Scanner res = backendLink(req);
        } catch (Exception ex) {

        }
    }

    private void updateBooking() {
        try {
            // Client ID
            int clientID = Integer.parseInt((createClientSelect.getText()));
            int PTID = Integer.parseInt((createPTSelect.getText()));
            Date date = Date.valueOf(createDateSelect.getValue());
            Time startTime = Time.valueOf(createStartTimeText.getText());
            Time endTime = Time.valueOf(createEndTimeText.getText());
            int focus = Integer.parseInt((createFocusText.getText()));

            BackendRequest req = new BackendRequest("UPDATE  ");
            // BackendRequest.setAdditionalData(clientID, PTID);
            Scanner res = backendLink(req);
        } catch (Exception ex) {

        }
    }

    private void deleteBookings() {
        try {
            // Client ID
            int clientID = Integer.parseInt((createClientSelect.getText()));
            int PTID = Integer.parseInt((createPTSelect.getText()));
            Date date = Date.valueOf(createDateSelect.getValue());
            Time startTime = Time.valueOf(createStartTimeText.getText());
            Time endTime = Time.valueOf(createEndTimeText.getText());
            int focus = Integer.parseInt((createFocusText.getText()));

            BackendRequest req = new BackendRequest("DELETE  ");
            // BackendRequest.setAdditionalData(clientID, PTID);
            Scanner res = backendLink(req);
        } catch (Exception ex) {

        }
    }
}
