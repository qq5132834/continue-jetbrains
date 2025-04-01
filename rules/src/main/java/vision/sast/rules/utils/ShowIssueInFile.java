package vision.sast.rules.utils;

import org.apache.commons.io.FileUtils;
import vision.sast.rules.dto.IssueDto;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

public class ShowIssueInFile {

    public static String show(String fileName, List<IssueDto> dtoList) throws Exception {
        List<String> lines = FileUtils.readLines(new File(fileName), Charset.forName("utf-8"));
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (String line : lines) {
//            line.replaceAll(" ", "");
//            line = "<div>" + line + "</div>";
            //line = line.replaceAll("<", "&lt;").replaceAll(">", "&gt;");//.replaceAll(" ", "&nbsp;");
            sb.append(line);
            sb.append("<br>");
            i++;
        }
//        return sb.toString();
        return "<html>" +
                "<head>" +
//                 "<link href='https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/themes/prism-tomorrow.min.css' rel='stylesheet'>" +
//                 "<script src='https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/prism.min.js'></script>" +
                 "</head>" +
                "<body>" +
                "<pre><code class='language-clike'>" +
                sb.toString() +
                 "</code></pre>" +
                "</body>" +
                "</html>";
    }


    /***
     * return "<html><head>" +
     *            "<link href='https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/themes/prism-tomorrow.min.css' rel='stylesheet'>" +
     *            "<script src='https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/prism.min.js'></script>" +
     *            "</head><body><pre><code class='language-" + language + "'>" +
     *            content +
     *            "</code></pre></body></html>";
     */

}
