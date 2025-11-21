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

import br.com.api.biblioteca.model.AlugarModel;
import br.com.api.biblioteca.service.AlugarService;
import br.com.api.biblioteca.service.LivroService;
import br.com.api.biblioteca.service.UserService;

@RestController
@CrossOrigin(origins = "*")
public class AlugarController {
    @Autowired
    private AlugarService alugarService;

    @PostMapping("/alugar/create")
    public ResponseEntity<?> create(@RequestBody AlugarModel alugar) {
        return alugarService.create(alugar);
    }

    @PutMapping("/alugar/update/{id}")
    public ResponseEntity<?> update(@RequestBody AlugarModel alugar) {
        return alugarService.update(alugar.getId(), alugar);
    }

    @DeleteMapping("/alugar/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        return alugarService.delete(id);
    }

    @GetMapping("/alugar/list")
    public Iterable<AlugarModel> listar() {
        return alugarService.listar();
    }
}
