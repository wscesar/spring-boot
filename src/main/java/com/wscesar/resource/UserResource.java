package com.wscesar.resource;

import com.wscesar.model.User;
import com.wscesar.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.ws.rs.QueryParam;
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
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<User> fetchUsers(@QueryParam("gender") String gender) {
        return userService.getAllUsers(Optional.ofNullable(gender));
    }

    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        path = "{userId}"
    )
    public ResponseEntity<?> fetchUser(@PathVariable("userId") UUID userId) {
        return userService
            .getUser(userId)
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElseGet(
                () -> ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("User " + userId + " was not found."))
            );
    }

    @RequestMapping(
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Integer> insertUser(@RequestBody User user) {
        int result = userService.insertUser(user);
        return getIntegerResponseEntity(result);
    }

    @RequestMapping(
        method = RequestMethod.PUT,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Integer> updateUser(@RequestBody User user) {
        int result = userService.updateUser(user);
        return getIntegerResponseEntity(result);
    }

    @RequestMapping(
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE,
        path = "{userId}"
    )
    public ResponseEntity<Integer> removeUser(@PathVariable("userId") UUID userId) {
        int result = userService.removeUser(userId);
        return getIntegerResponseEntity(result);
    }

    private ResponseEntity<Integer> getIntegerResponseEntity(int result) {
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
