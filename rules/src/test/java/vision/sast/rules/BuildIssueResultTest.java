package vision.sast.rules;

import vision.sast.rules.dto.IssueResult;

import java.io.File;

public class BuildIssueResultTest {
    public static void main(String[] args) {
        String[] args1 = {"src/test/resources/issue.json"};
        RulesApplication.main(args1);
    }
}
