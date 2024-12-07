package marketplace.service;

import marketplace.dto.request.AuthenticationRequestDto;
import marketplace.dto.response.AuthenticationResponseDto;
import marketplace.exception.CustomException;
import marketplace.model.UserModel;
import marketplace.model.VendorModel;
import marketplace.repository.UserRepository;
import marketplace.repository.VendorRepository;
import marketplace.security.JwtAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService
{
    @Autowired
    private JwtAuthenticationService jwtAuthenticationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VendorRepository vendorRepository;

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserMail(), request.getPassword()));

        Optional<UserModel> userModel = userRepository.findByUserMail(request.getUserMail());
        Optional<VendorModel> vendorModel = vendorRepository.findByVendorMail(request.getUserMail());

        String token=null;
        if(userModel.isPresent())
        {
            token = jwtAuthenticationService.generateToken(userModel.get());
        }
        else if(vendorModel.isPresent())
        {
            token = jwtAuthenticationService.generateToken(vendorModel.get());
        }
        else
        {
            throw new CustomException("Invalid username or password");
        }

        return new AuthenticationResponseDto().builder().authenticationMessage("Authenticated Successfully").authenticationToken(token).build();
    }
}
