package ru.itis.hauntedo.simbirtest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.itis.hauntedo.simbirtest.utils.enums.ConfirmCodeState;

import javax.persistence.*;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
@Table(name = "confirm_code")
public class ConfirmCode extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "confirm_code_state")
    private ConfirmCodeState confirmCodeState;

    @Column(name = "confirm_code", nullable = false)
    private UUID confirmCode;

    @OneToOne
    @JoinColumn(name = "account_id")
    private User user;
}
