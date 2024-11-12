package marketplace.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import marketplace.model.ServiceModel;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendorRequestDto
{
    @NotNull(message = "Full name must not be null")
    @NotEmpty(message = "Full name must not be empty")
    private String fullName;

    @NotNull(message = "Email must not be null")
    @NotEmpty(message = "Email must not be empty")
    private String vendorMail;

    @NotNull(message = "Password must not be null")
    @NotEmpty(message = "Password must not be empty")
    private String password;

    @NotNull(message = "Address must not be null")
    @NotEmpty(message = "Address must not be empty")
    private String vendorAddress;

    private List<ServiceModel> services;

    @NotNull(message = "Role must not be null")
    @NotEmpty(message = "Role must not be empty")
    @Pattern(regexp = "^(?i)vendor|user|admin$", message = "Role must be either 'vendor', 'user', or 'admin'")
    private String role;
}
