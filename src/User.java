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

    public boolean login(String email, String password) {
        if (this.email.equals(email) && this.password.equals(password)) {
            this.isLoggedIn = true;
            return true;
        }
        return false;
    }

    public void logout() {
        this.isLoggedIn = false;
    }

    public String getEmail() {
        return email;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
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