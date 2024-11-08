package models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

@Entity
@Table(name = "client", schema = "goodgather", catalog = "postgres")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "firstname", nullable = false)
    private String FirstName;

    @Column(name = "surname", nullable = false)
    private String surName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name = "country", nullable = false)
    private String country;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_user", referencedColumnName ="id" )
    private User user;


}
