package vision.sast.rules.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vision.sast.rules.RulesApplication;
import vision.sast.rules.dto.IssueDto;
import vision.sast.rules.utils.ShowIssueInFile;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestController
public class FileController {

    public static List<String> list;

    public static ConcurrentHashMap<String, List<IssueDto>> fileIssuesMap = new ConcurrentHashMap<>();

    public synchronized static void loadInitList() {
        if(list==null){
            Set<String> set = RulesApplication.ISSUE_RESULT.getResult().stream().map(dto->dto.getFilePath()).collect(Collectors.toSet());
            list = set.stream().toList().stream().sorted().toList();

            list.forEach(f->{
                if(fileIssuesMap.get(f)==null){
                    List<IssueDto> dtos = RulesApplication.ISSUE_RESULT.getResult().stream().filter(dto->dto.getFilePath().equals(f)).toList();
                    fileIssuesMap.put(f, dtos);
                }
            });
        }
    }

    @GetMapping("fileAndVtid")
    public String fileAndVtid(String vtid, String file) {
        loadInitList();
        try {
            List<IssueDto> dtos = fileIssuesMap.get(file).stream().filter(issueDto -> issueDto.getVtId().equals(vtid)).toList();
            return ShowIssueInFile.show(file, dtos);
        }catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

//        return vtid + "<br>" + file;
    }

    @GetMapping("file")
    public synchronized String file(String f) {
        loadInitList();
        List<IssueDto> ls = fileIssuesMap.get(f);
        //数量
        Map<String, List<IssueDto>> vtidGroupMap = ls.stream().collect(Collectors.groupingBy(dto->dto.getVtId()));


        Map<String, String> vtidMap = new HashMap<>();
        ls.stream().forEach(dto->{
            int size = 0;
            if(vtidGroupMap.get(dto.getVtId())!=null){
                size = vtidGroupMap.get(dto.getVtId()).size();
            }
            String url = "<a href='fileAndVtid?vtid=" + dto.getVtId() + "&file=" + f + "'>"+ dto.getVtId() + "</a>&nbsp;&nbsp;&nbsp;" + size
                    + "<br>" + dto.getRule()
                    + "<br>" + dto.getDefectLevel()
                    + "<br>" + dto.getRuleDesc()
                    + "<br>" + "-------------------------------------------------------"
                    + "<br>";
            vtidMap.put(dto.getVtId(), url);
        });

        StringBuilder stringBuilder = new StringBuilder();
        vtidMap.values().forEach(vtid->stringBuilder.append(vtid));

        return f + ", " + ls.size() + "<br>" + stringBuilder.toString();
    }

    @GetMapping("/files")
    public String files(){
        loadInitList();
        StringBuilder stringBuilder = new StringBuilder();
        list.stream().map(e->{
            String str = "<a href='file?f="+e+"'>"+e+"</a>";
            return str + "<br>";
        }).forEach(stringBuilder::append);
        return stringBuilder.toString();
    }

}
