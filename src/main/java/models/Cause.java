package models;

import enumerated.Scope;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode


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


    @Column(name = "scope", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Scope scope;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_owner", nullable = false)
    private User idOwner;

}
