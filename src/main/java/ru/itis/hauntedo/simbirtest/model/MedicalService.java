package ru.itis.hauntedo.simbirtest.model;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class MedicalService extends AbstractEntity {

    @Column(name = "medical_service_title", nullable = false)
    private String title;

    @Column(name = "medical_service_description")
    private String description;

    @Column(name = "medical_service_cost", nullable = false)
    private Double cost;

    @OneToOne
    @JoinColumn(name = "medical_service_category_id")
    private MedicalServiceCategory medicalServiceCategory;

}
