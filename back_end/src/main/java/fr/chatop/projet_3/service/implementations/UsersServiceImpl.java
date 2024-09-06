package fr.chatop.projet_3.service.implementations;

import fr.chatop.projet_3.model.Users;
import fr.chatop.projet_3.model.dto.RegisterUserDto;
import fr.chatop.projet_3.repository.IUserRepository;
import fr.chatop.projet_3.service.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UsersServiceImpl implements IUserService {

  private final IUserRepository userRepository;

  @Override
  public Users createUser(Users user) {
    user.setCreatedAt(LocalDateTime.now());
    return userRepository.save(user);
  }

  public List<Users> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public Optional<Users> getUserById(int id) {
    return userRepository.findById(id);
  }

  @Override
  public Users updateUser(Integer id, RegisterUserDto registerUserDto) {
    Optional<Users> existingUserOpt = userRepository.findById(id);
    if (existingUserOpt.isPresent()) {
      Users existingUser = existingUserOpt.get();
      existingUser.setName(registerUserDto.getName());
      existingUser.setEmail(registerUserDto.getEmail());
      existingUser.setPassword(registerUserDto.getPassword());
      existingUser.setUpdatedAt(LocalDateTime.now());
      return userRepository.save(existingUser);
    }
    return null;
  }

  @Override
  public void deleteUser(Integer id) {
    userRepository.deleteById(id);
  }

  @Override
  public Users getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}
