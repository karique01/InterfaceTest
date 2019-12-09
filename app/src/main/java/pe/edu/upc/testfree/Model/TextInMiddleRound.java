package pe.edu.upc.testfree.Model;

import java.util.List;

public class TextInMiddleRound extends TextInRound {

    @Override
    public void addText(String text) {
        texts.add(text);
        texts.add(".");
    }

    @Override
    public List<String> getTexts() {
//        if (texts.size() > 0) {
//            if (texts.get(texts.size() - 1).equals(".")) {
//                texts.remove(texts.size() - 1);
//            }
//        }
        return texts;
    }
}
