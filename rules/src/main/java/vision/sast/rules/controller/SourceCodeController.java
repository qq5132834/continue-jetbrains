package vision.sast.rules.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vision.sast.rules.RulesApplication;
import vision.sast.rules.dto.IssueDto;
import vision.sast.rules.utils.ShowIssueInFile;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class SourceCodeController {

    private static Map<String, List<IssueDto>> issuesMap = new ConcurrentHashMap<>();
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

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

    @GetMapping("sourceCodeEdit")
    public synchronized String sourceCodeEdit(String file) {

        SwingUtilities.invokeLater(()->{
            try {
                final String f = file;
                ShowIssueInFile.edit(f);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        });

//        executorService.execute(()->{
//            try {
//                final String f = file;
//                ShowIssueInFile.edit(f);
//            }
//            catch(Exception e){
//                e.printStackTrace();
//            }
//        });

        return "editing.";
    }

    @GetMapping("sourceCode")
    public synchronized String sourceCode(String vtid, String file) {
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
