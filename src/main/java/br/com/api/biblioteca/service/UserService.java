package br.com.api.biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.biblioteca.model.UserModel;
import br.com.api.biblioteca.model.ResponseModel;
import br.com.api.biblioteca.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResponseModel responseModel;

    // Metodo para listar todos os usuarios
    public Iterable<UserModel> listar() {
        return userRepository.findAll();
    }

    // Metodo para cadastrar um novo usuario
    public ResponseEntity<?> create(UserModel user) {
        ResponseModel response = validarUser(user);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        UserModel novoUser = userRepository.save(user);
        return new ResponseEntity<>(novoUser, HttpStatus.CREATED);
    }

    // Metodo para atualizar um usuario existente
    public ResponseEntity<?> update(long id, UserModel user) {
        if (!userRepository.existsById(id)) {
            ResponseModel erro = new ResponseModel();
            erro.setMensagem("Usuario não encontrado!");
            return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
        }

        ResponseModel response = validarUser(user);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        user.setId(id);
        UserModel updatedUser = userRepository.save(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    
    // Metodo para deletar um usuario
    public ResponseEntity<?> delete(long id) {
        if (!userRepository.existsById(id)) {
            ResponseModel erro = new ResponseModel();
            erro.setMensagem("Usuario não encontrado!");
            return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
        }

        userRepository.deleteById(id);
        ResponseModel sucesso = new ResponseModel();
        responseModel.setMensagem("Usuario deletado com sucesso.");
        return new ResponseEntity<>(sucesso, HttpStatus.OK);
    }

    // Metodo para validar os dados do usuario
    private ResponseModel validarUser(UserModel user) {
        ResponseModel response = new ResponseModel();

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            response.setMensagem("O campo email é obrigatório.");
        }
        else if (user.getPassword() == null || user.getPassword().isEmpty()) {
            response.setMensagem("O campo senha é obrigatório.");
        }
        else {
            return null; // Dados válidos
        }
        return response;
    }

}
