package pl.dawid.transportapp.security.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.dawid.transportapp.security.model.User;
import pl.dawid.transportapp.security.model.UserRole;
import pl.dawid.transportapp.security.repository.UserRepository;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository repository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping()
    public void signUp(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        UserRole userRole = new UserRole();
        userRole.setRole("USER");
        user.setRoles(Set.of(userRole));
        repository.save(user);
    }

    @GetMapping()
    public List get() {
      return   repository.findAll();
    }
}
