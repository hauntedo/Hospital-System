package ru.itis.hauntedo.simbirtest.specification.appointment;

import org.springframework.data.jpa.domain.Specification;
import ru.itis.hauntedo.simbirtest.model.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentSpecification {

    public static Specification<Appointment> byPatient(User user) {
        return (root, query, criteriaBuilder) -> {
            if (user == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("patient"), user);
        };
    }

    public static Specification<Appointment> byDoctor(Doctor doctor) {
        return (root, query, criteriaBuilder) -> {
            if (doctor == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("doctor"), doctor);
        };
    }

    public static Specification<Appointment> byDateEqual(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            if (date == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("date"), date);
        };
    }

    public static Specification<Appointment> byDateLess(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            if (date == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThan(root.get("date"), date);
        };
    }

    public static Specification<Appointment> byDateGreat(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            if (date == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThan(root.get("date"), date);
        };
    }

    public static Specification<Appointment> byStartLess(LocalTime start) {
        return (root, query, criteriaBuilder) -> {
            if (start == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThan(root.get("start"), start);
        };
    }

    public static Specification<Appointment> byStartGreat(LocalTime start) {
        return (root, query, criteriaBuilder) -> {
            if (start == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThan(root.get("start"), start);
        };
    }

    public static Specification<Appointment> byStartEqual(LocalTime start) {
        return (root, query, criteriaBuilder) -> {
            if (start == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("start"), start);
        };
    }
}
