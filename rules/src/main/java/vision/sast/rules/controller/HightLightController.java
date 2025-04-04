package vision.sast.rules.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vision.sast.rules.utils.HighLightUtil;

@RestController
public class HightLightController {


    @GetMapping("highLight")
    public synchronized String highLight(String file) {
        try {
            return HighLightUtil.highlight(file);
        }
        catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

}
