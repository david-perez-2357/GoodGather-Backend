package main.goodgatherbackend.models;

import main.goodgatherbackend.enumerated.Scope;
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
@Entity
@Table(name="cause", schema ="goodgather", catalog = "postgres")
public class Cause {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "deleted", nullable = false)
    private Integer deleted = 0;

    @Column(name = "scope", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Scope scope;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_owner", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "cause", fetch = FetchType.LAZY)
    private List<Event> events;
}
