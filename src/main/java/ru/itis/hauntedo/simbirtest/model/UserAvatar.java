package ru.itis.hauntedo.simbirtest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "user_avatar")
public class UserAvatar extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "file_id")
    private FileEntity userAvatar;

    @OneToOne
    @JoinColumn(name = "account_id")
    private User user;
}
