package models;

public class Appointment {
    private int id;
    private String dateTime;
    private String state;
    private int patientId;
    private int doctorId;
    private  String time;

    public Appointment(String dateTime,String state, int patientId, int doctorId, String time) {
        this.dateTime = dateTime;
        this.state = state;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.time = time;
    }

    public Appointment(int id, String dateTime,String state, int patientId, int doctorId, String time) {
        this.id = id;
        this.dateTime = dateTime;
        this.state = state;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
}
