package Wordly.Render;

import java.util.ArrayList;
import java.util.List;

public class GameTableRenderer {
    private static final int ATTEMPTS = 6;

    private final List<String> userTableList;
    private List<String> resultTableList;


    public GameTableRenderer() {
        userTableList = new ArrayList<>();
        resultTableList = new ArrayList<>();
    }

    public List<String> getUserTableList() {
        return userTableList;
    }

    public List<String> getResultTableList() {
        return resultTableList;
    }

    private static StringBuilder topFrame(String wordly) {

        StringBuilder result = new StringBuilder();

        result.append("┌");
        result.append("─────┬".repeat(wordly.length()));
        result.delete(result.length() - 1, result.length());
        result.append("┐\n");

        return result;
    }

    private static StringBuilder bottomFrame(String wordly) {

        StringBuilder result = new StringBuilder();

        result.append("│\n");

        result.append("└");
        result.append("─────┴".repeat(wordly.length()));
        result.delete(result.length() - 1, result.length());
        result.append("┘\n");

        return result;
    }

    public void printStartTable(String wordly, int attempts) {

        StringBuilder startTable = new StringBuilder();
        for (int i = 0; i < attempts; i++) {
            startTable.append(topFrame(wordly));

            for (int j = 0; j < wordly.length(); j++) {
                startTable.append("|  ").append(" ").append("  ");
            }
            startTable.append(bottomFrame(wordly));
        }
        System.out.println(startTable);
    }

    private List<String> table(List<String> resultList, String wordly, int attempts) {

        StringBuilder table = new StringBuilder();
        resultTableList.addAll(resultList);

        for (int i = ATTEMPTS - attempts; i > 0; i--) {
            table.setLength(0);
            table.append(topFrame(wordly));

            for (int j = 0; j < wordly.length(); j++) {
                table.append("|  ").append(" ").append("  ");
            }
            table.append(bottomFrame(wordly));

            resultTableList.add(String.valueOf(table));
        }
        return resultTableList;
    }

    public void renderGameTable(String wordly, String userWord, int attempts) {

        StringBuilder result = new StringBuilder();

        result.append(topFrame(wordly));

        for (int i = 0; i < wordly.length(); i++) {
            char wordlyChar = wordly.charAt(i);
            char userChar = userWord.charAt(i);
            boolean letterFound = wordly.contains(String.valueOf(userChar));

            if (wordlyChar == userChar) {
                result.append("| *").append(userChar).append("* ");
            } else if (letterFound) {
                result.append("| -").append(userChar).append("- ");
            } else {
                result.append("|  ").append(userChar).append("  ");
            }
        }
        result.append(bottomFrame(wordly));

        userTableList.add(String.valueOf(result));
        resultTableList = table(userTableList, wordly, attempts);

        resultTableList.forEach(System.out::print);
        System.out.println();

    }
}
