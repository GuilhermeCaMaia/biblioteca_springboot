package br.com.api.biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.biblioteca.model.AutorModel;
import br.com.api.biblioteca.model.LivroModel;
import br.com.api.biblioteca.model.ResponseModel;
import br.com.api.biblioteca.repository.AutorRepository;
import br.com.api.biblioteca.repository.LivroRepository;

@Service
public class LivroService {
    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private ResponseModel responseModel;

    // Metodo para listar todos os livros
    public Iterable<LivroModel> listar() {
        return livroRepository.findAll();
    }

    public ResponseEntity<?> create(LivroModel livro) {
        ResponseModel response = validarLivro(livro);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
        if (livro.getAutor() != null && livro.getAutor().getId() != null) {
            AutorModel autor = autorRepository.findById(livro.getAutor().getId())
                    .orElse(null);
            if (autor == null) {
                ResponseModel erro = new ResponseModel();
                erro.setMensagem("Autor não encontrado.");
                return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
            }
            livro.setAutor(autor);
        }

        LivroModel novoLivro = livroRepository.save(livro);
        return new ResponseEntity<>(novoLivro, HttpStatus.CREATED);
    }

    // Metodo para atualizar um livro existente
    public ResponseEntity<?> update(long id, LivroModel livro) {
        if (!livroRepository.existsById(id)) {
            ResponseModel erro = new ResponseModel();
            erro.setMensagem("Livro Não encontrado");
            return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
        }
        ResponseModel response = validarLivro(livro);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (livro.getAutor() != null && livro.getAutor().getId() != null) {
            AutorModel autor = autorRepository.findById(livro.getAutor().getId())
                    .orElse(null);
            if (autor == null) {
                ResponseModel erro = new ResponseModel();
                erro.setMensagem("Autor não encontrado.");
                return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
            }
            livro.setAutor(autor);
        }
        livro.setId(id);
        LivroModel livroAtualizado = livroRepository.save(livro);
        return new ResponseEntity<>(livroAtualizado, HttpStatus.OK);
    }

    // Deletar livro
    public ResponseEntity<?> delete(long id) {
        if (!livroRepository.existsById(id)) {
            ResponseModel erro = new ResponseModel();
            erro.setMensagem("Livro Não encontrado");
            return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
        }

        livroRepository.deleteById(id);
        ResponseModel sucesso = new ResponseModel();
        responseModel.setMensagem("Livro deletado com sucesso.");
        return new ResponseEntity<>(sucesso, HttpStatus.OK);
    }

    // Método privado para validação centralizada
    private ResponseModel validarLivro(LivroModel livro) {
        ResponseModel response = new ResponseModel();

        if (livro.getTitle() == null || livro.getTitle().isBlank()) {
            response.setMensagem("O título do livro é obrigatório.");
        } else if (livro.getSynopsis() == null || livro.getSynopsis().isBlank()) {
            response.setMensagem("A sinopse do livro é obrigatória.");
        } else if (livro.getGenre() == null || livro.getGenre().isBlank()) {
            response.setMensagem("O gênero do livro é obrigatório.");
        } else if (livro.getYear() == null || livro.getYear() <= 0) {
            response.setMensagem("O ano do livro é obrigatório.");
        } else if (livro.getAutor() == null || livro.getAutor().getId() == null) {
            response.setMensagem("O autor do livro é obrigatório.");
        } else {
            return null; // Tudo certo
        }

        return response;
    }

}
