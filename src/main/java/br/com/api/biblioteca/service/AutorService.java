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

        if (autor.getName().equals("")) {
            responseModel.setMensagem("O nome do autor é obrigatório.");
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.BAD_REQUEST);
        } else if (autor.getNationality().equals("")) {
            responseModel.setMensagem("A nacionalidade do autor é obrigatória.");
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<AutorModel>(autorRepository.save(autor), HttpStatus.CREATED);
        }

    }
    
    // Metodo para atualizar um autor existente
    public ResponseEntity<?> update(long id, AutorModel autor) {
        if (!autorRepository.existsById(id)) {
            responseModel.setMensagem("Autor não encontrado.");
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.NOT_FOUND);
        } else if (autor.getName().equals("")) {
            responseModel.setMensagem("O nome do autor é obrigatório.");
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.BAD_REQUEST);
        } else if (autor.getNationality().equals("")) {
            responseModel.setMensagem("A nacionalidade do autor é obrigatória.");
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.BAD_REQUEST);
        } else {
            autor.setId(id);
            return new ResponseEntity<AutorModel>(autorRepository.save(autor), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> delete(long id) {
        autorRepository.deleteById(id);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMensagem("Autor deletado com sucesso.");
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }
    
}
