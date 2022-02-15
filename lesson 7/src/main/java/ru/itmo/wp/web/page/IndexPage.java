package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.annotation.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class IndexPage extends Page {
    private final ArticleService articleService = new ArticleService();
    //protected void action(HttpServletRequest request, Map<String, Object> view) {
      //  pMessage(request, view);
   // }

    //private void putMessage(HttpServletRequest request, Map<String, Object> view) {
     //   String message = (String) request.getSession().getAttribute("message");
    // if (!Strings.isNullOrEmpty(message)) {
      //      view.put("message", message);
        //    request.getSession().removeAttribute("message");
        //}
    //}

    @Json
    private void findAllPosts(HttpServletRequest request, Map<String, Object> view) {
        view.put("articles", articleService.toArticleRecord(articleService.findAllByTime()));
    }
}
