package com.example

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.Relation
import io.micronaut.data.model.naming.NamingStrategies


@MappedEntity(namingStrategy = NamingStrategies.LowerCase.class)
class Course {

    /**
     * The database id
     */
    @GeneratedValue
    @Id
    private Long id

    String title

    @Relation(value = Relation.Kind.MANY_TO_MANY, mappedBy = "courses")
    Set<Student> students

    Course() {
    }

    Long getId() {
        return id
    }

    void setId(Long id) {
        this.id = id
    }

    Set<Student> getStudents() {
        return students
    }

    void setStudents(Set<Student> students) {
        this.students = students
    }

    String getTitle() {
        return title
    }

    void setTitle(String title) {
        this.title = title
    }
}
