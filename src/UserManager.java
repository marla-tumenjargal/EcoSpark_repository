import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages user profiles including storing profiles and authenticating
 */
class UserManager {
    private static final String DATA_FILE = "users.json";
    private Map<String, Profile> userProfiles;

    /**
     * Constructor, constructs new instance of the user manager and loads user data
     */
    public UserManager() {
        userProfiles = new HashMap<>();
        loadData();
    }

    /**
     * Authenticates user with email and password
     * @param email, user's email
     * @param password, user's password
     * @return true if authentication is successful
     */
    public boolean authenticate(String email, String password) {
        if (userProfiles.containsKey(email)) {
            Profile profile = userProfiles.get(email);
            return profile.getPassword().equals(password);
        }
        return false;
    }

    /**
     * Checks if a user exists in the system.
     *
     * @param email The user's email.
     * @return true if the user exists, false otherwise.
     */
    public boolean userExists(String email) {
        return userProfiles.containsKey(email);
    }

    /**
     * Adds a new user profile
     * @param profile, profile
     */
    public void addUser(Profile profile) {
        userProfiles.put(profile.getEmail(), profile);
        saveData();
    }

    /**
     * Getter, gets email
     * @param email
     * @return
     */
    public Profile getProfile(String email) {
        return userProfiles.get(email);
    }

    /**
     * Retrieves a user's profile.
     * @param profile, profile
     */
    public void updateProfile(Profile profile) {
        userProfiles.put(profile.getEmail(), profile);
        saveData();
    }

    /**
     * Saves user data to the JSON file.
     */
    public void saveData() {
        try {
            JSONObject json = new JSONObject();
            JSONArray usersArray = new JSONArray();

            for (Profile profile : userProfiles.values()) {
                usersArray.put(profile.toJSON());
            }

            json.put("users", usersArray);

            try (FileWriter file = new FileWriter(DATA_FILE)) {
                file.write(json.toString(2));
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Error saving user data: " + e.getMessage(),
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     *Loads user data
     */
    public void loadData() {
        File dataFile = new File(DATA_FILE);

        if (!dataFile.exists()) {
            return;
        }

        try {
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
            }

            JSONObject json = new JSONObject(content.toString());
            JSONArray usersArray = json.getJSONArray("users");

            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject userJson = usersArray.getJSONObject(i);
                Profile profile = Profile.fromJSON(userJson); // Ensure Profile has a fromJSON method
                userProfiles.put(profile.getEmail(), profile);
            }

        } catch (IOException | JSONException e) {
            JOptionPane.showMessageDialog(null,
                    "Error loading user data: " + e.getMessage(),
                    "Load Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Deletes profile
     * @param email
     */
    public void deleteProfile(String email) {
        if (userProfiles.containsKey(email)) {
            userProfiles.remove(email);
            saveData();
        }
    }
}