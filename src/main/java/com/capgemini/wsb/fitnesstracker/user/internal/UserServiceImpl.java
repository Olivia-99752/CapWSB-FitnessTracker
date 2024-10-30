package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllByEmail(final String email) { return userRepository.findAllByEmail(email); }

    @Override
    public List<User> findOlder(long age) { return userRepository.findOlder(age); }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(final Long userId, final User updatedUser) {

        final Optional<User> user = getUser(userId);
        log.info("Updating User {}", user);
        if(user.isPresent()) {
              deleteUser(userId);
              createUser(updatedUser);
              return updatedUser;
        }
        else{
            throw new IllegalArgumentException("User does not exist");
        }
    }

    @Override
    public Optional<User> deleteUser(final Long userId) {
        log.info("Deleting User {}", userId);
        final Optional<User> user = getUser(userId);
        user.ifPresent(userRepository::delete);
        return user;
    }

}