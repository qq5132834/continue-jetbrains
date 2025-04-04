package vision.sast.rules.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ResourcesController {


    @RequestMapping("highlight.css")
    public String css(){
        return "highlight.css";
    }

}
