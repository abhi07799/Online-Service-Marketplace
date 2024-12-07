package marketplace.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import marketplace.dto.request.AuthenticationRequestDto;
import marketplace.dto.response.AuthenticationResponseDto;
import marketplace.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Authentication APIs")
@RestController
@RequestMapping("api/v1/auth/")
public class AuthenticationController
{
    @Autowired
    private AuthenticationService authService;

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponseDto> login(@Valid @RequestBody AuthenticationRequestDto request)
    {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
