package ru.itis.hauntedo.simbirtest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "appointment_detail_file")
public class AppointmentDetailFile extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "appointment_file_id")
    private FileEntity file;

    @OneToOne
    @JoinColumn(name = "appointment_detail_id")
    private AppointmentDetail appointmentDetail;
}
