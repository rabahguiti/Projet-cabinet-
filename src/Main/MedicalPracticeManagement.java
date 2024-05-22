package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MedicalPracticeManagement {
    private Map<Integer, MedicalRecord> medicalRecords;
    private Map<Integer, Appointment> appointments;
    private Map<Integer, Patient> patients;
    private int recordCounter;
    private int appointmentCounter;

    public MedicalPracticeManagement() {
        this.medicalRecords = new HashMap<>();
        this.appointments = new HashMap<>();
        this.patients = new HashMap<>();
        this.recordCounter = 0;
        this.appointmentCounter = 0;
    }

    public void addPatient(String name, String firstName, String phone) {
        Patient patient = new Patient(name, firstName, phone);
        patients.put(patients.size() + 1, patient);
    }

    public void addMedicalRecord(String patientName) {
        MedicalRecord medicalRecord = new MedicalRecord(++recordCounter, patientName);
        medicalRecords.put(recordCounter, medicalRecord);
    }

    public void addMedicationToRecord(int recordId, String medication) {
        MedicalRecord medicalRecord = medicalRecords.get(recordId);
        if (medicalRecord != null) {
            medicalRecord.addMedication(medication);
        }
    }

    public void addCertificateToRecord(int recordId, String certificate) {
        MedicalRecord medicalRecord = medicalRecords.get(recordId);
        if (medicalRecord != null) {
            medicalRecord.setCertificate(certificate);
        }
    }

    public void scheduleAppointment(String patientName, String date, String time) {
        Appointment appointment = new Appointment(++appointmentCounter, patientName, date, time);
        appointments.put(appointmentCounter, appointment);
    }

    // Other methods for managing appointments, patients, and medical records

//    public static void main(String[] args) {
//        MedicalPracticeManagement practiceManagement = new MedicalPracticeManagement();
//        // Implement menu and user interface
//    }
}




class MedicalRecord {
    private int recordId;
    private String patientName;
    private List<String> medications;
    private String certificate;

    public MedicalRecord(int recordId, String patientName) {
        this.recordId = recordId;
        this.patientName = patientName;
        this.medications = new ArrayList<>();
    }

    // Getters and setters

    public void addMedication(String medication) {
        medications.add(medication);
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    // Other relevant methods
}

class Appointment {
    private int appointmentId;
    private String patientName;
    private String date;
    private String time;

    public Appointment(int i, String patientName, String date, String time) {
    }

    // Constructor, getters, and setters
}

class Patient {
    private String name;
    private String firstName;
    private String phone;

    public Patient(String name, String firstName, String phone) {
        this.name = name;
        this.firstName = firstName;
        this.phone = phone;
    }
// Constructor, getters, and setters
}
