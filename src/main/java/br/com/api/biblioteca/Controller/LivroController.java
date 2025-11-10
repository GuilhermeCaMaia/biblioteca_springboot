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

import br.com.api.biblioteca.model.LivroModel;
import br.com.api.biblioteca.service.LivroService;

@RestController
@CrossOrigin(origins = "*")
public class LivroController {
    
    @Autowired
    private LivroService livroService;

    @PostMapping("/livro/create")
    public ResponseEntity<?> create(@RequestBody LivroModel livro) {
        return livroService.create(livro);
    }

    @PutMapping("/livro/update/{id}")
    public ResponseEntity<?> update(@RequestBody LivroModel livro) {
        return livroService.update(livro.getId(), livro);
    }

    @DeleteMapping("/livro/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        return livroService.delete(id);
    }

    @GetMapping("/livro/list")
    public Iterable<LivroModel> listar() {
        return livroService.listar();
    }
}
