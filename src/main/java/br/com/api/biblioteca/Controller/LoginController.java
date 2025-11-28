package br.com.api.biblioteca.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.biblioteca.DTO.LoginDTO;
import br.com.api.biblioteca.model.UserModel;
import br.com.api.biblioteca.repository.UserRepository;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login) {
        UserModel user = userRepository.findByEmail(login.getEmail());
    
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuário não encontrado");
        }

        if (user.getPassword().equals(login.getPassword())) {
            user.setPassword(null);
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("senha incorreta");
    }
}
