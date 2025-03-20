import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class User {
    protected String name;
    protected String email;
    protected String password;
    protected boolean isLoggedIn;

    public static final Map<String, User> userDatabase = new HashMap<>();

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isLoggedIn = false;
    }

    public static boolean register(String name, String email, String password) {
        if (!userDatabase.containsKey(email)) {
            userDatabase.put(email, new RegularUser(name, email, password));
            return true;
        }
        return false;
    }

    public boolean login(String email, String password) {
        User user = userDatabase.get(email);
        if (user != null && user.password.equals(password)) {
            user.isLoggedIn = true;
            return true;
        }
        return false;
    }

    public void logout() {
        this.isLoggedIn = false;
    }

    public static User getUser(String email) {
        return userDatabase.get(email);
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}

class RegularUser extends User {
    public RegularUser(String name, String email, String password) {
        super(name, email, password);
    }
}

//public abstract class User {
//    protected String email;
//    protected String password;
//    protected boolean isLoggedIn;
//
//    public User(String email, String password) {
//        this.email = email;
//        this.password = password;
//        this.isLoggedIn = false;
//    }
//
//    public abstract boolean login(String email, String password);
//    public abstract void logout();
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public boolean isLoggedIn() {
//        return isLoggedIn;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//}