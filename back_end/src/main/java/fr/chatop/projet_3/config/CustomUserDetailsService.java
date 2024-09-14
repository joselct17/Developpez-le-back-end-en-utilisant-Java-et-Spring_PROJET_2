package fr.chatop.projet_3.config;


import fr.chatop.projet_3.model.Users;
import fr.chatop.projet_3.service.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final IUserService userService;


  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Users user = userService.getUserByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }

    return User.withUsername(user.getEmail())
      .password(user.getPassword())
      .roles("USER")
      .build();
  }
}
