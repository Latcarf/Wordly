package Wordly.FilesWork;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceManager {
    private ResourceBundle resourceBundle;
    private static ResourceManager instance;
    private Locale locale;


    private ResourceManager(Locale locale) {
        this.locale = locale;
        this.resourceBundle = ResourceBundle.getBundle("resources\\messages", locale);
    }

    public static synchronized ResourceManager getInstance(Locale locale) {

        if (instance == null || !instance.getLocale().equals(locale)) {
            instance = new ResourceManager(locale);
        }
        return instance;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getMessage(String key) {
        return resourceBundle.getString(key);
    }

    public void changeLocale(Locale locale) {
        this.locale = locale;
        this.resourceBundle = ResourceBundle.getBundle("resources\\messages", locale);
    }
}
