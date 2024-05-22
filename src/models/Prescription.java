package models;

import java.util.List;

public class Prescription {
    private int id;
    private int consultationId;
    private String medications;
    private String dosage;
    private String duration;

    public Prescription(int consultationId, String medications, String dosage, String duration) {
        this.consultationId = consultationId;
        this.medications = medications;
        this.dosage = dosage;
        this.duration = duration;
    }

    public Prescription(int id, int consultationId, String medications, String dosage, String duration) {
        this.id = id;
        this.consultationId = consultationId;
        this.medications = medications;
        this.dosage = dosage;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(int consultationId) {
        this.consultationId = consultationId;
    }

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
