package jbdc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class DatabaseCredentials {

    private String URL;
    private String USER;
    private String PASSWORD;

    private static DatabaseCredentials INSTANCE;

    public static DatabaseCredentials getInstance() {
        if(INSTANCE == null) throw new IllegalArgumentException("No instance! Please call initialize method first.");
        return INSTANCE;
    }

    public static void initialize(String url){
        INSTANCE = new DatabaseCredentials();

        INSTANCE.loadProperties(Paths.get(url));
    }

    private void loadProperties(Path path) {
        Properties properties = new Properties();

        try{
            properties.load(Files.newBufferedReader(path));

            USER = properties.getProperty("username");
            PASSWORD = properties.getProperty("password");
            URL = properties.getProperty("url");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private  DatabaseCredentials() {}
    public String getURL(){
        return URL;
    }
    public String getUSER() {
        return USER;
    }
    public String getPASSWORD() {
        return PASSWORD;
    }
}