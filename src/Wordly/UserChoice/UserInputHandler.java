package Wordly.UserChoice;

import Wordly.FilesWork.Dictionary;
import Wordly.FilesWork.ResourceManager;
import Wordly.Mechanics.Hint;

import java.util.*;

public class UserInputHandler {
    private final Set<String> enteredWords = new HashSet<>();
    private final ResourceManager manager = ResourceManager.getInstance(new Locale("en", "US"));
    private final Dictionary dictionary = new Dictionary();
    private final Hint hint;


    public UserInputHandler(Hint hint) {
        this.hint = hint;
    }

    public boolean isContinueGame(Scanner scanner, List<String> userTableList) {

        String playAgain = scanner.nextLine().toLowerCase().trim();

        while (true) {

            if (playAgain.equals(manager.getMessage("YES"))) {
                clear_EnteredWords_CountHint();
                userTableList.clear();
                return true;

            } else if (playAgain.equals(manager.getMessage("NO"))) {
                System.out.println(manager.getMessage("GOOD_BYE"));
                System.exit(0);

            } else {
                System.out.println(manager.getMessage("YES_OR_NO"));
                playAgain = scanner.nextLine().toLowerCase().trim();
            }
        }
    }

    public void isShowRules(Scanner scanner) {

        System.out.println(manager.getMessage("SHOW_THE_RULES"));
        System.out.println(manager.getMessage("YES_OR_NO"));

        String showRules = scanner.nextLine().toLowerCase().trim();

        while (true) {

            if (showRules.equals(manager.getMessage("YES"))) {
                System.out.println(manager.getMessage("RULE_MESSAGE") + "\n");
                return;

            } else if (showRules.equals(manager.getMessage("NO"))) {
                return;

            } else {
                System.out.println(manager.getMessage("YES_OR_NO"));
                showRules = scanner.nextLine().toLowerCase().trim();
            }
        }
    }

    public String validateUserInput(Scanner scanner, String wordly) {

        String userWord = scanner.nextLine().toLowerCase().trim();

        if (userWord.equals(manager.getMessage("SURRENDER"))) {
            return manager.getMessage("SURRENDER");

        } else if (userWord.equals(manager.getMessage("HINT"))) {
            System.out.println(hint.generateHint());
            return "";

        } else if (userWord.length() != wordly.length()) {
            System.out.println(manager.getMessage("WRONG_SIZE"));
            return "";

        } else if (enteredWords.contains(userWord)) {
            System.out.println(manager.getMessage("ALREADY_WAS"));
            return "";

        } else if (!dictionary.isRightUserWord(userWord, manager.getLocale(), wordly.length())) {
            System.out.println(manager.getMessage("WRONG_WORLD"));
            return "";

        } else if (dictionary.isRightUserWord(userWord, manager.getLocale(), wordly.length())) {
            enteredWords.add(userWord);
            hint.updateNotGuessedYet(wordly, enteredWords);
        }
        return userWord;
    }

    public void clear_EnteredWords_CountHint() {

        enteredWords.clear();
        hint.setCountHint(0);
        hint.setAllHintsUsed(false);
    }

    public void changeLocale(Locale locale) {
        manager.changeLocale(locale);
    }

}
