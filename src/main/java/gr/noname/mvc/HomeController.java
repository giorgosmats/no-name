package gr.noname.mvc;

import gr.noname.LoadDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    LoadDatabase data ;

    @GetMapping("/")
    public String home() {

        return "home";
    }

    @PostMapping("/generate")
    public String loadData(){

        data.register();

        return "home";
    }
}
