package vision.sast.rules.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HightLightController {


    @GetMapping("highLight")
    public synchronized String highLight(String file) {

        return "editing.";
    }

}
