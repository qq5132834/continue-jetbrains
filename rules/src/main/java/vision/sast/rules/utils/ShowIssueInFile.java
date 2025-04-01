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
        for (String line : lines) {
            sb.append(line).append("<br>");
        }
        return sb.toString();
    }

}
