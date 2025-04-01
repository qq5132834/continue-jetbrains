package vision.sast.rules;

import vision.sast.rules.dto.IssueResult;

import java.io.File;

public class BuildIssueResultTest {
    public static void main(String[] args) {
        IssueResult issueResult = RulesApplication.buildIssueResult(new File("src/test/resources/issue.json"));
        System.out.println(issueResult.getResult().size());
    }
}
