package studentApp.model;

import io.vertx.core.json.JsonObject;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Student {
    @Id
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @ManyToMany
    private List<ClassSubject> subjects;
    @ManyToOne
    private List<Class> classes;

    public Student(Integer id, String firstName, String lastName, String email, String password) {

    }

    public Student(String firstname, String lastname, String email,String password) {

    }

    public Student(JsonObject entries) {

    }
}
