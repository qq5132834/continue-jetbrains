package vision.sast.rules.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DynamicController {

    @GetMapping("/")
    public String dynamic() {
//        model.addAttribute("title", "动态加载标题");
//        model.addAttribute("content", "动态加载内容");
        return "index";
    }

}
