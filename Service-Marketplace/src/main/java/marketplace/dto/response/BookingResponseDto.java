package marketplace.dto.response;

import lombok.*;
import marketplace.model.BookingStatusEnum;
import marketplace.model.ServiceModel;
import marketplace.model.UserModel;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponseDto
{
    private long id;

    private UserModel user;

    private ServiceModel service;

    private BookingStatusEnum bookingStatus;

    private LocalDateTime bookingCreatedDate;

    private LocalDateTime bookingUpdateDate;
}
