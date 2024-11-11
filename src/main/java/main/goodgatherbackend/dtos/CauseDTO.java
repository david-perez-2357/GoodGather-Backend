package main.goodgatherbackend.dtos;

import enumerated.Scope;
import lombok.Data;

@Data
public class CauseDTO {
private Integer id;
private String name;
private String description;
private String image;
private Scope scope;
private Integer idUser;

}
