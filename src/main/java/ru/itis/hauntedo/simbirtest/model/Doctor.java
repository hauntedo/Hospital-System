package ru.itis.hauntedo.simbirtest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.checkerframework.checker.units.qual.N;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@Table(name = "doctor")
public class Doctor extends User {

    @Column(name = "doctor_experience")
    private Integer experience;

    @Column(name = "doctor_education")
    private String education;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "doctor_medical_service",
            joinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "medical_service_id", referencedColumnName = "id")
    )
    private Set<MedicalService> medicalServices = new HashSet<>();

}
