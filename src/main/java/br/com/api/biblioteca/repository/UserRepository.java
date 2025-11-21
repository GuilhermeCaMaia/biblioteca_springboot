package br.com.api.biblioteca.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.api.biblioteca.model.UserModel;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Long> {
    
}
