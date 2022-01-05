package gr.noname.middleware.entities;

import lombok.Data;
import javax.persistence.*;


//@Document(collection = "comment") // gia mongo
@Data
@Entity
public class Comment {

//    @Id //gia mongo

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String comment;

    @ManyToOne //(fetch=FetchType.LAZY)
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


//    public Person getPerson() {
//        return person;
//    }
//
//    public void setPerson(Person person) {
//        this.person = person;
//    }
}
