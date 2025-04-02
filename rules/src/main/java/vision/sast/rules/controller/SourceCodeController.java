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

    @GetMapping("sourceCode")
    public synchronized String fileAndVtid(String vtid, String file) {
        if (vtid != null && file != null) {
            try {
                String key = vtid + ":" + file;
                if(issuesMap.get(key)==null){
                    List<IssueDto> issueDtos = RulesApplication.ISSUE_RESULT.getResult().stream().filter(dto->dto.getFilePath().equals(file) && dto.getVtId().equals(vtid)).toList();
                    issuesMap.put(key, issueDtos);
                }
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
