package hu.riean.personalregistry.ui;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author riean
 */
@Controller
@RequestMapping("/person")
public class PersonUiController {

    @GetMapping("/list")
    public ModelAndView getAll() throws IOException {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("personList");
        return mv;
    }
}
