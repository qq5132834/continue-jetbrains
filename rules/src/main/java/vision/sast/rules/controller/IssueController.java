package vision.sast.rules.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IssueController {

    @GetMapping("")
    public  String list(){
        return "index";
    }


}
