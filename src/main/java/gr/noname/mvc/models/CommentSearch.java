package gr.noname.mvc.models;

import org.springframework.stereotype.Component;

@Component
public class CommentSearch {
    private String comment;

    public CommentSearch() { }

    public CommentSearch(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
