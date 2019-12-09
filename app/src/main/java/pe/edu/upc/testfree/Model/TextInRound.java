package pe.edu.upc.testfree.Model;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public abstract class TextInRound {
    List<String> texts;

    TextInRound() {
        texts = new ArrayList<>();
    }

    public abstract void addText(String text);

    public abstract List<String> getTexts();
}
