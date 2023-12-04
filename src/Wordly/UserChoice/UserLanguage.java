package Wordly.UserChoice;

import Wordly.FilesWork.ResourceManager;
import Wordly.Mechanics.Wordly;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import static Wordly.Mechanics.Wordly.wordlyMap;

public class UserLanguage {

    private List<String> availableLanguages;

    Locale locale;
    private ResourceManager manager = ResourceManager.getInstance(new Locale("en", "US"));

    public Locale choiceStartLanguage(Scanner scanner) {

        locale = getUserLanguage(scanner);
        manager = ResourceManager.getInstance(locale);
        return locale;
    }

    private Locale getUserLanguage(Scanner scanner) {

        String language = scanner.nextLine().toLowerCase().trim();

        while (true) {
            if (language.equals("eng") || language.equals("англ")) {
                return locale = new Locale("en", "US");
            } else if (language.equals("ukr") || language.equals("укр")) {
                return locale = new Locale("uk", "UA");
            } else {
                System.out.println(manager.getMessage("SELECT_LANGUAGE"));
                language = scanner.nextLine().toLowerCase().trim();
            }
        }
    }

    private List<String> getAvailableLanguages() {

        List<String> languages = new ArrayList<>();
        for (String key : wordlyMap.keySet()) {
            if (key.startsWith("EN") && !Wordly.COMPLETED_WORDLY_LISTS.contains(key)) {
                if (!languages.contains(manager.getMessage("ENG"))) {
                    languages.add(manager.getMessage("ENG"));
                }
            } else if (key.startsWith("UKR") && !Wordly.COMPLETED_WORDLY_LISTS.contains(key)) {
                if (!languages.contains(manager.getMessage("UKR"))) {
                    languages.add(manager.getMessage("UKR"));
                }
            }
        }
        return languages;
    }

    private void printAvailableLanguagesForUser() {

        availableLanguages = getAvailableLanguages();
        StringBuilder language = new StringBuilder();

        for (String availableLanguage : availableLanguages) {
            language.append(availableLanguage).append("\n");
        }
        System.out.printf(manager.getMessage("AVAILABLE_LANGUAGES"), language);
    }

    public boolean continueChangingLanguage(Locale locale, Scanner scanner) {

        changeLocale(locale);

        String playOn = scanner.nextLine().toLowerCase().trim();

        while (true) {

            if (playOn.equals(manager.getMessage("YES"))) {
                printAvailableLanguagesForUser();
                return true;

            } else if (playOn.equals(manager.getMessage("NO"))) {
                System.out.println(manager.getMessage("GOOD_BYE"));
                System.exit(0);

            } else {
                System.out.println(manager.getMessage("YES_OR_NO"));
                playOn = scanner.nextLine().toLowerCase().trim();
            }
        }
    }

    public Locale choiceNewLanguage(Scanner scanner) {

        String language = scanner.nextLine().toLowerCase().trim();

        while (true) {
            if (language.equals("eng") || language.equals("english") || language.equals("англ") || language.equals("англійська")) {
                System.out.printf(manager.getMessage("LANGUAGE_CHANGED"), manager.getMessage("ENG"));
                System.out.println();
                Locale newLocale = new Locale("en", "US");
                changeLocale(newLocale);
                availableLanguages = getAvailableLanguages();
                return newLocale;

            } else if (language.equals("ukr") || language.equals("ukrainian") || language.equals("укр") || language.equals("українська")) {
                System.out.printf(manager.getMessage("LANGUAGE_CHANGED"), manager.getMessage("UKR"));
                System.out.println();
                Locale newLocale = new Locale("uk", "UA");
                changeLocale(newLocale);
                availableLanguages = getAvailableLanguages();
                return newLocale;

            } else {
                printAvailableLanguagesForUser();
                language = scanner.nextLine().toLowerCase().trim();
            }
        }
    }

    public void changeLocale(Locale locale) {
        manager.changeLocale(locale);
    }
}

