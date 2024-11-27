package main.goodgatherbackend.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

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
    private Integer id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="description", nullable = false)
    private String description;

    @Column(name="image", nullable = false)
    private String image;

    @Column(name="capacity", nullable = false)
    private Integer capacity;

    @Column(name="start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name="end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name="address", nullable = false)
    private String address;

    @Column(name="country", nullable = false)
    private String country;

    @Column(name="province", nullable = false)
    private String province;

    @Column(name="ticket_price", nullable = false)
    private Double ticketPrice;

    @Column(name="created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name="deleted", nullable = false)
    private Integer deleted;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_owner", referencedColumnName ="id" )
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_cause", nullable = false)
    private Cause cause;

    // Relations mapped by the Event class
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private List<Ticket> tickets;
}
