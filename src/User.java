/**
 * User with basic account information
 */
public abstract class User {
    protected String name;
    protected String email;
    protected String password;
    protected boolean isLoggedIn;

    /**
     * Constructs new User
     * @param name, name of user
     * @param email, email of user
     * @param password, password of user
     */
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isLoggedIn = false;
    }
}
