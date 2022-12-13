package ru.itis.hauntedo.simbirtest.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.itis.hauntedo.simbirtest.utils.enums.Role;
import ru.itis.hauntedo.simbirtest.utils.enums.State;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@Table(name = "account")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends AbstractEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(unique = true, nullable = false, name = "email")
    private String email;

    @Column(name = "hash_password")
    private String hashPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_state", nullable = false)
    private State state;


}
