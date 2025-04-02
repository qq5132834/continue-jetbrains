package vision.sast.rules;

import org.apache.commons.io.FileUtils;
import vision.sast.rules.dto.IssueDto;
import vision.sast.rules.utils.ShowIssueInFile;

import java.io.File;
import java.util.ArrayList;

public class ShowIssueInFileTest {
    public static void main(String[] args) throws Exception {
        String str = ShowIssueInFile.show("src/test/resources/test.c", new ArrayList<IssueDto>());
        System.out.println(str);
    }
}
