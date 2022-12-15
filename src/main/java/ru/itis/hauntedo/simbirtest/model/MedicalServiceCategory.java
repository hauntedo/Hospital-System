package ru.itis.hauntedo.simbirtest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@Table(name = "medical_service_category")
public class MedicalServiceCategory extends AbstractEntity {

    @Column(name = "category_title", nullable = false)
    private String title;

    @Column(name = "category_description")
    private String description;

    @Column(name = "medical_service_category_code", unique = true, nullable = false)
    private String code;

    @OneToMany(mappedBy = "medicalServiceCategory")
    private List<MedicalService> medicalServices;

}
