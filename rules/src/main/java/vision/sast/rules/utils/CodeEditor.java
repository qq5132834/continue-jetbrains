package vision.sast.rules.utils;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.TokenMaker;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.modes.CPlusPlusTokenMaker;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;

public class CodeEditor {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        SwingUtilities.invokeLater(()->{
            String code = getCatCode();
            CodeEditor.createAndShowGUI(code);
        });


        TokenMaker tokenMaker;
        TokenMakerFactory tokenMakerFactory;
        CPlusPlusTokenMaker cPlusPlusTokenMaker;

    }

    public static void createAndShowGUI(String text) {
        JFrame frame = new JFrame("Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        // 创建 RSyntaxTextArea
        RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS); //c++语法
        textArea.setCodeFoldingEnabled(true);
        textArea.setText(text);

        // 将 RSyntaxTextArea 包装在 RTextScrollPane 中
        RTextScrollPane sp = new RTextScrollPane(textArea);
        frame.add(sp);

        frame.setVisible(true);
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
