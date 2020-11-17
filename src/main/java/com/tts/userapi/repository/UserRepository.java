package com.tts.userapi.repository;

import com.tts.userapi.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Long>{

    User findByState(String state);
}
