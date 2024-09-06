package fr.chatop.projet_3.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class JWTService {

  private final JwtEncoder jwtEncoder;

  public String generateToken(Authentication authentication) {
    Instant now = Instant.now();
    JwtClaimsSet claims = JwtClaimsSet.builder()
      .issuer("self")
      .issuedAt(now)
      .expiresAt(now.plus(1, ChronoUnit.DAYS))
      .subject(authentication.getName())
      .build();
    JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
    return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
  }

}
