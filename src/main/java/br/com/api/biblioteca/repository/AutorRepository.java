package br.com.api.biblioteca.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import br.com.api.biblioteca.model.AutorModel;

@Repository
public interface AutorRepository extends CrudRepository<AutorModel, Long> {
    
}
