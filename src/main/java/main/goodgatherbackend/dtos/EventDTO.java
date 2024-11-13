package main.goodgatherbackend.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDTO {
    private Integer id;
    private String name;
    private String description;
    private String image;
    private Integer capacity;
    private Integer boughtTickets;
    private String startDate;
    private String endDate;
    private Double ticketPrice;
    private String address;
    private String province;
    private String country;
    private Boolean deleted;
    private Integer idOwner;
    private Integer idCause;
}
