package com.sunrise.dental;

public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean login(User user) {

        if (user == null) {
            return false;
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            return false;
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            return false;
        }

        return userDAO.login(user.getUsername(), user.getPassword());
    }
}