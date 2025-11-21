package br.com.api.biblioteca.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.biblioteca.model.UserModel;
import br.com.api.biblioteca.service.UserService;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user/create")
    public ResponseEntity<?> create(@RequestBody UserModel user) {
        return userService.create(user);
    }

    @PutMapping("/user/update/{id}")
    public ResponseEntity<?> update(@RequestBody UserModel user) {
        return userService.update(user.getId(), user);
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        return userService.delete(id);
    }

    @GetMapping("/user/list")
    public Iterable<UserModel> listar() {
        return userService.listar();
    }
}
