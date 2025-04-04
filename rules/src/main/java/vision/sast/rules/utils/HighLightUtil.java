package vision.sast.rules.utils;

import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.TokenTypes;

import javax.swing.text.Segment;

public class HighLightUtil {

    public static String highlight(String file) {

        return file;
    }

    private static Segment createSegment(String code) {
        return new Segment(code.toCharArray(), 0, code.length());
    }

    private static void printToken(Token token){
        Token next = token;
        String text = new String(next.getTextArray());

        while (next !=null && next.getType() != TokenTypes.NULL) {

            int offset = next.getOffset();
            int len = next.length();
            int type = next.getType();
            String tokenImage = text.substring(offset, offset + len);
//            System.out.println(type + " " + tokenImage);
            System.out.print(TokenTypeUtil.getHtml(type, tokenImage));
//            System.out.print(tokenImage);
            next = next.getNextToken();
        }
        System.out.println();
    }

}
