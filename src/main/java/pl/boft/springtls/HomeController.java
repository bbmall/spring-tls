package pl.boft.springtls;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
class HomeController {

    @GetMapping("/")
    ResponseEntity goHome() {
        return ResponseEntity.ok("All is good!");
    }
}
