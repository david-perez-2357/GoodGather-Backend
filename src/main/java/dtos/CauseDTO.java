package dtos;

import enumerated.Scope;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

@Data
public class CauseDTO {
private Integer idCause;
private String name;
private String description;
private String image;
private Scope scope;
private Integer idUser;

}
