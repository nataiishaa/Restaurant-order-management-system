package com.developer.Restaurant_manager_appIDZ.services;



import com.developer.Restaurant_manager_appIDZ.model.Order;
import com.developer.Restaurant_manager_appIDZ.model.User;
import com.developer.Restaurant_manager_appIDZ.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public String signIn(String login, String password) {
        // Попытка найти пользователя по логину
        User user = userRepository.findByLogin(login);
        if (user == null) {
            return "ERROR: User not found";
        }
        // Проверка пароля
        if (!user.getPassword().equals(password)) {
            return "ERROR: Incorrect password";
        }
        return "Sign in successfully";
    }

    public String authenticate(String login) {
        // Проверка существования пользователя по логину
        if (userRepository.findByLogin(login) == null) {
            return "ERROR: User not found";
        }
        return "Authenticated successfully";
    }

    public User getByLogin(String login) {
        // Получение пользователя по логину
        return userRepository.findByLogin(login);
    }
    @Transactional
    public String removeUserItem(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return "ERROR: Dish to delete not found";
        }
        userRepository.deleteById(id);
        return "Dish item successfully deleted";
    }

}
