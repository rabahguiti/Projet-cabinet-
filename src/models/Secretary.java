package models;

public class Secretary {
    private int id;
    private String name;

    public Secretary(String name) {
        this.name = name;
    }

    public Secretary(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void scheduleAppointment() {
        // Method implementation
    }

    public void managePatientInfo() {
        // Method implementation
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
