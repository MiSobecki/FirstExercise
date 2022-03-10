package sobecki.michal.ex1.controller;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import sobecki.michal.ex1.model.Response;
import sobecki.michal.ex1.repository.UserRepository;

import java.util.Map;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("register")
    public Response register(@RequestParam String name) {
        int count = userRepository.register(name);

        return new Response("OK", count);
    }

    @DeleteMapping("delete")
    public void delete(@RequestParam String name) {
        userRepository.delete(name);
    }

    @GetMapping("stats")
    public Map<String, Integer> getUsers(@RequestParam(required = false, defaultValue = "SHORT") Mode mode) {
        return switch (mode) {
            case ALL -> userRepository.getAllUsers();
            case IGNORE_CASE -> userRepository.getAllUsersInsensitive();
            case SHORT -> userRepository.getTopUsers();
        };
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingNameArgument(MissingServletRequestParameterException ex) {
        return ex.getMessage();
    }
}
