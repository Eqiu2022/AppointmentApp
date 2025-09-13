package project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentTest {
    private Appointment appointment;
    @BeforeEach
    public void setUp() {
        LocalDate start = LocalDate.of(2024, 11, 28);
        LocalDate end = LocalDate.of(2024, 11, 30);
        appointment = new DailyAppointment("test", start, end);
    }

    @Test
    public void testBetweenStartAndEnd() {
        LocalDate date = LocalDate.of(2024, 11, 29);
        boolean actual = appointment.occursOn(date);
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void testBeforeStart() {
        LocalDate date = LocalDate.of(2024, 11, 1);
        boolean actual = appointment.occursOn(date);
        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    public void testAfterEnd() {
        LocalDate date = LocalDate.of(2024, 12, 29);
        boolean actual = appointment.occursOn(date);
        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    public void testOnStart() {
        LocalDate date = LocalDate.of(2024, 11, 28);
        boolean actual = appointment.occursOn(date);
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void testOnEnd() {
        LocalDate date = LocalDate.of(2024, 11, 30);
        boolean actual = appointment.occursOn(date);
        boolean expected = true;
        assertEquals(expected, actual);
    }
}

class OneTimeAppointmentTest {

    @Test
    public void testConstructor() {
        LocalDate date = LocalDate.of(2024, 11, 25);
        OneTimeAppointment appointment = new OneTimeAppointment("One time appointment", date);
        assertEquals(date, appointment.getStartDate());
        assertEquals(date, appointment.getEndDate());
    }

    @Test
    public void testOccursOnExactDate() {
        LocalDate date = LocalDate.of(2024, 11, 25);
        OneTimeAppointment appointment = new OneTimeAppointment("One time appointment", date);
        assertTrue(appointment.occursOn(date));
    }

    @Test
    public void testOccursOnBeforeDate() {
        LocalDate date = LocalDate.of(2024, 11, 25);
        OneTimeAppointment appointment = new OneTimeAppointment("One time appointment", date);
        LocalDate beforeDate = LocalDate.of(2024, 11, 24);
        assertFalse(appointment.occursOn(beforeDate));
    }

    @Test
    public void testOccursOnAfterDate() {
        LocalDate date = LocalDate.of(2024, 11, 25);
        OneTimeAppointment appointment = new OneTimeAppointment("One time appointment", date);
        LocalDate afterDate = LocalDate.of(2024, 11, 26);
        assertFalse(appointment.occursOn(afterDate));
    }
}

class DailyAppointmentTest {

    @Test
    public void testOccursOnWithinRange() {
        LocalDate start = LocalDate.of(2024, 11, 25);
        LocalDate end = LocalDate.of(2024, 11, 27);
        DailyAppointment appointment = new DailyAppointment("Daily appointment", start, end);
        assertTrue(appointment.occursOn(LocalDate.of(2024, 11, 26)));
    }

    @Test
    public void testOccursOnOutsideRange() {
        LocalDate start = LocalDate.of(2024, 11, 25);
        LocalDate end = LocalDate.of(2024, 11, 27);
        DailyAppointment appointment = new DailyAppointment("Daily appointment", start, end);
        assertFalse(appointment.occursOn(LocalDate.of(2024, 11, 28)));
    }
}

class MonthlyAppointmentTest {

    @Test
    public void testOccursOnSameDayOfMonth() {
        LocalDate start = LocalDate.of(2024, 11, 25);
        LocalDate end = LocalDate.of(2025, 3, 25);
        MonthlyAppointment appointment = new MonthlyAppointment("Monthly appointment", start, end);
        assertTrue(appointment.occursOn(LocalDate.of(2024, 12, 25)));  // Same day of the next month
    }

    @Test
    public void testOccursOnDifferentDayOfMonth() {
        LocalDate start = LocalDate.of(2024, 11, 25);
        LocalDate end = LocalDate.of(2025, 3, 25);
        MonthlyAppointment appointment = new MonthlyAppointment("Monthly appointment", start, end);
        assertFalse(appointment.occursOn(LocalDate.of(2024, 12, 26)));  // Different day of the month
    }

    @Test
    public void testOccursOnOutsideRange() {
        LocalDate start = LocalDate.of(2024, 11, 25);
        LocalDate end = LocalDate.of(2025, 3, 25);
        MonthlyAppointment appointment = new MonthlyAppointment("Monthly appointment", start, end);
        assertFalse(appointment.occursOn(LocalDate.of(2025, 4, 25)));  // Outside end date
    }
}

class AppointmentComparisonTest {

    @Test
    public void testSortingAppointments() {
        OneTimeAppointment onetime = new OneTimeAppointment("One time", LocalDate.of(2024, 11, 24));
        DailyAppointment daily = new DailyAppointment("Daily", LocalDate.of(2024, 11, 20), LocalDate.of(2024, 11, 25));
        MonthlyAppointment monthly = new MonthlyAppointment("Monthly", LocalDate.of(2024, 9, 25), LocalDate.of(2025, 12, 25));

        Appointment[] appointments = { onetime, daily, monthly };
        Arrays.sort(appointments);

        assertEquals(monthly, appointments[0]);
        assertEquals(daily, appointments[1]);
        assertEquals(onetime, appointments[2]);
    }

    @Test
    public void testEquals() {
        OneTimeAppointment appointment1 = new OneTimeAppointment("One time", LocalDate.of(2024, 11, 25));
        OneTimeAppointment appointment2 = new OneTimeAppointment("One time", LocalDate.of(2024, 11, 25));
        assertEquals(appointment1, appointment2);
    }

    @Test
    public void testNotEquals() {
        OneTimeAppointment appointment1 = new OneTimeAppointment("One time", LocalDate.of(2024, 11, 25));
        OneTimeAppointment appointment2 = new OneTimeAppointment("Different event", LocalDate.of(2024, 11, 25));
        assertNotEquals(appointment1, appointment2);
    }
}

