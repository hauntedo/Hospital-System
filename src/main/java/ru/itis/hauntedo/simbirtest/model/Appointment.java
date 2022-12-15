package ru.itis.hauntedo.simbirtest.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import ru.itis.hauntedo.simbirtest.utils.enums.AppointmentState;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@SQLDelete(sql = "update doctor_appointment set appointment_state = 'CANCELLED' where id = ?")
@Table(name = "doctor_appointment")
public class Appointment extends AbstractEntity {

    @Column(name = "appointment_date")
    private Instant date;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "medical_service_id", referencedColumnName = "id")
    private MedicalService medicalService;

    @Column(name = "patient_comment")
    private String patientComment;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private User patient;

    @Enumerated(EnumType.STRING)
    @Column(name = "appointment_state")
    private AppointmentState appointmentState;

}
