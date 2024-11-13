package main.goodgatherbackend.dtos;

import lombok.Data;

@Data
public class UserClientDTO {
    private Integer id;
    private String username;
    private String password;

    private Integer idClient;
    private String firstname;
    private String surname;
    private String email;
    private String birthdate;
    private String province;
    private String country;
}
