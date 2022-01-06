package gr.noname.middleware.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
//@Getter
//@Setter
@Entity
//@Document(collection = "person") //gia mongo
public class Person {

    //@Id // gia mongo
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true)
    @Pattern(regexp = "^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$", message = "This is not an email")
    private String email;
    @NotBlank(message = "Can't be empty")
    private String password;
    @NotBlank(message = "Can't be empty")
    private String firstName;
    @NotBlank(message = "Can't be empty")
    private String lastName;
    @NotBlank(message = "Can't be empty")
    private String gender;
    @NotBlank(message = "Can't be empty")
    private String address;
    @NotBlank(message = "Can't be empty")
    private String contactDetails;

//    @DBRef // gia mongo
//    @JsonBackReference // gia mongo
//    private List<Comment> comments; // gia mongo

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    @JsonBackReference
    private Set<Comment> comments = new HashSet<>();


    public Person() {

    }

    public Person(String email, String password, String firstName, String lastName, String gender, String address, String contactDetails) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.address = address;
        this.contactDetails = contactDetails;
    }


}
