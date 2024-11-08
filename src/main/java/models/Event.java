package models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Locale;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

@Entity
@Table(name="event", schema = "goodgather", catalog = "postgres")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="description", nullable = false)
    private String description;

    @Column(name="image", nullable = false)
    private String image;

    @Column(name="capacity", nullable = false)
    private Integer capacity;

    @Column(name="start_date", nullable = false)
    private Locale startDate;

    @Column(name="end_date", nullable = false)
    private Integer endDate;

    @Column(name="ticket_price", nullable = false)
    private Double ticketPrice;

    @Column(name="deleted", nullable = false)
    private Boolean deleted;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_owner", referencedColumnName ="id" )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_cause", nullable = false)
    private Cause cause;




}
