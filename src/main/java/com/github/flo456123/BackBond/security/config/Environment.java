package com.github.flo456123.BackBond.security.config;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * The {@link Environment} class provides access to environment variables stored in a .env file located in the application's root directory.
 */
public class Environment {
    private static final Dotenv dotenv = Dotenv.configure().filename(".env").load();

    /**
     * Retrieves the value of the environment variable with the specified key.
     *
     * @param key the name of the environment variable
     * @return the value of the environment variable, or null if it is not set
     */
    public static String get(String key) {
        return dotenv.get(key);
    }
}