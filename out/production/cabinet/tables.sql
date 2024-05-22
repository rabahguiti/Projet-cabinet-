use cabinet;
CREATE TABLE IF NOT EXISTS Patient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    date_of_birth DATE,
    gender VARCHAR(10),
    address VARCHAR(255),
    phone_number VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS Doctor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    specialization VARCHAR(255),
    phone_number VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS Appointment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date_time DATETIME,
    patient_id INT,
    doctor_id INT,
    FOREIGN KEY (patient_id) REFERENCES Patient(id),
    FOREIGN KEY (doctor_id) REFERENCES Doctor(id)
);

CREATE TABLE IF NOT EXISTS Consultation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    doctor_id INT,
    patient_id INT,
    date_time DATETIME,
    notes TEXT,
    FOREIGN KEY (doctor_id) REFERENCES Doctor(id),
    FOREIGN KEY (patient_id) REFERENCES Patient(id)
);

CREATE TABLE IF NOT EXISTS MedicalRecord (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    consultation_id INT,
    prescriptions TEXT,
    medical_history TEXT,
    FOREIGN KEY (patient_id) REFERENCES Patient(id),
    FOREIGN KEY (consultation_id) REFERENCES Consultation(id)
);



CREATE TABLE IF NOT EXISTS Prescription (
    id INT AUTO_INCREMENT PRIMARY KEY,
    consultation_id INT,
    medications TEXT,
    dosage VARCHAR(50),
    duration VARCHAR(50),
    FOREIGN KEY (consultation_id) REFERENCES Consultation(id)
);

CREATE TABLE IF NOT EXISTS Secretary (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);
