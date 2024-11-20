package marketplace.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String fullName;

    @Column(unique = true,nullable = false)
    private String userMail;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userAddress;

    @Column(nullable = false)
    private String role;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime AccountCreatedOn;
}
