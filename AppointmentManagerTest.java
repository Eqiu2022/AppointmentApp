package project;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Comparator;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class AppointmentManagerTest {
	private Appointment appointment;
	private AppointmentManager manager;
	@BeforeEach
	public void setUp() {
		
		
		LocalDate start = LocalDate.of(2024, 11, 28);
		LocalDate end = LocalDate.of(2024, 11, 30);
		appointment = new DailyAppointment("test", start, end);
	}
	@Test
    public void testAddAppointment() {
		manager = new AppointmentManager();
		manager.add(appointment);
		
		
		Exception exception = assertThrows(Exception.class, () -> manager.add(appointment));
		assertEquals("Appointment already exists!", exception.getMessage());
	}
	
	@Test
    public void testDeleteAppointment() {
		manager = new AppointmentManager();
		manager.add(appointment);
		manager.delete(appointment);
		
		Exception exception = assertThrows(Exception.class, () -> manager.delete(appointment));
		assertEquals("Appointment does not exist!", exception.getMessage());
	}
	
	@Test
    public void testUpdateAppointment() {
		manager = new AppointmentManager();
		Appointment firstAppointment = new DailyAppointment("test", LocalDate.of(2000, 1, 1), LocalDate.of(2000, 1, 2));
		manager.add(firstAppointment);
		
		manager.update(firstAppointment, appointment);
		Exception exception = assertThrows(Exception.class, () -> manager.add(appointment));
		assertEquals("Appointment already exists!", exception.getMessage());
	}
	
	@Test
    public void testGetAppointmentsOn() {
		Appointment[] appointmentsExpected = {appointment};
		
		Comparator<Appointment> byDate = Comparator.comparing(p -> p.getStartDate());
		
		manager = new AppointmentManager();
		manager.add(appointment);
		manager.add(new DailyAppointment("test", LocalDate.of(2024, 11, 28), LocalDate.of(2024, 11, 28)));
		Appointment[] appointmentsActual = manager.getAppointments(LocalDate.of(2024, 11, 28), byDate);
		assertEquals(appointmentsExpected,appointmentsActual);
	}
}
