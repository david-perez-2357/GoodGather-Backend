package dtos;

import lombok.Data;

@Data
public class TicketDTO {
    private Integer id;
    private Double price;
    private Integer amount;
    private Integer idEvent;
    private Integer idUser;
}
