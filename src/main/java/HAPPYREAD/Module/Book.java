package HAPPYREAD.Module;

/**
 * Created by  on 2016/7/8.
 */
public class Book {
    private String title;
    private String author=null;
    private String brief=null;
    private String totalchars=null;
    private String imageSrc;

    public Book(String title) {
        this.title = title;
        this.imageSrc="@drawable/bookimageblank";
    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.imageSrc="@drawable/bookimageblank";
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getTotalchars() {
        return totalchars;
    }

    public void setTotalchars(String totalchars) {
        this.totalchars = totalchars;
    }
}
