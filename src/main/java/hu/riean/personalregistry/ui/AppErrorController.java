package hu.riean.personalregistry.ui;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 *
 * @author riean
 */
//@Controller
@ControllerAdvice
public class AppErrorController {

    Logger logger = LoggerFactory.getLogger(AppErrorController.class);

    @ExceptionHandler(NoResourceFoundException.class)
    public ModelAndView handleNoResourceFoundException(HttpServletRequest request, NoResourceFoundException ex) {
        logger.error("ss");
        return createMV(404);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, Exception ex) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        int statusCode = 0;
        if (status != null) {
            statusCode = Integer.parseInt(status.toString());
        }

        return createMV(statusCode);
    }

    private ModelAndView createMV(int statusCode) {
        ModelAndView mv = new ModelAndView();
        String subTitle = "";
        if (400 <= statusCode && statusCode < 500) {
            subTitle = "Hibás kérés";
        }
        if (500 <= statusCode && statusCode < 600) {
            subTitle = "Szerveroldali hiba";
        }
        if (statusCode == 404) {
            subTitle = "Nem takálható";
        }

        mv.addObject("subTitle", subTitle);
        mv.setViewName("error");
        return mv;
    }

}
