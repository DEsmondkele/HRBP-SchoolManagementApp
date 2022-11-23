package studentApp.model;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
public class Staff {
    @Id
    private int id;

    private String firstName;
    private String lastName;
    @NotNull
    @Email
    private String email;
    @NotBlank
    @NotNull
    private String password;
    @OneToMany
    private List<Student> students;
    @ManyToMany
    private List<Class>classes;
}
