package gr.noname.mvc.models;

import org.springframework.stereotype.Component;

@Component
public class PersonSearch {
//    private String email;
//
//    public PersonSearch() { }
//
//    public PersonSearch(String email) {
//        this.email = email;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    private String query;

    public PersonSearch(){}
    public PersonSearch(String query) {
        this.query = query;
    }
}
