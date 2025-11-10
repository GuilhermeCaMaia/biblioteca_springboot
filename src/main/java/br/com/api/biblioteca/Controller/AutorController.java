package br.com.api.biblioteca.Controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.api.biblioteca.model.AutorModel;
import br.com.api.biblioteca.service.AutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@CrossOrigin(origins = "*")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @PostMapping("/autor/create")
    public ResponseEntity<?> create(@RequestBody AutorModel autor) {
        return autorService.create(autor);
    }
    
    @PutMapping("/autor/update/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody AutorModel autor) {
        return autorService.update(autor.getId(), autor);
    }

    @DeleteMapping("/autor/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        return autorService.delete(id);
    }

    @GetMapping("/autor/list")
    public Iterable<AutorModel> listar() {
        return autorService.listar();
    }
    

    // @GetMapping("/autor/")
    // public String rota() {
    //     return "Hello World";
    // }

}