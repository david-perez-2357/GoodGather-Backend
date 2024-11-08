package models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

@Entity
@Table(name="ticket", schema = "goodgather", catalog = "postgres")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="price", nullable = false)
    private Double price;

    @Column(name="amount", nullable = false)
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_event", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_user", nullable = false)
    private User user;


}
