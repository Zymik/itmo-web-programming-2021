package ru.itmo.web.lesson4.util;

import ru.itmo.web.lesson4.model.Color;
import ru.itmo.web.lesson4.model.Post;
import ru.itmo.web.lesson4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", Color.GREEN),
            new User(6, "pashka", "Pavel Mavrin", Color.RED),
            new User(9, "geranazarov555", "Georgiy Nazarov", Color.BLUE),
            new User(11, "tourist", "Gennady Korotkevich", Color.RED)
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "Codeforces Round #743 Is Unrated: Investigating the Reason", """
                    Hello.

                    Sorry for it. Actually, right now I don't have any idea why testing is so slow now. Obviously, it doesn't work as expected. It is not because of problems. So please don't blame writers or coordinators. I'm investigating the reason, but don't see it now.

                    I myself am very upset and I apologize to the authors, coordinator, and participants. I will do my best to find the cause and fix it. Surprisingly, we haven't made any big changes to this part of the system lately. But something went wrong.

                    Mike.""", 1),

            new Post(2, "Codeforces Round #12 failed", """
                    Hello!

                    Tired to rest? Your help is needed.

                    During the period from December 31 until today I have made many changes in the Codeforces. The changes affected only the interior, so you will not notice any change in the user interface. Nevertheless, during this time was changed about 150 project files, database schema, some principles of data storage. In short, after all this, I'm not sure that everything works as expected (although, of course, I spent time on testing). For this reason I decided to organize Codeforces Testing Round #1, which will unrated and his only goal - a comprehensive testing of the project in conditions close to the real contest.

                    It will be three problems (possibly well-known, folklore), but I hope it will be pleasure to solve them.

                    UPD: Thanks to all. The round finished. We didn't notice any bugs. Write suggestions and opinions in the comments.""", 6),

            new Post(3, "Educational Codeforces Round 111 [Rated for Div. 2]", """
                    Hello Codeforces!

                    On Wednesday, July 14, 2021 at 17:35 Educational Codeforces Round 111 (Rated for Div. 2) will start.

                    Series of Educational Rounds continue being held as Harbour.Space University initiative! You can read the details about the cooperation between Harbour.Space University and Codeforces in the blog post.

                    This round will be rated for the participants with rating lower than 2100. It will be held on extended ICPC rules. The penalty for each incorrect submission until the submission with a full solution is 10 minutes. After the end of the contest you will have 12 hours to hack any solution you want. You will have access to copy any solution and test it locally.

                    You will be given 6 or 7 problems and 2 hours to solve them.

                    The problems were invented and prepared by Adilbek adedalic Dalabaev, Vladimir vovuh Petrov, Ivan BledDest Androsov, Maksim Neon Mescheryakov and me. Also huge thanks to Mike MikeMirzayanov Mirzayanov for great systems Polygon and Codeforces.

                    Good luck to all the participants!""", 11),

            new Post(4, "Hello everybody", "Hello everybody!", 11)
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("posts", POSTS);
        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }
}
