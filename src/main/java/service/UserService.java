package service;

import model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class UserService {
    private static UserService userServ;

    private UserService() {
    }

    public static UserService userServiceInstance() {
        if (userServ == null) {
            userServ = new UserService();
        }
        return userServ;
    }

    /* хранилище данных */
    private Map<Long, User> dataBase = Collections.synchronizedMap(new HashMap<>());
    /* счетчик id */
    private AtomicLong maxId = new AtomicLong(0);
    /* список авторизованных пользователей */
    private Map<Long, User> authMap = Collections.synchronizedMap(new HashMap<>());


    public List<User> getAllUsers() {
        return new ArrayList<>(dataBase.values());
    }

    public User getUserById(Long id) {
        return dataBase.get(id);
    }

    public boolean addUser(User user) {
        for (User p : dataBase.values()) {
            if (p.getEmail().equals(user.getEmail())) {
                return false;
            }
        }
        user.setId(maxId.get());
        dataBase.put(user.getId(), user);
        maxId.set(maxId.get() + 1);
        return true;
    }

    public void deleteAllUser() {
        dataBase = Collections.synchronizedMap(new HashMap<>());
    }

    public boolean isExistsThisUser(User user) {
        for (User p : dataBase.values()) {
            if (p.getEmail().equals(user.getEmail())) {
                return true;
            }
        }
        return false;
    }

    public List<User> getAllAuth() {
        return new ArrayList<>(authMap.values());
    }

    public boolean authUser(User user) {
        if (userServ.isExistsThisUser(user)) {
            for (User p : dataBase.values()) {
                if ((p.getEmail().equals(user.getEmail())) & (p.getPassword().equals(user.getPassword()))) {
                    authMap.put(p.getId(), p);
                    return true;
                }
            }
        }

        return false;
    }

    public void logoutAllUsers() {
        authMap.clear();
    }

    public boolean isUserAuthById(Long id) {
        return authMap.containsKey(id);
    }

}
