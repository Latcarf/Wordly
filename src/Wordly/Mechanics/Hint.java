package Wordly.Mechanics;

import Wordly.FilesWork.ResourceManager;

import java.util.*;

public class Hint {

    private final List<Character> notGuessedYet = new ArrayList<>();
    private final List<String> alreadyGuessed = new ArrayList<String>();


    private int countHint;
    private boolean allHintsUsed = false;


    private final Random RANDOM = new Random();
    private final ResourceManager manager = ResourceManager.getInstance(new Locale("en", "US"));


    public void updateNotGuessedYet(String wordly, Set<String> enteredWords) {

        notGuessedYet.clear();
        alreadyGuessed.clear();
        allHintsUsed = false;

        for (char c : wordly.toCharArray()) {
            String wordlyChar = String.valueOf(c);

            boolean isGuessed = false;
            for (String userChar : enteredWords) {

                if (userChar.contains(wordlyChar)) {
                    if (!alreadyGuessed.contains(wordlyChar)) {
                        alreadyGuessed.add(wordlyChar);
                    }
                    isGuessed = true;
                    break;
                }
            }
            if (!isGuessed) {
                notGuessedYet.add(c);
            }
        }
    }

    public String generateHint() {

        if (countHint > notGuessedYet.size() && notGuessedYet.isEmpty()) {
            allHintsUsed = true;
            return String.format(manager.getMessage("ALL_GUESSED"), String.join(", ", alreadyGuessed));

        } else if (notGuessedYet.isEmpty()) {
            allHintsUsed = true;
            return manager.getMessage("NO_HINTS");

        } else if (allHintsUsed) {
            return String.format(manager.getMessage("ALL_GUESSED"), String.join(", ", alreadyGuessed));

        } else {
            countHint++;

            int randomIndex = RANDOM.nextInt(notGuessedYet.size());
            char guessed = notGuessedYet.get(randomIndex);
            alreadyGuessed.add(String.valueOf(guessed));
            notGuessedYet.remove(randomIndex);

            return String.format(manager.getMessage("THE_HINT_IS"), guessed);
        }
    }

    protected int getCountHint() {
        return countHint;
    }

    public void setCountHint(int countHint) {
        this.countHint = countHint;
    }

    public void setAllHintsUsed(boolean allHintsUsed) {
        this.allHintsUsed = allHintsUsed;
    }

    public void changeLocale(Locale locale) {
        manager.changeLocale(locale);
    }

}
