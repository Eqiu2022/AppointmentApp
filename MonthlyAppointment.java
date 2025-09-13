package project;

import java.time.LocalDate;

public class MonthlyAppointment extends Appointment {

    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    public MonthlyAppointment(String description, LocalDate startDate, LocalDate endDate) {
        super(description, startDate, endDate);
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return inBetween(date) && getStartDate().getDayOfMonth() == date.getDayOfMonth();
    }

    @Override
    public String toString() {
        return "(monthly) " + super.toString();
    }
}