package vision.sast.rules;

import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vision.sast.rules.dto.IssueResult;

import java.io.File;
import java.nio.charset.Charset;

@SpringBootApplication
public class RulesApplication {

    public static IssueResult ISSUE_RESULT;

    public static void main(String[] args) {
        if(args!=null && args.length>0){
            String issuePath = args[0];
            ISSUE_RESULT = buildIssueResult(new File(issuePath));
        }
        if(ISSUE_RESULT==null){
            System.out.println("issue result is null");
            System.exit(0);
        }
        SpringApplication.run(RulesApplication.class, args);

    }

    public static IssueResult buildIssueResult(File file){
        if(file!=null && file.exists()){
            try {
                StringBuilder stringBuilder = new StringBuilder();
                FileUtils.readLines(file, Charset.forName("utf-8")).stream().map(line->line+"\n").forEach(stringBuilder::append);
                IssueResult issueResult = JSONObject.parseObject(stringBuilder.toString(), IssueResult.class);
                return issueResult;
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return new IssueResult();
    }

}
