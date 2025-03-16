import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, String> users = new HashMap<>();
    private static final String USER_DATA_FILE = "userData.txt";
    private static final String SESSION_FILE = "session.txt";
    private String loggedInUserEmail;

    public UserManager() {
        loadUserData();
        loadSession();
    }

    public boolean registerUser(String email, String password) {
        if (users.containsKey(email)) { return false; }
        String hashedPassword = hashPassword(password);
        users.put(email, hashedPassword);
        saveUserData();
        return true;
    }

    public boolean loginUser(String email, String password) {
        String hashedPassword = hashPassword(password);
        if (users.containsKey(email) && users.get(email).equals(hashedPassword)) {
            loggedInUserEmail = email;
            saveSession();
            return true;
        }
        return false;
    }

    public void logoutUser() {
        loggedInUserEmail = null;
        saveSession();
    }

    public String getLoggedInUserEmail() {
        return loggedInUserEmail;
    }

    private void saveUserData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE))) {
            for (Map.Entry<String, String> entry : users.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("No existing user data found.");
        }
    }

    private void saveSession() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SESSION_FILE))) {
            if (loggedInUserEmail != null) {
                writer.write(loggedInUserEmail);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSession() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SESSION_FILE))) {
            String email = reader.readLine();
            if (email != null && users.containsKey(email)) {
                loggedInUserEmail = email;
            }
        } catch (IOException e) {
            System.out.println("No active session found.");
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Password hashing failed.");
        }
    }
}
