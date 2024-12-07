package marketplace.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDto
{
    private String authenticationMessage;
    private String authenticationToken;
}
