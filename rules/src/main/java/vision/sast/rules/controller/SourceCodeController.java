package vision.sast.rules.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vision.sast.rules.RulesApplication;
import vision.sast.rules.dto.IssueDto;
import vision.sast.rules.utils.ShowIssueInFile;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class SourceCodeController {

    private static Map<String, List<IssueDto>> issuesMap = new ConcurrentHashMap<>();

    private static String getKey(String vtid, String file){
        String key = vtid + ":" + file;
        return key;
    }

    public static synchronized int init(String vtid, String file) {
        String key = getKey(vtid, file);
        if(issuesMap.get(key)==null){
            List<IssueDto> issueDtos = RulesApplication.ISSUE_RESULT.getResult().stream().filter(dto->dto.getFilePath().equals(file) && dto.getVtId().equals(vtid)).toList();
            issuesMap.put(key, issueDtos);
        }
        return issuesMap.get(key).size();
    }

    @GetMapping("sourceCode")
    public synchronized String fileAndVtid(String vtid, String file) {
        if (vtid != null && file != null) {
            try {
                int size = init(vtid, file);
                String key = getKey(vtid, file);
                List<IssueDto> issueDtos = issuesMap.get(key);
                return ShowIssueInFile.show(file, issueDtos);
            }catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
        else {
            return "null";
        }

    }

}
