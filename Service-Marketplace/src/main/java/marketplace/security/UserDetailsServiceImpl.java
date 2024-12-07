package marketplace.security;

import marketplace.model.UserModel;
import marketplace.model.VendorModel;
import marketplace.repository.UserRepository;
import marketplace.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VendorRepository vendorRepository;


    @Override
    public UserDetails loadUserByUsername(String userMail) throws UsernameNotFoundException
    {
        Optional<UserModel> userModel = userRepository.findByUserMail(userMail);
        if (userModel.isPresent())
        {
            return userModel.get();
        }

        Optional<VendorModel> vendorModel = vendorRepository.findByVendorMail(userMail);
        if (vendorModel.isPresent())
        {
            return vendorModel.get();
        }
        throw new UsernameNotFoundException("User : " + userMail + " not Found");
    }
}
