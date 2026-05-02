package kg.attractor.javasharehub.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @Column(name = "role")
    private String role;
    private boolean enabled;

    @OneToMany(mappedBy = "uploader", fetch = FetchType.LAZY)
    private List<FileEntity> files;
}