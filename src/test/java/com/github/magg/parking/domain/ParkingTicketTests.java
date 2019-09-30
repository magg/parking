package com.github.magg.parking.domain;

import com.github.magg.parking.exception.ParkingException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(JUnit4.class)
public class ParkingTicketTests {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void testCreationIssueTime(){
        ParkingTicket ticket = new ParkingTicket();

        assertNotNull(ticket.getIssueTime());
    }


    @Test
    public void testVALIDExitTime() throws InterruptedException {
        ParkingTicket ticket = new ParkingTicket();

        Thread.sleep(100);

        ticket.setExitTime(LocalDateTime.now());


        assertTrue(ticket.getExitTime().isAfter(ticket.getIssueTime()));

    }

    @Test
    public void testInvalidExitTIME(){

        expectedEx.expect(ParkingException.class);
        expectedEx.expectMessage("Parking Ticket exitTime cannot be before issueTime");

        ParkingTicket ticket = new ParkingTicket();

        String str = "1986-04-08 12:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

        ticket.setExitTime(dateTime);

    }
}
