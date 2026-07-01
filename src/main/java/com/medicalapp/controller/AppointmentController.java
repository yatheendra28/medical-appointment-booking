package com.medicalapp.controller;

import com.medicalapp.model.Appointment;
import com.medicalapp.model.Doctor;
import com.medicalapp.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class AppointmentController {
    
    @Autowired
    private AppointmentService appointmentService;
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        model.addAttribute("doctors", appointmentService.getAllDoctors());
        return "index";
    }
    
    @GetMapping("/appointments")
    public String viewAppointments(Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        model.addAttribute("doctors", appointmentService.getAllDoctors());
        return "appointment";
    }
    
    @GetMapping("/appointment/new")
    public String showNewAppointmentForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("doctors", appointmentService.getAllDoctors());
        return "appointment";
    }
    
    @PostMapping("/appointment/save")
    public String saveAppointment(@RequestParam String patientName,
                                  @RequestParam String patientEmail,
                                  @RequestParam String patientPhone,
                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime appointmentDateTime,
                                  @RequestParam String symptoms,
                                  @RequestParam Long doctorId,
                                  RedirectAttributes redirectAttributes) {
        try {
            Doctor doctor = appointmentService.getDoctorById(doctorId);
            if (doctor == null) {
                redirectAttributes.addFlashAttribute("error", "Doctor not found");
                return "redirect:/appointments";
            }
            
            Appointment appointment = new Appointment(
                patientName, patientEmail, patientPhone, 
                appointmentDateTime, symptoms, "SCHEDULED"
            );
            appointment.setDoctor(doctor);
            
            appointmentService.saveAppointment(appointment);
            redirectAttributes.addFlashAttribute("success", "Appointment booked successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error booking appointment: " + e.getMessage());
        }
        return "redirect:/appointments";
    }
    
    @GetMapping("/appointment/cancel/{id}")
    public String cancelAppointment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Appointment appointment = appointmentService.getAppointmentById(id);
            if (appointment != null) {
                appointment.setStatus("CANCELLED");
                appointmentService.saveAppointment(appointment);
                redirectAttributes.addFlashAttribute("success", "Appointment cancelled successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Appointment not found");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error cancelling appointment");
        }
        return "redirect:/appointments";
    }
    
    @GetMapping("/appointment/delete/{id}")
    public String deleteAppointment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            appointmentService.deleteAppointment(id);
            redirectAttributes.addFlashAttribute("success", "Appointment deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting appointment");
        }
        return "redirect:/appointments";
    }
    
    @GetMapping("/doctor/{id}")
    public String viewDoctorAppointments(@PathVariable Long id, Model model) {
        Doctor doctor = appointmentService.getDoctorById(id);
        if (doctor != null) {
            model.addAttribute("doctor", doctor);
            model.addAttribute("appointments", appointmentService.getAppointmentsByDoctor(id));
        }
        return "doctor-appointments";
    }
}
