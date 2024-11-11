package marketplace.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendorResponseDto
{
    private long id;
    private String fullName;
    private String vendorMail;
    private String password;
    private String vendorAddress;
    private Set<String> services;
    private String role;
    private LocalDateTime AccountCreatedOn;
}
