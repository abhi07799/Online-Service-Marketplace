package marketplace.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequestDto
{
    @NotNull(message = "Email must not be null")
    @NotEmpty(message = "Email must not be empty")
    private String userMail;

    @NotNull(message = "Password must not be null")
    @NotEmpty(message = "Password must not be empty")
    private String password;

    @NotNull(message = "Role must not be null")
    @NotEmpty(message = "Role must not be empty")
    @Pattern(regexp = "^(?i)provider|user|admin$", message = "Role must be either 'provider', 'user', or 'admin'")
    private String role;
}
