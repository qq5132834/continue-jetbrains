package vision.sast.rules.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vision.sast.rules.RulesApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class FileController {

    public static List<String> list;

    public synchronized static void loadInitList() {
        if(list==null){
            Set<String> set = RulesApplication.ISSUE_RESULT.getResult().stream().map(dto->dto.getFilePath()).collect(Collectors.toSet());
            list = set.stream().toList().stream().sorted().toList();
        }
    }

    @GetMapping("file")
    public String file(String f){
        return f;
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
