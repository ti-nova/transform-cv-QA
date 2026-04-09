package config;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    private static final Dotenv dotenv = Dotenv.load();

    public static String getUserEmail() {
        return dotenv.get("USER_EMAIL");
    }

    public static String getUserPassword() {
        return dotenv.get("USER_PASSWORD");
    }
}