package com.intern.repo;

import com.intern.data.Pep;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PepRepository extends MongoRepository<Pep, String>, CustomPepRepository {
}
