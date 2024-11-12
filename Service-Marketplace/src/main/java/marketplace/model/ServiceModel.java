package marketplace.model;

import jakarta.persistence.*;
import lombok.*;

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
    private VendorModel vendor;
}
