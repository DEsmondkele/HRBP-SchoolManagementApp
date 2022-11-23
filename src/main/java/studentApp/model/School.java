package studentApp.model;

import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class School {
    private int id;
    @OneToMany
    private List<Student> students;
    @OneToMany
    private List<Class> classes;
    @OneToMany
    private List<ClassSubject> subjects;

}
