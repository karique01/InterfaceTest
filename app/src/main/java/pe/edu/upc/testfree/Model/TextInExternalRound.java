package pe.edu.upc.testfree.Model;

import java.util.List;

public class TextInExternalRound extends TextInRound {

    @Override
    public void addText(String text) {
        texts.add(text);
    }

    @Override
    public List<String> getTexts() {
        return texts;
    }
}
