package com.developer.Restaurant_manager_appIDZ.controllers;

import com.developer.Restaurant_manager_appIDZ.model.User;
import com.developer.Restaurant_manager_appIDZ.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestParam String username, @RequestParam String password) {
        String response = userService.signIn(username, password);
        if (response.equals("Sign in successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestParam String username) {
        String response = userService.authenticate(username);
        if (response.equals("Authenticated successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByusername(@PathVariable String username) {
        User user = userService.getByLogin(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeDish(@PathVariable Integer id) {
        String response = userService.removeUserItem(id);
        if (response.contains("successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}