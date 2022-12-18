package ru.itis.hauntedo.simbirtest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "appointment_info")
public class AppointmentDetail extends AbstractEntity {

    @Column(name = "recommendation")
    private String recommendation;

    @Column(name = "general_info")
    private String generalInfo;

    @Column(name = "anamnesis")
    private String anamnesis;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;


}
