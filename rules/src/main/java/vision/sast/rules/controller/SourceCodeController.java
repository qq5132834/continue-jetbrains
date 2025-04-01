package vision.sast.rules.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vision.sast.rules.dto.IssueDto;
import vision.sast.rules.utils.ShowIssueInFile;

import java.util.List;

@RestController
public class SourceCodeController {

    @GetMapping("sourceCode")
    public String fileAndVtid(String vtid, String file) {
        FileController.loadInitList();
        try {
            List<IssueDto> dtos = FileController.fileIssuesMap.get(file).stream().filter(issueDto -> issueDto.getVtId().equals(vtid)).toList();
            return ShowIssueInFile.show(file, dtos);
        }catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

}
