package br.com.api.biblioteca.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import br.com.api.biblioteca.model.LivroModel;

@Repository
public interface LivroRepository extends CrudRepository<LivroModel, Long> {
	
}
