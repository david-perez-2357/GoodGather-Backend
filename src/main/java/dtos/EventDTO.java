package dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDTO {
    private Integer idEvent;
    private String name;
    private String description;
    private String image;
    private Integer capacity;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double ticketPrice;
    private Boolean deleted;
    private Integer idOwner;
    private Integer idCause;
}
