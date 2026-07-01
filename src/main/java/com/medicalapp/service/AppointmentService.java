package com.medicalapp.service;

import com.medicalapp.model.Appointment;
import com.medicalapp.model.Doctor;
import com.medicalapp.repository.AppointmentRepository;
import com.medicalapp.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }
    
    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
    
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
    
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
    
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }
    
    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }
    
    public List<Appointment> getAppointmentsByStatus(String status) {
        return appointmentRepository.findByStatus(status);
    }
    
    @PostConstruct
    public void initData() {
        if (doctorRepository.count() == 0) {
            Doctor doctor1 = new Doctor("Dr. John Smith", "Cardiology", "john.smith@hospital.com", "+1-555-0101");
            Doctor doctor2 = new Doctor("Dr. Sarah Johnson", "Neurology", "sarah.johnson@hospital.com", "+1-555-0102");
            Doctor doctor3 = new Doctor("Dr. Michael Brown", "Orthopedics", "michael.brown@hospital.com", "+1-555-0103");
            Doctor doctor4 = new Doctor("Dr. Emily Davis", "Pediatrics", "emily.davis@hospital.com", "+1-555-0104");
            Doctor doctor5 = new Doctor("Dr. Robert Wilson", "Dermatology", "robert.wilson@hospital.com", "+1-555-0105");
            
            doctorRepository.save(doctor1);
            doctorRepository.save(doctor2);
            doctorRepository.save(doctor3);
            doctorRepository.save(doctor4);
            doctorRepository.save(doctor5);
            
            LocalDateTime now = LocalDateTime.now();
            
            Appointment app1 = new Appointment("Alice Johnson", "alice@email.com", "+1-555-0201", 
                now.plusDays(2).withHour(10).withMinute(0), "Chest pain and shortness of breath", "SCHEDULED");
            app1.setDoctor(doctor1);
            
            Appointment app2 = new Appointment("Bob Williams", "bob@email.com", "+1-555-0202", 
                now.plusDays(3).withHour(14).withMinute(30), "Severe headaches and dizziness", "SCHEDULED");
            app2.setDoctor(doctor2);
            
            Appointment app3 = new Appointment("Carol Martinez", "carol@email.com", "+1-555-0203", 
                now.plusDays(1).withHour(9).withMinute(0), "Knee pain after running", "SCHEDULED");
            app3.setDoctor(doctor3);
            
            Appointment app4 = new Appointment("David Lee", "david@email.com", "+1-555-0204", 
                now.plusDays(4).withHour(11).withMinute(30), "Skin rash on arms", "SCHEDULED");
            app4.setDoctor(doctor5);
            
            Appointment app5 = new Appointment("Emma Watson", "emma@email.com", "+1-555-0205", 
                now.plusDays(5).withHour(15).withMinute(0), "Child fever and cough", "SCHEDULED");
            app5.setDoctor(doctor4);
            
            appointmentRepository.save(app1);
            appointmentRepository.save(app2);
            appointmentRepository.save(app3);
            appointmentRepository.save(app4);
            appointmentRepository.save(app5);
        }
    }
}
