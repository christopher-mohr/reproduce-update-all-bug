package com.example

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.Relation
import io.micronaut.data.model.naming.NamingStrategies
import io.micronaut.data.jdbc.annotation.JoinColumn
import io.micronaut.data.jdbc.annotation.JoinTable

@MappedEntity(namingStrategy = NamingStrategies.LowerCase.class)
class Student {

    /**
     * The database id
     */
    @GeneratedValue
    @Id
    private Long id

    String name

    @JoinTable(name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @Relation(value = Relation.Kind.MANY_TO_MANY, cascade = Relation.Cascade.UPDATE)
    Set<Course> courses

    Student() {
    }

    Long getId() {
        return id
    }

    void setId(Long id) {
        this.id = id
    }

    Set<Course> getCourses() {
        return courses
    }

    void setCourses(Set<Course> courses) {
        this.courses = courses
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }
}
