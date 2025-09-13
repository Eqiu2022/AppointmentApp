package project;

import java.time.LocalDate;

public class OneTimeAppointment extends Appointment {

    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    public OneTimeAppointment(String description, LocalDate startDate) {
        super(description, startDate, startDate);
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return getStartDate().isEqual(date);
    }

    @Override
    public String toString() {
        return "(onetime) " + super.toString();
    }
}