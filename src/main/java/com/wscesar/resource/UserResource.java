package com.wscesar.resource;

import com.wscesar.model.User;
import com.wscesar.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    path = "api/v1/users"
)
public class UserResource {

    private UserService userService;

    @Autowired
    public UserResource(UserService userservice) {
        this.userService = userservice;
    }

    @RequestMapping(
        method = RequestMethod.GET
    )
    public List<User> fetchUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(
        method = RequestMethod.GET,
        path = "{userId}"
    )
    public ResponseEntity<?> fetchUser(@PathVariable("userId") UUID userId) {
        Optional<User> optionalUser = userService.getUser(userId);

        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(optionalUser.get());
        }

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ErrorMessage("User " + userId + " was not found on this server."));
    }

    @RequestMapping(
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Integer> insertUser(@RequestBody User user) {
        int result = userService.insertUser(user);

        if (result == 1) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    class ErrorMessage {

        String errorMessage;

        public ErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}
