package br.com.api.biblioteca.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.api.biblioteca.model.AlugarModel;

public interface AlugarRepository extends CrudRepository<AlugarModel, Long>{
    
}
