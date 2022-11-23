package studentApp.model;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Setter
@Getter
@Table
public class Class {
    @Id
    private int id;
    @OneToMany
    private List<ClassSubject>subjects;
    @OneToMany
    private List<Student> students;
    @ManyToMany
    private List<Staff>staffs;

    public Class(int id, List<ClassSubject> subjects, List<Student> students, List<Staff> staffs) {
        this.id = id;
        this.subjects = subjects;
        this.students = students;
        this.staffs = staffs;
    }
}
