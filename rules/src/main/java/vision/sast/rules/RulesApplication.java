package vision.sast.rules;

import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vision.sast.rules.dto.IssueResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;

@SpringBootApplication
public class RulesApplication {

    public static String ISSUE_FILEPATH;
    public static IssueResult ISSUE_RESULT;
    public static Properties PROPERTIES = new Properties();

    public static void main(String[] args) {

        System.setProperty("java.awt.headless", "true");


        if(args!=null && args.length>0){
            String issuePath = args[0];
            ISSUE_RESULT = buildIssueResult(new File(issuePath));
        }
        if(ISSUE_RESULT==null){
            System.out.println("issue result is null");
            System.exit(0);
        }
        loadProperties();
        SpringApplication.run(RulesApplication.class, args);

    }

    public static void loadProperties() {
        try {
            File propertiesFile = new File("config.properties");
            if(!propertiesFile.exists()){
                propertiesFile.createNewFile();
            }
            InputStream input = new FileInputStream(propertiesFile);
            PROPERTIES.load(input);
            System.out.println(PROPERTIES);
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static IssueResult buildIssueResult(File file){
        if(file!=null && file.exists()){
            try {
                ISSUE_FILEPATH = file.getAbsolutePath().replaceAll("\\\\", "/");
                System.out.println(ISSUE_FILEPATH);
                StringBuilder stringBuilder = new StringBuilder();
                FileUtils.readLines(file, Charset.forName("utf-8")).stream().map(line->line+"\n").forEach(stringBuilder::append);
                IssueResult issueResult = JSONObject.parseObject(stringBuilder.toString(), IssueResult.class);
                return issueResult;
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            System.out.println("issue file is not exist");
            System.exit(0);
        }
        return new IssueResult();
    }

}
