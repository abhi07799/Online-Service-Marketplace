package marketplace.security;


import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import marketplace.model.UserModel;
import marketplace.model.VendorModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtAuthenticationService
{
     @Value("${security.jwt.secret-key}")
     private String SECRET_KEY;

    public String generateToken(UserModel userModel)
    {
        String token = Jwts.builder().subject(userModel.getUsername()).issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)).signWith(getSigningKey())
                .compact();

        return token;
    }

    public String generateToken(VendorModel vendorModel)
    {
        String token = Jwts.builder().subject(vendorModel.getUsername()).issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)).signWith(getSigningKey())
                .compact();

        return token;
    }

    private SecretKey getSigningKey()
    {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }


    //this method is used to extract all the information such as subject, issuedAt or expiration etc. from the token we generated
    private Claims extractAllClaims(String token)
    {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    //method to extract a specific property or information from the token
    private <T> T extractClaim(String token, Function<Claims, T> resolver)
    {
        Claims claim = extractAllClaims(token);
        return resolver.apply(claim);
    }

    //method to extract the username
    public String extractUsername(String token)
    {
        //getting the subject because when we generate the token,we put the username in subject parameter
        return extractClaim(token, Claims::getSubject);
    }

    // we will check whether the username  from payload and the username from UserDetails are same or not
    public boolean isValid(String token, UserDetails user)
    {
        String username = extractUsername(token);
        if(username.equals(user.getUsername()) && !isTokenExpired(token))
        {
            return true;
        }

        return false;
    }

    //to check whether token is expired or not
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //method is created so that we can get the expiration time from the token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


}
