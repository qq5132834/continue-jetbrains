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
        StringBuilder sb = new StringBuilder("<ol>");

        List<String> newLines = lines.stream().map(line->{
            line = "<li>" +line + "</li>";
            return line;
        }).collect(Collectors.toList());

        int insertTime = 0;
        for (IssueDto dto: sortedList) {
            int line = dto.getLine();
            int index = line + insertTime;
            if(index > 0){
                String divStr = "<div style='background-color: red'>" + dto.getRuleDesc() + "</div>";
                newLines.add(index, divStr);
                insertTime++;
            }
        }

        for (String line : newLines) {
            sb.append(line);
            sb.append("<br>");
        }

        sb.append("</ol>");
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
