package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.BasicDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;


    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/simple")
    public List<BasicDto> getAllBasicUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toBasicDto)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUser(id).map(userMapper::toDto).orElse(null);
    }

    @GetMapping("/find/email")
    public UserDto getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email).map(userMapper::toDto).orElse(null);
    }

    @GetMapping("/email")
    public List<UserDto> getAllUsersByEmail(@RequestParam String email) {
        return userService.findAllByEmail(email).stream().map(userMapper::toDto).toList();
    }

    @GetMapping("/older/{time}")
    public List<UserDto> getUserByOlder(@PathVariable String time) {
        LocalDate date = LocalDate.parse(time);
        return userService.findOlder(date.getYear()).stream().map(userMapper::toDto).toList();
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        return userService.updateUser(id, user);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody UserDto userDto) throws InterruptedException {

        // Demonstracja how to use @RequestBody
        System.out.println("User with e-mail: " + userDto.email() + "passed to the request");

        // TODO: saveUser with Service and return User
        User user = userMapper.toEntity(userDto);
        return userService.createUser(user);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public User deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId).orElse(null);
    }

}