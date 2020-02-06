/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlesexgym;

import java.sql.Date;
import java.sql.Time;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author LaveshPanjwani
 */
public class BookingActionsTest {

    private BookingActions instance;

    public BookingActionsTest() {
    }

    @Before
    public void setUp() {
        this.instance = new BookingActions();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of newBooking method, of class BookingActions.
     */
    @Test
    public void testNewBooking() {
        // Create class for Client-Server Communication
        Request request = new Request("ADD");
        // Populate Class with Details
        request.setAdditionalData(1, 1, Date.valueOf("2020-03-01"), Time.valueOf("10:00:00"), Time.valueOf("12:00:00"),
                1);

        String expSuccessResult = "Success - Booking Successfully Created";
        String successResult = instance.newBooking(request);
        assertEquals(expSuccessResult, successResult);

        String expFailureResult = "Error - Conflicting Booking Exists";
        String failureResult = instance.newBooking(request);
        assertEquals(expFailureResult, failureResult);
    }

    /**
     * Test of updateBooking method, of class BookingActions.
     */
    @Test
    public void testUpdateBooking() {
        // Create class for Client-Server Communication
        Request request = new Request("UPDATE", 5);
        // Populate Class with Details
        request.setAdditionalData(1, 2, Date.valueOf("2020-02-02"), Time.valueOf("16:35:00"), Time.valueOf("18:00:00"),
                1);

        String expResult = "Success - Booking Successfully Updated";
        String result = instance.updateBooking(request);
        assertEquals(expResult, result);
    }

    /**
     * Test of deleteBooking method, of class BookingActions.
     */
    @Test
    public void testDeleteBooking() {
        // Create class for Client-Server Communication
        Request request = new Request("DELETE", 5);

        String expResult = "Success - Booking Successfully Deleted";
        String result = instance.deleteBooking(request);
        assertEquals(expResult, result);
    }

}
