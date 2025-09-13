package project;

import java.time.LocalDate;

public class DailyAppointment extends Appointment {

    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    public DailyAppointment(String description, LocalDate startDate, LocalDate endDate) {
        super(description, startDate, endDate);
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return inBetween(date);
    }

    @Override
    public String toString() {
        return "(daily) " + super.toString();
    }
}
