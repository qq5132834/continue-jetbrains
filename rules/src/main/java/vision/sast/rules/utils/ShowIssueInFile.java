package vision.sast.rules.utils;

import org.apache.commons.io.FileUtils;
import vision.sast.rules.RulesApplication;
import vision.sast.rules.dto.IssueDto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

public class ShowIssueInFile {

    private static Map<String, String> map = new HashMap<>();

    public synchronized static void init(){
        try {
            File file = new File("escape.txt");
            System.out.println(file.getAbsolutePath() + ", " + file.exists());
            if(file.exists()){
                List<String> list = FileUtils.readLines(file,"utf-8");
                list.stream().map(l->l.trim()).forEach(l->{
                    String[] ss = l.split(" ");
                    if(ss!=null && ss.length >= 2){
                        map.put(ss[0], ss[ss.length-1]);
                    }
                });
                map.entrySet().forEach(entry->{
                    System.out.println(entry.getKey() + " " + entry.getValue());
                });
            }
            else {
                file.createNewFile();
            }
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static String show(String fileName, List<IssueDto> dtoList) {
        try {
            init();
            System.out.println("fileName = " + fileName + ", dtoList = " + dtoList.size());
            List<IssueDto> sortedList = dtoList.stream().sorted(Comparator.comparing(IssueDto::getLine)).toList();
            List<String> lines = new ArrayList<>();

            String codeFormat = "utf-8";
            if(RulesApplication.PROPERTIES.get(PropertiesKey.codeFormat)!=null){
                codeFormat = (String) RulesApplication.PROPERTIES.get(PropertiesKey.codeFormat);
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), codeFormat))) {
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            StringBuilder sb = new StringBuilder("<ol>");

            List<String> newLines = lines.stream().map(line -> {
                for(Map.Entry<String, String> entry: map.entrySet()){
                    line = line.replaceAll(entry.getKey(), entry.getValue());
                }
                line = "<li>" + line + "</li>";
                return line;
            }).collect(Collectors.toList());

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
            String htmlFileName = new File(fileName).getName() + ".html";
            FileUtils.write(new File(htmlFileName), html, Charset.forName("utf-8"));
            return html;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
