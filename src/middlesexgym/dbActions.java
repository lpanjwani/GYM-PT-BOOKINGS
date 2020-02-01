/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlesexgym;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author LaveshPanjwani
 */
public class dbActions {

    private final dbController db = new dbController();

    private ArrayList<String> bookingsDecode(ResultSet dbRes) throws SQLException {
        ArrayList<String> bookings = new ArrayList<>();

        while (dbRes.next()) {
            String current = ("ID: " + dbRes.getInt("id"));
            current += " Client: " + dbRes.getString("client");
            current += " Trainer: " + dbRes.getString("trainer");
            current += " Date: " + dbRes.getDate("date");
            current += " Start Time: " + dbRes.getTime("startTime");
            current += " End Time: " + dbRes.getTime("endTime");
            current += " Focus: " + dbRes.getString("focus");
            bookings.add(current);
        }

        return bookings;

    }

    public ArrayList getBookings() throws SQLException {
        ResultSet dbRes = db.runQuery("SELECT bookings.id, CONCAT(client.name, '"
                + " (ID ', client.id, ')') AS client, " + "CONCAT(staff.name, ' (ID ', staff.id, ')') AS trainer, "
                + "bookings.date, bookings.startTime, bookings.endTime, "
                + "CONCAT(focus.name, ' (ID ', focus.id, ')') AS focus FROM GYM.bookings "
                + "INNER JOIN client ON client.id = bookings.client "
                + "INNER JOIN staff ON staff.id = bookings.trainer "
                + "INNER JOIN focus ON focus.id = bookings.focus ORDER BY bookings.id DESC;");

        ArrayList<String> bookings = bookingsDecode(dbRes);

        return bookings;
    }

    public ArrayList getBookingsByID(int id) throws SQLException {
        ResultSet dbRes = db.runQuery("SELECT bookings.id, CONCAT(client.name,' (ID ', client.id, ')') AS client, "
                + "CONCAT(staff.name, ' (ID ', staff.id, ')') AS trainer, "
                + "bookings.date, bookings.startTime, bookings.endTime, "
                + "CONCAT(focus.name, ' (ID ', focus.id, ')') AS focus " + "FROM GYM.bookings INNER JOIN client "
                + "ON client.id = bookings.client INNER JOIN staff "
                + "ON staff.id = bookings.trainer INNER JOIN focus ON "
                + "focus.id = bookings.focus WHERE bookings.id = " + id + " ORDER BY bookings.id DESC;");

        ArrayList<String> bookings = bookingsDecode(dbRes);

        return bookings;
    }

    public ArrayList getBookingsByClient(int id) throws SQLException {
        ResultSet dbRes = db.runQuery("SELECT bookings.id, CONCAT(client.name, "
                + "' (ID ', client.id, ')') AS client, " + "CONCAT(staff.name, ' (ID ', staff.id, ')') AS trainer, "
                + "bookings.date, bookings.startTime, bookings.endTime, "
                + "CONCAT(focus.name, ' (ID ', focus.id, ')') AS focus " + "FROM GYM.bookings INNER JOIN client "
                + "ON client.id = bookings.client INNER JOIN staff "
                + "ON staff.id = bookings.trainer INNER JOIN focus ON " + "focus.id = bookings.focus WHERE client.id = "
                + id + " ORDER BY bookings.id DESC;");

        ArrayList<String> bookings = bookingsDecode(dbRes);

        return bookings;
    }

    public ArrayList getBookingsByPT(int id) throws SQLException {

        ResultSet dbRes = db.runQuery("SELECT bookings.id, CONCAT(client.name, "
                + "' (ID ', client.id, ')') AS client, " + "CONCAT(staff.name, ' (ID ', staff.id, ')') AS trainer, "
                + "bookings.date, bookings.startTime, bookings.endTime, "
                + "CONCAT(focus.name, ' (ID ', focus.id, ')') AS focus " + "FROM GYM.bookings INNER JOIN client "
                + "ON client.id = bookings.client INNER JOIN staff "
                + "ON staff.id = bookings.trainer INNER JOIN focus ON " + "focus.id = bookings.focus WHERE staff.id = "
                + id + " ORDER BY bookings.id DESC; ;");

        ArrayList<String> bookings = bookingsDecode(dbRes);

        return bookings;
    }

    public ArrayList getBookingsByDate(Date date) throws SQLException {
        ResultSet dbRes = db.runQuery("SELECT bookings.id, CONCAT(client.name, "
                + "' (ID ', client.id, ')') AS client, CONCAT(staff.name, "
                + "' (ID ', staff.id, ')') AS trainer, bookings.date, "
                + "bookings.startTime, bookings.endTime, CONCAT(focus.name, "
                + "' (ID ', focus.id, ')') AS focus FROM GYM.bookings "
                + "INNER JOIN client ON client.id = bookings.client "
                + "INNER JOIN staff ON staff.id = bookings.trainer " + "INNER JOIN focus ON focus.id = bookings.focus "
                + "WHERE DATE(bookings.date) = " + date + " ORDER BY bookings.id DESC;");

        ArrayList<String> bookings = bookingsDecode(dbRes);

        return bookings;
    }

    public int newBooking(BackendRequest req) {
        int result = db
                .runUpdate("INSERT INTO GYM.bookings (`client`," + "`trainer`,`date`,`startTime`,`endTime`,`focus`) "
                        + "SELECT '" + req.getClient() + "', '" + req.getPT() + "'," + " '" + req.getDate() + "', '"
                        + req.getStartTime() + "'," + " '" + req.getEndTime() + "', '" + req.getFocus() + "' "
                        + "FROM DUAL " + "WHERE NOT EXISTS( SELECT id FROM GYM.bookings " + "WHERE date = '"
                        + req.getDate() + "'" + "AND endTime > '" + req.getStartTime() + "' " + "AND startTime < '"
                        + req.getEndTime() + "' );");

        return result;
    }

}
