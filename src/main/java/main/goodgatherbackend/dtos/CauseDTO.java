package main.goodgatherbackend.dtos;

import lombok.Data;
import main.goodgatherbackend.enumerated.Scope;

@Data
public class CauseDTO {
private Integer id;
private String name;
private String description;
private String image;
private Scope scope;
private Integer idUser;

}
