package models;

import java.util.List;

public class MedicalRecord {
    private int id;
    private int patientId;
    private int consultationId;
    private List<String> prescriptions;
    private String medicalHistory;

    public MedicalRecord(int patientId, int consultationId, List<String> prescriptions, String medicalHistory) {
        this.patientId = patientId;
        this.consultationId = consultationId;
        this.prescriptions = prescriptions;
        this.medicalHistory = medicalHistory;
    }

    public MedicalRecord(int id, int patientId, int consultationId, List<String> prescriptions, String medicalHistory) {
        this.id = id;
        this.patientId = patientId;
        this.consultationId = consultationId;
        this.prescriptions = prescriptions;
        this.medicalHistory = medicalHistory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(int consultationId) {
        this.consultationId = consultationId;
    }

    public List<String> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<String> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
}
