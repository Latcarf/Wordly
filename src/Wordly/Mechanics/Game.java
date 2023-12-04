package Wordly.Mechanics;

import Wordly.FilesWork.ResourceManager;
import Wordly.Render.GameTableRenderer;
import Wordly.UserChoice.UserInputHandler;
import Wordly.UserChoice.UserLanguage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Game {
    public List<String> wordlyList = new ArrayList<>();

    private final int ATTEMPTS = 6; // Number of attempts a player has to guess a word.
    private int lvl = 1;
    private int sizeWordlyList;//Number of words in the wordly list.

    private final UserLanguage userLanguage = new UserLanguage();
    private final GameTableRenderer gameTableRenderer = new GameTableRenderer();
    private final Wordly wordly = new Wordly();
    private final Hint hint = new Hint();
    private final UserInputHandler userInputHandler = new UserInputHandler(hint);


    private Locale locale;
    private final ResourceManager manager = ResourceManager.getInstance(new Locale("en", "US"));


    public void startGame() {

        Scanner scanner = new Scanner(System.in);

        System.out.println(manager.getMessage("WELCOME") + "\n");
        locale = userLanguage.choiceStartLanguage(scanner);

        changeLanguage();

        userInputHandler.isShowRules(scanner);

        wordlyList = wordly.getWordly(scanner, locale);

        sizeWordlyList = wordlyList.size();

        gameLaunch(scanner);
    }

    private void gameLaunch(Scanner scanner) {
        // Main game loop.

        while (true) {
            boolean gameContinues = wordly(scanner);

            if (!gameContinues) {
                break;
            } else if (wordlyList.isEmpty() && wordly.needToChangeLanguage(locale, scanner)) {
                locale = userLanguage.choiceNewLanguage(scanner);
                changeLanguage();
                changeMode(scanner);
            }
        }
    }

    private boolean wordly(Scanner scanner) {
        // Handles one game round

        if (wordlyList.isEmpty()) {
            changeMode(scanner);
        }

        String wordly = this.wordly.getRandomWordly(wordlyList);


        printLvl();
        gameTableRenderer.printStartTable(wordly, ATTEMPTS);

        for (int gameCount = 0; gameCount < ATTEMPTS; ) {
            String userWord = userInputHandler.validateUserInput(scanner, wordly);

            if (userWord.equals("")) {
                continue;
            }
            if (userWord.equals(manager.getMessage("SURRENDER"))) {
                return isLose(ATTEMPTS, wordly, scanner);
            }
            if (isWin(userWord, wordly, scanner)) {
                return true;
            }

            gameCount++;
            printLvl();

            gameTableRenderer.renderGameTable(wordly, userWord, gameCount);
            gameTableRenderer.getResultTableList().clear();

            if (isLose(gameCount, wordly, scanner)) {
                return true;
            }

        }
        return true;
    }

    private boolean isWin(String userWord, String wordly, Scanner scanner) {

        if (userWord.equals(wordly)) {

            if (wordlyList.isEmpty() && this.wordly.isAllWordlyListsCompleted()) {
                System.out.printf(manager.getMessage("ALL_WORDLY_WIN"), hint.getCountHint());
                System.out.println(manager.getMessage("ALL_WORDLY_LISTS_COMPLETED"));
                System.exit(0);
            } else if (wordlyList.isEmpty()) {
                System.out.printf(manager.getMessage("ALL_WORDLY_WIN"), hint.getCountHint());
                System.out.println(manager.getMessage("DO_YOU_WANT_TO_CONTINUE"));
            } else {
                System.out.printf(manager.getMessage("WIN"), hint.getCountHint());
                System.out.println();
            }
            lvl++;
            this.wordly.markWordlyAsComplete();
            return userInputHandler.isContinueGame(scanner, gameTableRenderer.getUserTableList());
        }
        return false;
    }

    private boolean isLose(int gameCount, String wordly, Scanner scanner) {

        if (gameCount == ATTEMPTS) {

            if (wordlyList.isEmpty() && this.wordly.isAllWordlyListsCompleted()) {
                System.out.printf(manager.getMessage("ALL_WORDLY_LOSE"), hint.getCountHint(), wordly);
                System.out.println(manager.getMessage("ALL_WORDLY_LISTS_COMPLETED"));
                System.exit(0);
            } else if (wordlyList.isEmpty()) {
                System.out.printf(manager.getMessage("ALL_WORDLY_LOSE"), hint.getCountHint(), wordly);
                System.out.println(manager.getMessage("DO_YOU_WANT_TO_CONTINUE"));
            } else {
                System.out.printf(manager.getMessage("LOSE"), hint.getCountHint(), wordly);
                System.out.println();
            }
            lvl++;
            this.wordly.markWordlyAsComplete();
            return userInputHandler.isContinueGame(scanner, gameTableRenderer.getUserTableList());
        }
        return false;
    }

    private void changeMode(Scanner scanner) {

        wordlyList = wordly.getWordly(scanner, locale);
        lvl = 1;
        sizeWordlyList = wordlyList.size();
    }

    private void printLvl() {

        System.out.printf(manager.getMessage("LVL"), lvl, sizeWordlyList);
        System.out.println();
    }

    private void changeLanguage() {

        manager.changeLocale(locale);
        userInputHandler.changeLocale(locale);
        hint.changeLocale(locale);
        userLanguage.changeLocale(locale);
        wordly.changeLocale(locale);
    }

}
