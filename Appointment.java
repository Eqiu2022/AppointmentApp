package project;

import java.time.LocalDate;

public abstract class Appointment implements Comparable<Appointment> {
    //Class Variables
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    //Setters and Getters
    public String getDescription() {
        return description;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    //Constructor
    public Appointment(String description, LocalDate startDate, LocalDate endDate) {
        super();
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    protected boolean inBetween(LocalDate date) {
        return ((startDate.isBefore(date) || startDate.isEqual(date)) && (endDate.isAfter(date) || endDate.isEqual(date)));
    }

    public abstract boolean occursOn(LocalDate date);

    @Override
    public int compareTo(Appointment o) {
        if (!startDate.equals(o.startDate)) {
            return startDate.compareTo(o.startDate);
        } else if (!endDate.equals(o.endDate)) {
            return endDate.compareTo(o.endDate);
        } else {
            return description.compareTo(o.description);
        }
    }

    @Override
    public String toString() {
        return String.format("%s: %s - %s", description, startDate, endDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment that)) return false;
        return toString().equals(that.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
