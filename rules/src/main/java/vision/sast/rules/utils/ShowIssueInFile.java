package vision.sast.rules.utils;

import org.apache.commons.io.FileUtils;
import vision.sast.rules.dto.IssueDto;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ShowIssueInFile {

    public static String show(String fileName, List<IssueDto> dtoList) throws Exception {
        List<IssueDto> sortedList = dtoList.stream().sorted(Comparator.comparing(IssueDto::getLine)).toList();
        List<String> lines = FileUtils.readLines(new File(fileName), Charset.forName("utf-8"));
        StringBuilder sb = new StringBuilder();
        int i = 1;


        int insertTime = 0;
        for (IssueDto dto: sortedList) {
            int line = dto.getLine();
            int index = line + insertTime;
            if(index > 0){
                String divStr = "<div class=\"issue\">" + dto.getRuleDesc() + "</div>";
                lines.add(index, divStr);
                insertTime++;
            }
        }

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
