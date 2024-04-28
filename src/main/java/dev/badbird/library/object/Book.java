package dev.badbird.library.object;

import dev.badbird.library.Main;
import dev.badbird.library.util.Utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;

public class Book implements Serializable {
    private static final long serialVersionUID = -1;

    @FieldName("ISBN")
    private long id; // ISBN
    private String title;
    private String author;
    private String publisher;
    private Date copyright, published;
    @Price
    private double price;
    private Genre genre;
    private Category category;


    public String getFieldName(Field field) {
        // get the user-friendly name of the field to display in the table
        return field.isAnnotationPresent(FieldName.class) ? field.getAnnotation(FieldName.class).value()
                : Utils.capitalizeFirst(Utils.camelCaseToWords(field.getName()));
    }

    public boolean isInvalidField(Field field) {
        // should we show this field in the table?
        return field.isAnnotationPresent(Hidden.class) || Modifier.isStatic(field.getModifiers());
    }

    public void printInfo() {
        // prints all fields of the book in a table
        String table = "| %-15s | %-45s |%n";
        String separator = "-".repeat(67) + "%n";
        System.out.printf(separator);
        System.out.printf(table, "Field", "Value");
        System.out.printf(separator);
        Field[] declaredFields = getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            if (isInvalidField(declaredField)) continue;
            String name = getFieldName(declaredField);
            String value;
            try {
                if (declaredField.getType() == Date.class) {
                    value = Main.DATE_FORMAT.format(declaredField.get(this));

                } else {
                    String prefix = "";
                    if (declaredField.isAnnotationPresent(Price.class)) prefix = "$";
                    value = prefix + declaredField.get(this).toString();
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            System.out.printf(table, name, value);
        }
        System.out.printf(separator);
    }

    public Book(long id, String title, String author, String publisher, Date copyright, Date published, double price, Genre genre, Category category) {
        if (!Utils.isValidISBN(id)) { // check if isbn is valid
            throw new IllegalArgumentException("Invalid ISBN!");
        }
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.copyright = copyright;
        this.published = published;
        this.price = price;
        this.genre = genre;
        this.category = category;
    }

    /* Getters / Setters cause I can't use lombok :( */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getCopyright() {
        return copyright;
    }

    public void setCopyright(Date copyright) {
        this.copyright = copyright;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
