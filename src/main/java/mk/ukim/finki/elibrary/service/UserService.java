package mk.ukim.finki.elibrary.service;

import mk.ukim.finki.elibrary.model.User;
import mk.ukim.finki.elibrary.model.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User login(String username, String password);

    User register(String username, String password, String repeatPassword, String name, String surname, Role role);
}
