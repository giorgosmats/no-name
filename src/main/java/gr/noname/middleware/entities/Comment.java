package gr.noname.middleware.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;



@Getter
@Setter
//@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person ;


    public Comment() {

    }

    public Comment(String comment) {
        this.comment = comment;
    }

    public Comment(String comment, Person person) {
        this.comment = comment;
        this.person = person;
    }

}
