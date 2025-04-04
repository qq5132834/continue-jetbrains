package vision.sast.rules.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ResourcesController {


    @RequestMapping("cpp.css")
    public String cpp_css(){
        return "cpp.css";
    }

}
