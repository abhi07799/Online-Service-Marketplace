package marketplace.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "services")
public class ServiceModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String serviceTitle;

    private String serviceDescription;

    private String servicePrice;

    private String serviceStatus;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    @JsonBackReference
    private VendorModel vendor;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
