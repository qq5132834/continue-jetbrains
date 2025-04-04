package vision.sast.rules.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.text.StringEscapeUtils;
import vision.sast.rules.RulesApplication;
import vision.sast.rules.dto.IssueDto;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShowIssueInFile {


    public static List<String> openFile(String fileName) throws Exception {

        String codeFormat = "GBK";
        if(RulesApplication.PROPERTIES.get(PropertiesKey.codeFormat)!=null){
            codeFormat = (String) RulesApplication.PROPERTIES.get(PropertiesKey.codeFormat);
        }
        System.out.println("open file format = " + codeFormat);

        List<String> lines = FileUtils.readLines(new File(fileName),codeFormat);

        return lines;

    }

    public static String show(String fileName, List<IssueDto> dtoList) {
        try {
            System.out.println("fileName = " + fileName + ", dtoList = " + dtoList.size());
            List<IssueDto> sortedList = dtoList.stream().sorted(Comparator.comparing(IssueDto::getLine)).toList();

            List<String> lines = openFile(fileName);
            List<String> newLines = lines.stream().map(line->{
                line = StringEscapeUtils.escapeHtml4(line);
                line = "<li>" + line + "</li>";
                return line;
            }).collect(Collectors.toList());

            StringBuilder sb = new StringBuilder("<ol>");

            int insertTime = 0;
            for (IssueDto dto : sortedList) {
                int line = dto.getLine();
                int index = line + insertTime;
                if (index > 0) {
                    String divStr = "<div style='background-color: pink'>"
                            + dto.getName() + "<br>"
                            + dto.getLine() + "/" + dto.getVtId() + "/" + dto.getRule() + "/" + dto.getDefectLevel() + "/" + dto.getDefectType() + "/" + "<br>"
                            + dto.getRuleDesc() + "<br>"
                            + dto.getIssueDesc() + "<br>"
                            + "</div>";
                    newLines.add(index, divStr);
                    insertTime++;
                }
            }

            for (String line : newLines) {
                sb.append(line);
                sb.append("<br>");
            }

            sb.append("</ol>");
            String html = "<html>" +
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
            //String htmlFileName = new File(fileName).getName() + ".html";
            //FileUtils.write(new File(htmlFileName), html, Charset.forName("utf-8"));
            return html;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
