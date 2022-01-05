package gr.noname.middleware.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


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
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private String address;
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
