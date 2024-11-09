package marketplace.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto
{
    private long id;
    private String fullName;
    private String userMail;
    private String password;
    private String userMobile;
    private String userAddress;
    private String role;
    private LocalDateTime AccountCreatedOn;
}
