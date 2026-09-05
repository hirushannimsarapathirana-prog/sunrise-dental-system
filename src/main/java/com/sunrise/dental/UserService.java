package com.sunrise.dental;

public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User login(User user) {

        if (user == null) {
            return null;
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            return null;
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            return null;
        }

        return userDAO.login(user.getUsername(), user.getPassword());
    }
}