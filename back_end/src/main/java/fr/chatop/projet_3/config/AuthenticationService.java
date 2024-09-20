package fr.chatop.projet_3.config;

import fr.chatop.projet_3.model.Users;
import fr.chatop.projet_3.service.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class AuthenticationService {

  private final IUserService userService;
  private final BCryptPasswordEncoder passwordEncoder; // Assurez-vous que ce bean est configuré
  private final JwtEncoder jwtEncoder;

  public String loginAndGenerateToken(String login, String password) {
    // Rechercher l'utilisateur par email
    Users user = userService.getUserByEmail(login);
    if (user == null) {
      throw new RuntimeException("Invalid email");
    }

    // Vérifier que le mot de passe correspond à celui de l'utilisateur
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new RuntimeException("Invalid password");
    }

    // Si l'email et le mot de passe sont corrects, générer le token JWT
    return generateTokenForUser(user);
  }

  private String generateTokenForUser(Users user) {
    Instant now = Instant.now();
    JwtClaimsSet claims = JwtClaimsSet.builder()
      .issuer("self")
      .issuedAt(now)
      .expiresAt(now.plus(1, ChronoUnit.DAYS))
      .subject(user.getEmail()) // Utilisation de l'email comme sujet
      .build();
    JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
      JwsHeader.with(MacAlgorithm.HS256).build(),
      claims
    );
    return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
  }
}
