package com.example

import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
class DemoSpec extends Specification {

    @Inject
    EmbeddedApplication<?> application

    @Inject
    StudentRepository studentRepository

    @Inject
    CourseRepository courseRepository

    void 'individual updates should add courses to students'() {
        when:
        Student student = new Student()
        student.setName("Carl")

        Student studentPeter = new Student()
        studentPeter.setName("Peter")

        Course course = new Course()
        course.setTitle("course1")

        def c1 = courseRepository.save(course)

        def studentInserted = studentRepository.save(student)
        def studentInsertedPeter = studentRepository.save(studentPeter)

        def courses = [c1] as Set<Course>
        studentInserted.setCourses(courses)
        studentInsertedPeter.setCourses(courses)

        studentRepository.update(studentInserted)
        studentRepository.update(studentInsertedPeter)

        def found = studentRepository.findById(studentInserted.id)
        def foundPeter = studentRepository.findById(studentInsertedPeter.id)

        then:
        !found.empty
        !found.get().courses.empty
        !foundPeter.empty
        !foundPeter.get().courses.empty
    }

    void 'single update should add courses to students'() {
        when:
        Student student = new Student()
        student.setName("John")

        Student studentJames = new Student()
        studentJames.setName("James")

        Course course = new Course()
        course.setTitle("course3")

        def c1 = courseRepository.save(course)

        def studentInserted = studentRepository.save(student)
        def studentInsertedJames = studentRepository.save(studentJames)

        def courses = [c1] as Set<Course>
        studentInserted.setCourses(courses)
        studentInsertedJames.setCourses(courses)

        studentRepository.updateAll([studentInserted, studentInsertedJames])

        def found = studentRepository.findById(studentInserted.id)
        def foundJames = studentRepository.findById(studentInsertedJames.id)

        then:
        !found.empty
        !found.get().courses.empty
        !foundJames.empty
        !foundJames.get().courses.empty
    }

}
