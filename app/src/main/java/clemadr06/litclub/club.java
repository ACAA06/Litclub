package clemadr06.litclub;

/**
 * Created by Clement Adriean on 18-07-2018.
 */

public class club {
    private String title;
    private String subtitle;
    private String author;
    private int id;

    public club(String title, String subtitle, String author,int id) {
        this.title = title;
        this.subtitle = subtitle;
        this.author=author;
        this.id=id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }
    public String getAuthor() {
        return author;
    }
    public int getId() {
        return id;
    }
}