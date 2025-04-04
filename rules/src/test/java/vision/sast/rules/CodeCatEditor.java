package vision.sast.rules;

import java.awt.*;
import javax.swing.*;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.rsyntaxtextarea.modes.PlainTextTokenMaker;
import org.fife.ui.rtextarea.*;

public class CodeCatEditor {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(()->{
            String code = getCatCode();
            CodeCatEditor.createAndShowGUI(code);
        });

        TokenMaker tokenMaker;
        TokenMakerFactory tokenMakerFactory;
        PlainTextTokenMaker plainTextTokenMaker;

    }

    public static void createAndShowGUI(String text) {
        JFrame frame = new JFrame("Code Cat Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        // 创建 RSyntaxTextArea
        RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
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
                              
               int main() {
                   cout << "Hello, C++!" << endl;
                   return 0;
               }
               """;
    }
}
