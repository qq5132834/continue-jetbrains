package vision.sast.rules;

import java.awt.*;
import javax.swing.*;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.rtextarea.*;

public class CodeCatEditor {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CodeCatEditor::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Code Cat Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // 创建 RSyntaxTextArea
        RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        textArea.setText(getCatCode());

        // 将 RSyntaxTextArea 包装在 RTextScrollPane 中
        RTextScrollPane sp = new RTextScrollPane(textArea);
        frame.add(sp);

        frame.setVisible(true);
    }

    // 代码猫（ASCII 艺术）
    private static String getCatCode() {
        return """
               #include <stdio.h>
               int main()
               {
                  int a = 0;
                  int b = 2;
                  int c = a + b;
                  printf("Hello, World! %d", c);
                  return 0;
               }
               """;
    }
}
