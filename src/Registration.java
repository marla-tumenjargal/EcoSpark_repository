class Registration {
    public static Profile register(String email, String password, String name, int age, String gender) {
        // In a real application, you would validate inputs and check if the email already exists
        if (email == null || email.isEmpty() || password == null || password.isEmpty() ||
                name == null || name.isEmpty() || age <= 0) {
            System.out.println("Registration failed. Please fill in all required fields.");
            return null;
        }

        System.out.println("Registration successful. Welcome to EcoSpark, " + name + "!");
        return new Profile(email, password, name);
    }
}