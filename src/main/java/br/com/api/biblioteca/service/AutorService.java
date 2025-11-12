package br.com.api.biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.biblioteca.model.AutorModel;
import br.com.api.biblioteca.model.ResponseModel;
import br.com.api.biblioteca.repository.AutorRepository;

@Service
public class AutorService {
    
    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private ResponseModel responseModel;

    // Metodo para listar todos os autores
    public Iterable<AutorModel> listar() {
        return autorRepository.findAll();
    }

    // Metodo para cadastrar um novo autor
    public ResponseEntity<?> create(AutorModel autor) {
        ResponseModel response = validarAutor(autor);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        AutorModel novoAutor = autorRepository.save(autor);
        return new ResponseEntity<>(novoAutor, HttpStatus.CREATED);
    }
    
    // Metodo para atualizar um autor existente
    public ResponseEntity<?> update(long id, AutorModel autor) {
        if (!autorRepository.existsById(id)) {
            ResponseModel erro = new ResponseModel();
            erro.setMensagem("Autor Não encontrado");
            return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
        }

        ResponseModel response = validarAutor(autor);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        autor.setId(id);
        AutorModel autorAtualizado = autorRepository.save(autor);
        return new ResponseEntity<>(autorAtualizado, HttpStatus.OK);
    }

    // Deletar autor
    public ResponseEntity<?> delete(long id) {
        if (!autorRepository.existsById(id)) {
            ResponseModel erro = new ResponseModel();
            erro.setMensagem("Autor não encontrado");
            return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
        }

        autorRepository.deleteById(id);
        ResponseModel sucesso = new ResponseModel();
        responseModel.setMensagem("Autor deletado com sucesso.");
        return new ResponseEntity<>(sucesso, HttpStatus.OK);
    }

    // Método privado para validação centralizada
    private ResponseModel validarAutor(AutorModel autor) {
        ResponseModel response = new ResponseModel();

        if (autor.getName() == null || autor.getName().isEmpty()){
            response.setMensagem("O nome do autor é obrigatorio.");
        } else if (autor.getNationality() == null || autor.getNationality().isEmpty()) {
            response.setMensagem("A nacionalidade do autor é obrigatoria.");
        } else {
            return null; // Tudo certo
        }

        return response;
    }
}
