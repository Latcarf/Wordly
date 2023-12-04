package Wordly.Mechanics;

import Wordly.FilesWork.ResourceManager;
import Wordly.UserChoice.UserLanguage;

import java.util.*;

public class Wordly {
    public static final Map<String, List<String>> wordlyMap = new HashMap<>() {{
        put("UK_5", new ArrayList<>(Arrays.asList("ліжко", "сонце", "річка", "школа", "колір", "пісня", "торба", "земля", "таксі", "цапля")));
        put("UK_6", new ArrayList<>(Arrays.asList("градус", "джміль", "дубина", "ювелір", "гектар", "вулкан", "жаргон", "емоція", "емірат", "яблуко")));
        put("EN_5", new ArrayList<>(Arrays.asList("bread", "cloud", "dance", "mango", "fruit", "globe", "horse", "juice", "dream", "lucky")));
        put("EN_6", new ArrayList<>(Arrays.asList("guitar", "rocket", "wisdom", "castle", "donkey", "pencil", "marvel", "Wizard", "impact", "Bridge")));
    }};
    public static final Set<String> COMPLETED_WORDLY_LISTS = new HashSet<>();

    private final UserLanguage userLanguage = new UserLanguage();
    private final Random RANDOM = new Random();
    private ResourceManager manager = ResourceManager.getInstance(new Locale("en", "US"));


    private String userWordlyList;
    private final String WORDLY_MODE_5 = "5";
    private final String WORDLY_MODE_6 = "6";

    private boolean availableWordly5 = false;
    private boolean availableWordly6 = false;
    private String availableWordly5Key = null;
    private String availableWordly6Key = null;


    protected List<String> getWordly(Scanner scanner, Locale locale) {

        manager = ResourceManager.getInstance(locale);

        chooseWordly(locale);
        String options = availableWordly();
        return userChoiceAvailableWordly(scanner, options);
    }

    private void chooseWordly(Locale locale) {

        availableWordly5 = false;
        availableWordly6 = false;
        availableWordly5Key = null;
        availableWordly6Key = null;

        for (String key : wordlyMap.keySet()) {
            if (key.startsWith(locale.getLanguage().toUpperCase()) && !COMPLETED_WORDLY_LISTS.contains(key)) {
                if (key.endsWith(WORDLY_MODE_5)) {
                    availableWordly5 = true;
                    availableWordly5Key = key;
                } else if (key.endsWith(WORDLY_MODE_6)) {
                    availableWordly6 = true;
                    availableWordly6Key = key;
                }
            }
        }
    }

    private String availableWordly() {

        StringBuilder options = new StringBuilder(manager.getMessage("AVAILABLE_WORDLY"));

        if (availableWordly5) {
            options.append(" ").append(WORDLY_MODE_5);
        }
        if (availableWordly6) {
            options.append(" ").append(WORDLY_MODE_6);
        }
        System.out.println(options);

        options.append("\n").append(manager.getMessage("CHOOSE_WORDLY_SIZE"));
        return options.toString();
    }

    private List<String> userChoiceAvailableWordly(Scanner scanner, String options) {

        String userSize = scanner.nextLine().trim();

        while (true) {
            if (userSize.equals(WORDLY_MODE_5) && availableWordly5) {
                userWordlyList = availableWordly5Key;
                return wordlyMap.get(userWordlyList);
            } else if (userSize.equals(WORDLY_MODE_6) && availableWordly6) {
                userWordlyList = availableWordly6Key;
                return wordlyMap.get(userWordlyList);
            } else {
                System.out.println(options);
                userSize = scanner.nextLine().trim();
            }
        }
    }

    protected boolean needToChangeLanguage(Locale locale, Scanner scanner) {

        chooseWordly(locale);
        if (!availableWordly5 && !availableWordly6) {
            System.out.println(manager.getMessage("NO_AVAILABLE_WORDLY"));

            return userLanguage.continueChangingLanguage(locale, scanner);
        }
        return false;
    }

    protected String getRandomWordly(List<String> wordlyList) {

        int randomNumber = RANDOM.nextInt(wordlyList.size());
        String randomWordly = wordlyList.get(randomNumber);
        wordlyList.remove(randomWordly);

        return randomWordly;
    }

    protected boolean isAllWordlyListsCompleted() {
        return COMPLETED_WORDLY_LISTS.size() == wordlyMap.size();
    }

    protected void markWordlyAsComplete() {
        COMPLETED_WORDLY_LISTS.add(userWordlyList);
    }

    public void changeLocale(Locale locale) {
        manager.changeLocale(locale);
    }
}