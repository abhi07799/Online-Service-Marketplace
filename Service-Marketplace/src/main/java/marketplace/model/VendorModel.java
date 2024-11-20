package marketplace.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
    @JsonManagedReference
    private List<ServiceModel> services;

    @Column(nullable = false)
    private String role;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime AccountCreatedOn;
}
