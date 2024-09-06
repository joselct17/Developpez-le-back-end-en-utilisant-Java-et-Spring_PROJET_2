package fr.chatop.projet_3.service.interfaces;

import fr.chatop.projet_3.model.Rentals;
import fr.chatop.projet_3.model.Users;
import fr.chatop.projet_3.model.dto.RegisterUserDto;

import java.util.List;
import java.util.Optional;

 public interface IUserService {
   Users createUser(Users user);

   List<Users> getAllUsers();

   Optional<Users> getUserById(int id);

   Users updateUser(Integer id, RegisterUserDto registerUserDto);

   void deleteUser(Integer id);

   Users getUserByEmail(String email);
}
