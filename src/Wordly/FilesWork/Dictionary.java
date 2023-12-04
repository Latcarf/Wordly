package Wordly.FilesWork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;

public class Dictionary {
    private  HashSet<String> dictionary;

    private String getDictionaryForUser(Locale locale, int sizeWordly) {

        if (locale.getLanguage().equals("uk") && sizeWordly == 5) {
            return "dictionary\\dictionary_UKR_size_5.txt";
        } else if (locale.getLanguage().equals("uk") && sizeWordly == 6) {
            return "dictionary\\dictionary_UKR_size_6.txt";
        } else if (locale.getLanguage().equals("en") && sizeWordly == 5) {
            return "en";
//            return "dictionary\\dictionary_EN_size_5.txt";
        } else if (locale.getLanguage().equals("en") && sizeWordly == 6) {
            return "en";
//            return "dictionary\\dictionary_EN_size_6.txt";
        }
        return null;
    }

    public boolean isRightUserWord(String userWord, Locale locale, int sizeWordly) {

        dictionary = new HashSet<>();

        String dictionaryForUser = getDictionaryForUser(locale, sizeWordly);
        assert dictionaryForUser != null;
        if (dictionaryForUser.equals("en")) {
            return true;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(dictionaryForUser))) {
            String line;
            while ((line = br.readLine()) != null) {
                dictionary.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dictionary.contains(userWord);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Dictionary)) return false;
        Dictionary that = (Dictionary) o;
        return Objects.equals(dictionary, that.dictionary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dictionary);
    }
}
