package hu.riean.personalregistry.ui;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/add")
    public ModelAndView add() throws IOException {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("personOne");
        mv.addObject("id", null);
        mv.addObject("add", true);
        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView one(@PathVariable long id) throws IOException {
        ModelAndView mv = new ModelAndView();
        mv.addObject("id", id);
        mv.addObject("add", false);
        mv.setViewName("personOne");
        return mv;
    }
}
