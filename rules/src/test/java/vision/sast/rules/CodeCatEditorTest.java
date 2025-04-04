package vision.sast.rules;

import org.fife.ui.rsyntaxtextarea.TokenMaker;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.modes.CPlusPlusTokenMaker;
import vision.sast.rules.utils.CodeCatEditor;

import javax.swing.*;

public class CodeCatEditorTest {

    public static void main(String[] args) {

//        SwingUtilities.invokeLater(()->{
//            String code = getCatCode();
//            CodeCatEditor.createAndShowGUI(code);
//        });

        String code = getCatCode();
        CodeCatEditor.createAndShowGUI(code);

        TokenMaker tokenMaker;
        TokenMakerFactory tokenMakerFactory;
        CPlusPlusTokenMaker cPlusPlusTokenMaker;

    }

    // 代码猫（ASCII 艺术）
    private static String getCatCode() {
        return """
               #include <stdio.h>
               using namespace std;
                              
               int main() {  //你好
                   cout << "Hello, C++!" << endl;
                   return 0;
               }
               """;
    }
}
