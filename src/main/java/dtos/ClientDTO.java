package dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientDTO {
    private Integer idClient;
    private String firstname;
    private String surname;
    private String email;
    private LocalDate birthdate;
    private String country;
    private Integer idUser;
}
