package marketplace.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto
{
    private String fullName;
    private String userMail;
    private String password;
    private String userMobile;
    private String userAddress;
    private String role;
}
