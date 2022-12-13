package ru.itis.hauntedo.simbirtest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@Table(name = "medical_service")
public class MedicalService extends AbstractEntity {

    @Column(name = "medical_service_title", nullable = false)
    private String title;

    @Column(name = "medical_service_description")
    private String description;

    @Column(name = "medical_service_cost", nullable = false)
    private Double cost;

    @ManyToMany(mappedBy = "medicalServices")
    private Set<Doctor> doctors = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "medical_service_category_id")
    private MedicalServiceCategory medicalServiceCategory;

}
