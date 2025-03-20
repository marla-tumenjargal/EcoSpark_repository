import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

class UserManager {
    private static final String DATA_FILE = "users.json";
    private Map<String, Profile> userProfiles;

    public UserManager() {
        userProfiles = new HashMap<>();
        loadData();
    }

    public boolean authenticate(String email, String password) {
        if (userProfiles.containsKey(email)) {
            Profile profile = userProfiles.get(email);
            return profile.getPassword().equals(password);
        }
        return false;
    }

    public boolean userExists(String email) {
        return userProfiles.containsKey(email);
    }

    public void addUser(Profile profile) {
        userProfiles.put(profile.getEmail(), profile);
        saveData();
    }

    public Profile getProfile(String email) {
        return userProfiles.get(email);
    }

    public void updateProfile(Profile profile) {
        userProfiles.put(profile.getEmail(), profile);
        saveData();
    }

    public void saveData() {
        try {
            JSONObject json = new JSONObject();
            JSONArray usersArray = new JSONArray();

            for (Profile profile : userProfiles.values()) {
                usersArray.put(profile.toJSON()); // Ensure Profile has a toJSON method
            }

            json.put("users", usersArray);

            try (FileWriter file = new FileWriter(DATA_FILE)) {
                file.write(json.toString(2)); // Pretty-print JSON for readability
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Error saving user data: " + e.getMessage(),
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void loadData() {
        File dataFile = new File(DATA_FILE);

        if (!dataFile.exists()) {
            // No saved data yet
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

    public void deleteProfile(String email) {
        if (userProfiles.containsKey(email)) {
            userProfiles.remove(email); // Remove the user from the map
            saveData(); // Save the updated user data to the JSON file
        } else {
            System.out.println("User with email " + email + " not found.");
        }
    }
}