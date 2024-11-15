package marketplace.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "bookings")
public class BookingModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceModel service;

    @Enumerated(EnumType.STRING)
    private BookingStatusEnum bookingStatus;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime bookingCreatedDate;

    @UpdateTimestamp
    private LocalDateTime bookingUpdateDate;

}
