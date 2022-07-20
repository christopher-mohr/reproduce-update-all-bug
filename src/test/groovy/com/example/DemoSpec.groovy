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

        Course course2 = new Course()
        course2.setTitle("course2")

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

        Student studentPeter = new Student()
        studentPeter.setName("James")

        Course course = new Course()
        course.setTitle("course3")

        Course course2 = new Course()
        course2.setTitle("course4")

        def c1 = courseRepository.save(course)

        def studentInserted = studentRepository.save(student)
        def studentInsertedPeter = studentRepository.save(studentPeter)

        def courses = [c1] as Set<Course>
        studentInserted.setCourses(courses)
        studentInsertedPeter.setCourses(courses)

        studentRepository.updateAll([studentInserted, studentInsertedPeter])

        def found = studentRepository.findById(studentInserted.id)
        def foundPeter = studentRepository.findById(studentInsertedPeter.id)

        then:
        !found.empty
        !found.get().courses.empty
        !foundPeter.empty
        !foundPeter.get().courses.empty
    }

}
