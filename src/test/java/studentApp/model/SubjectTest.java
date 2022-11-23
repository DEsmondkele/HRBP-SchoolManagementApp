package studentApp.model;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;


public class SubjectTest {
    ClassSubject subject;
    @Before
    public void setUp(){
        subject = new ClassSubject(1, "English", 70.5);

    }

    @Test
    public void newClassSubjectsCanBeCreatedTest(){
        subject.setName("Math");
        subject.setScore(60.5);
  Assertions.assertThat(subject);
  Assertions.assertThat(subject.getName());
  Assertions.assertThat(subject.getScore());
    }
}
