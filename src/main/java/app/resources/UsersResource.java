package app.resources;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class UsersResource {

    @RequestMapping("/api/users/")
    public String index() {
        return "Greetings from Springs Boot!";
    }
}