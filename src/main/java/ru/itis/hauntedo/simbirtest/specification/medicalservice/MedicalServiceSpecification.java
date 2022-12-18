package ru.itis.hauntedo.simbirtest.specification.medicalservice;

import org.springframework.data.jpa.domain.Specification;
import ru.itis.hauntedo.simbirtest.model.MedicalService;
import ru.itis.hauntedo.simbirtest.model.MedicalServiceCategory;

import javax.persistence.criteria.Join;

public class MedicalServiceSpecification {

    public static Specification<MedicalService> byCategoryCode(String code) {
        return (root, query, criteriaBuilder) -> {
            if (code == null) {
                return criteriaBuilder.conjunction();
            }
            Join<MedicalServiceCategory,MedicalService> msToMsc = root.join("medicalServiceCategory");
            return criteriaBuilder.equal(msToMsc.get("code"), code);
        };
    }
}
