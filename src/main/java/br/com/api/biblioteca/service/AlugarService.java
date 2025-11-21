package br.com.api.biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.biblioteca.model.AlugarModel;
import br.com.api.biblioteca.model.LivroModel;
import br.com.api.biblioteca.model.ResponseModel;
import br.com.api.biblioteca.model.UserModel;
import br.com.api.biblioteca.repository.AlugarRepository;
import br.com.api.biblioteca.repository.LivroRepository;
import br.com.api.biblioteca.repository.UserRepository;

@Service
public class AlugarService {

    private final ResponseModel responseModel;
    @Autowired
    private AlugarRepository alugarRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UserRepository userRepository;

    AlugarService(ResponseModel responseModel) {
        this.responseModel = responseModel;
    }

    // Metodo para listar todos os alugueis
    public Iterable<AlugarModel> listar() {
        return alugarRepository.findAll();
    }

    // Metodo para cadastrar um novo aluguel
    public ResponseEntity<?> create(AlugarModel alugar) {
        ResponseModel response = validarAluguel(alugar);
        if (response != null) {
            return ResponseEntity.badRequest().body(response);
        }
        if (alugar.getUser() != null && alugar.getUser().getId() != null &&
                alugar.getLivro() != null && alugar.getLivro().getId() != null) {
            UserModel user = userRepository.findById(alugar.getUser().getId())
                    .orElse(null);
            LivroModel livro = livroRepository.findById(alugar.getLivro().getId())
                    .orElse(null);
            if (user == null || livro == null) {
                ResponseModel erro = new ResponseModel();
                erro.setMensagem("Usuario ou Livro não encontrado.");
                return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
            }
            alugar.setUser(user);
            alugar.setLivro(livro);
        }
        AlugarModel novoAluguel = alugarRepository.save(alugar);
        return new ResponseEntity<>(novoAluguel, HttpStatus.CREATED);
    }
    
    // Metodo para atualizar um aluguel existente
    public ResponseEntity<?> update(long id, AlugarModel alugar) {
        if (!alugarRepository.existsById(id)) {
            ResponseModel erro = new ResponseModel();
            erro.setMensagem("alugel não encontrado");
            return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
        }

        ResponseModel response = validarAluguel(alugar);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (alugar.getUser() != null && alugar.getUser().getId() != null &&
                alugar.getLivro() != null && alugar.getLivro().getId() != null) {
            UserModel user = userRepository.findById(alugar.getUser().getId())
                    .orElse(null);
            LivroModel livro = livroRepository.findById(alugar.getLivro().getId())
                    .orElse(null);
            if (user == null || livro == null) {
                ResponseModel erro = new ResponseModel();
                erro.setMensagem("Usuario ou Livro não encontrado.");
                return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
            }
            alugar.setUser(user);
            alugar.setLivro(livro);
        }
        alugar.setId(id);
        AlugarModel aluguelAtualizado = alugarRepository.save(alugar);
        return new ResponseEntity<>(aluguelAtualizado, HttpStatus.OK);
    }

    // Deletar aluguel
    public ResponseEntity<?> delete(long id) {
        if (!alugarRepository.existsById(id)) {
            ResponseModel erro = new ResponseModel();
            erro.setMensagem("Aluguel não encontrado!");
            return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
        }

        alugarRepository.deleteById(id);
        ResponseModel sucesso = new ResponseModel();
        responseModel.setMensagem("Aluguel excluido com sucesso.");
        return new ResponseEntity<>(sucesso, HttpStatus.OK);
    }

    // Metodo para validar centralizada
    private ResponseModel validarAluguel(AlugarModel alugar) {
        ResponseModel response = new ResponseModel();

        if (alugar.getDataAluguel() == null) {
            response.setMensagem("Data de aluguel é obrigatória.");
        } else if (alugar.getDataDevolucao() == null) {
            response.setMensagem("Data de devolução é obrigatória.");
        } else if (alugar.getUser() == null || alugar.getUser().getId() == null) {
            response.setMensagem("O usuario que alugou é obrigatorio");
        } else if (alugar.getLivro() == null || alugar.getLivro().getId() == null) {
            response.setMensagem("O livro alugado é obrigatorio");
        } else {
            return null; // sem erros
        }
        
        return response;
    }
}
