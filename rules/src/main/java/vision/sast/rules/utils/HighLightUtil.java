package vision.sast.rules.utils;

import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.TokenMaker;
import org.fife.ui.rsyntaxtextarea.TokenTypes;
import org.fife.ui.rsyntaxtextarea.modes.CPlusPlusTokenMaker;

import javax.swing.text.Segment;
import java.util.List;

public class HighLightUtil {

    private int tokenType = TokenTypes.NULL;

    public HighLightUtil(){

    }

    public static String highlightFile(String file) throws Exception {
        StringBuilder sb = new StringBuilder();
        List<String> lines = SourceCodeUtil.openFile(file);
        HighLightUtil highlighterUtil = new HighLightUtil();
        lines.stream().forEach(line->{

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(highlighterUtil.highlightLine(line));
            stringBuilder.append("\n");
            sb.append(stringBuilder.toString());
        });
        return sb.toString();
    }

    public String highlightLine(String line) {
        Segment segment = this.createSegment(line);
        TokenMaker tm = new CPlusPlusTokenMaker();
        Token token = tm.getTokenList(segment, TokenTypes.NULL, 0);
        StringBuilder stringBuilder = this.printToken(token);
        return stringBuilder.toString();
    }

    private Segment createSegment(String code) {
        return new Segment(code.toCharArray(), 0, code.length());
    }

    private StringBuilder printToken(Token token){
        Token next = token;
        StringBuilder stringBuilder = new StringBuilder();
        while (next !=null && next.getType() != TokenTypes.NULL) {

            int offset = next.getOffset();
            int len = next.length();
            int type = next.getType();
            String text = new String(next.getTextArray());
            String tokenImage = text.substring(offset, offset + len);
            String htmlTag = TokenTypeUtil.getHtml(type, tokenImage);
//            System.out.println(type + " " + tokenImage);
            stringBuilder.append(htmlTag);
            next = next.getNextToken();
        }
        return stringBuilder;
    }

}
