package vision.sast.rules.utils;

import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.TokenMaker;
import org.fife.ui.rsyntaxtextarea.TokenTypes;
import org.fife.ui.rsyntaxtextarea.modes.CPlusPlusTokenMaker;

import javax.swing.text.Segment;
import java.util.List;

public class HighLightUtil {

    public static String highlight(String file) throws Exception {
        StringBuilder sb = new StringBuilder();
        List<String> lines = ShowIssueInFile.openFile(file);
        lines.stream().forEach(line->{
            Segment segment = createSegment(line);
            TokenMaker tm = new CPlusPlusTokenMaker();
            Token token = tm.getTokenList(segment, TokenTypes.NULL, 0);
            StringBuilder stringBuilder = printToken(token);
            stringBuilder.append("\n");
            sb.append(stringBuilder.toString());
        });
        return sb.toString();
    }

    private static Segment createSegment(String code) {
        return new Segment(code.toCharArray(), 0, code.length());
    }

    private static StringBuilder printToken(Token token){
        Token next = token;
        String text = new String(next.getTextArray());
        StringBuilder stringBuilder = new StringBuilder();
        while (next !=null && next.getType() != TokenTypes.NULL) {

            int offset = next.getOffset();
            int len = next.length();
            int type = next.getType();
            String tokenImage = text.substring(offset, offset + len);
            String htmlTag = TokenTypeUtil.getHtml(type, tokenImage);
//            System.out.println(type + " " + tokenImage);
            stringBuilder.append(htmlTag);
            next = next.getNextToken();
        }
        return stringBuilder;
    }

}
