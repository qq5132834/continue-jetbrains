package vision.sast.rules;

import vision.sast.rules.dto.IssueDto;
import vision.sast.rules.utils.ShowIssueInFile;

import java.util.ArrayList;

public class ShowIssueInFileTest {
    public static void main(String[] args) throws Exception {
        RulesApplication.loadProperties();
        String str = ShowIssueInFile.show("src/test/resources/ANSI_test.c", new ArrayList<IssueDto>());
        System.out.println(str);
    }
}
