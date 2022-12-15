package ru.itis.hauntedo.simbirtest.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.itis.hauntedo.simbirtest.model.Doctor;
import ru.itis.hauntedo.simbirtest.model.MedicalService;
import ru.itis.hauntedo.simbirtest.model.MedicalServiceCategory;

import javax.persistence.criteria.Join;
import javax.print.Doc;

public class DoctorSpecification {

    public static Specification<Doctor> byMedicalServiceCategory(String code) {
        return (root, query, criteriaBuilder) -> {
            if (code == null) {
                return criteriaBuilder.conjunction();
            }
            Join<Doctor, MedicalService> drToMs = root.join("medicalServices");
            Join<MedicalService, MedicalServiceCategory> msToMsc = drToMs.join("medicalServiceCategory");
            return criteriaBuilder.equal(msToMsc.get("code"), code);
        };
    }

    public static Specification<Doctor> byMedicalServiceCode(String code) {
        return (root, query, criteriaBuilder) -> {
            if (code == null) {
                return criteriaBuilder.conjunction();
            }
            Join<MedicalService,Doctor> drToMs = root.join("medicalServices");
            return criteriaBuilder.equal(drToMs.get("code"), code);
        };
    }
}
