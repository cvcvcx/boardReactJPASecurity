package org.cvcvcx.board.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.cvcvcx.board.entity.MemberRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String secretString;

    private Key secretKey;

    private final long exp =1000L * 60 * 60;

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init(){
        secretKey = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String username, List<MemberRole> roles){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles",roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(now.getTime()+exp))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public String getUsername(String token){
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }
    public String resolveToken(HttpServletRequest request){
        return request.getHeader("Authorization");
    }

    public boolean validateToken(String token){
        try {
            if(!token.substring(0,"BEARER ".length()).equalsIgnoreCase("BEARER ")){
                return false;
            }
            String validatedToken = token.split(" ")[1].trim();

            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(validatedToken);

            return !claims.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            return false;
        }
    }




}
