package marketplace.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import marketplace.model.ServiceModel;
import marketplace.model.UserModel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingRequestDto
{
    @NotNull(message = "User Id Can Not Be Null")
    private UserModel user;

    @NotNull(message = "Service Id Can Not Be Null")
    private ServiceModel service;
}
