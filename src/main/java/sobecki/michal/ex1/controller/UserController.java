package sobecki.michal.ex1.controller;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import sobecki.michal.ex1.model.Response;
import sobecki.michal.ex1.repository.UserRepository;

import java.util.LinkedHashMap;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("register")
    public Response register(@RequestParam String name) {
        int count = userRepository.register(name);

        return new Response("OK", count);
    }

    @GetMapping("delete")
    public void delete(@RequestParam String name) {
        userRepository.delete(name);
    }

    @GetMapping("stats")
    public LinkedHashMap<String, Integer> getUsers(@RequestParam(required = false) String mode) {
        if (mode == null) return userRepository.getTopUsers();

        return switch (mode) {
            case "ALL" -> userRepository.getAllUsers();
            case "IGNORE_CASE" -> userRepository.getAllUsersInsensitive();
            default -> throw new IllegalArgumentException("There is no such mode");
        };
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleWrongModeArgument(IllegalArgumentException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingNameArgument(MissingServletRequestParameterException ex) {
        return ex.getMessage();
    }
}
