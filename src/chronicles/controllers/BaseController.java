package chronicles.controllers;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class BaseController extends MultiActionController{
    protected HttpSession chroniclesSession ;


    @Override
    protected void initBinder(javax.servlet.http.HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);    //To change body of overridden methods use File | Settings | File Templates.
        this.chroniclesSession = request.getSession();
    }


}
