package vision.sast.rules;

import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.TokenImpl;
import org.fife.ui.rsyntaxtextarea.TokenMaker;
import org.fife.ui.rsyntaxtextarea.TokenTypes;
import org.fife.ui.rsyntaxtextarea.modes.CPlusPlusTokenMaker;
import vision.sast.rules.utils.TokenTypeUtil;

import javax.swing.text.Segment;
import java.util.Arrays;

public class CPPTokenMakerTest {
    public static void main(String[] args) {
        String code = """
                #include <stdio.h>
                int main()
                {
                    int a = 1;
                    printf("%d",a);
                    return 1;
                }
                """;
        // TokenTypes.java 类型
        TokenTypes tokenTypes;
        Arrays.stream(code.split("\n")).forEach(line->{
            CPPTokenMakerTest cppTokenMakerTest = new CPPTokenMakerTest();
            Segment segment = cppTokenMakerTest.createSegment(line);
            TokenMaker tm = new CPlusPlusTokenMaker();
            Token token = tm.getTokenList(segment, TokenTypes.NULL, 0);
            cppTokenMakerTest.printToken(token);
//            System.out.println(token);
        });

    }

    protected Segment createSegment(String code) {
        return new Segment(code.toCharArray(), 0, code.length());
    }

    protected void printToken(Token token){
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
