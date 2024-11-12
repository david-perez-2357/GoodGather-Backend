package main.goodgatherbackend.dtos;

import lombok.Data;

@Data
public class TicketDTO {
    private Integer id;
    private Double price;
    private Integer amount;
    private String purchaseDate;
    private Integer idEvent;
    private Integer idUser;
}
