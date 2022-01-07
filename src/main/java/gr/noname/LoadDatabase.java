package gr.noname;


import gr.noname.middleware.entities.Comment;
import gr.noname.middleware.entities.Person;
import gr.noname.middleware.repositories.CommentRepository;
import gr.noname.middleware.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class LoadDatabase {

    //private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    private static final String[] personNames = new String[]{
            "Elpida",
            "Ioanna",
            "Fillipos",
            "George",
            "Thodoris",
            "Diamadis"
    };

    private static final String[] personLastNames = new String[]{
            "Matsiras",
            "L",
            "D",
            "R",
            "P",
            "K"
    };

    private static final String[] addresses = new String[]{
            "El. Venizelou 44",
            "Zaimi 11",
            "Lambrou Katsoni 55",
            "Amfitheas 148",
    };

    private static final String[] randomComments = new String[]{
            "Very good",
            "Thanks",
            "I agree with you",
            "I'm planning a visit",
            "Can you take me a photo",
            "I'm positive"
    };

    @Autowired
    PersonRepository personRepository;
    @Autowired
    CommentRepository commentRepository;


    private static List<Person> generateRandomPeople() {

        List<Person> people = new ArrayList<>();

        int count = getRandomUpperBound(15) +10;

        for (int i = 0; i < count; i++) {

            int g = getRandomUpperBound(personNames.length);
            String name = personNames[g];
            String lastName =personLastNames[getRandomUpperBound(personLastNames.length)];

            Person person = new Person(
                    generaterandomEmail(i, name, lastName),
                    "1234",
                    name,
                    lastName,
                    getGender(g),
                    addresses[getRandomUpperBound(addresses.length)] + " " + getRandomUpperBound(100),
                    "69" + (getRandomUpperBound(9999999)+10000000)
            );

            people.add(person);

        }

        return people;
    }

    private static List<Comment> generateRandomComments(Person person) {

        List<Comment> comments = new ArrayList<>();

        int count = getRandomUpperBound(4) + 1;

        for (int i = 0; i < count; i++) {
            comments.add(
                    new Comment(
                            randomComments[getRandomUpperBound(randomComments.length)],
                            person
                    )
            );
        }
        return comments;
    }


    private static String generaterandomEmail(long id, String name, String lastName) {

        return (name.toLowerCase() + lastName.toLowerCase() + id + getRandomUpperBound(500) + "@gmail.com");
    }


    private static String getGender(int i) {
        if (i < 2) {
            return "Female";
        } else {
            return "Male";
        }
    }

    private static int getRandomUpperBound(int i) {
        return new Random().nextInt(i);
    }


    public void register() {

        List<Person> people = new ArrayList<>();
        List<List<Comment>> comments = new ArrayList<>();

        people = generateRandomPeople();

        personRepository.saveAll(people);

        for(int i=0 ; i<people.size() ; i++){
            comments.add(generateRandomComments(people.get(i)));
        }
        for(int i=0 ; i<comments.size() ; i++){
            commentRepository.saveAll(comments.get(i));
        }

    }


//    @EventListener(ApplicationReadyEvent.class)
//    public void addData() {
//
//        //register();
//
//        //log.info("Preloading " + personRepository.saveAll(generateRandomPeople()));
//        //log.info("Preloading " + commentRepository.saveAll(generateRandomComments()));
//        //log.info("Preloading " + commentRepository.saveAll(generateRandomC(personRepository.findAll())));
//    }


}
