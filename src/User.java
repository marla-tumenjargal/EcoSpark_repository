public abstract class User {
    protected String name;
    protected String email;
    protected String password;
    protected boolean isLoggedIn;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isLoggedIn = false;
    }
}
