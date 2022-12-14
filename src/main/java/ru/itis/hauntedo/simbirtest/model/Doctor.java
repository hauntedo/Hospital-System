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

    @ManyToMany(mappedBy = "doctors")
    private Set<MedicalService> medicalServices = new HashSet<>();

}
