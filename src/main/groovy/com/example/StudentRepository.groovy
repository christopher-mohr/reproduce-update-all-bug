package com.example

import io.micronaut.data.annotation.Join
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface StudentRepository extends CrudRepository<Student, Long> {

    @Override
    @Join(value = "courses", type = Join.Type.FETCH)
    Optional<Student> findById(Long id)

}
