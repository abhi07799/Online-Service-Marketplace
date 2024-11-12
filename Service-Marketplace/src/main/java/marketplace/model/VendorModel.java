package marketplace.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "vendors")
public class VendorModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String fullName;

    @Column(unique = true,nullable = false)
    private String vendorMail;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String vendorAddress;

    @OneToMany(mappedBy = "vendor")
    private List<ServiceModel> services;

    @Column(nullable = false)
    private String role;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime AccountCreatedOn;
}
