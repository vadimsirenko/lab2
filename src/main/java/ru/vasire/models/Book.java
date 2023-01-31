package ru.vasire.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {

    private static int DAYS_TO_READ = 10;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Поле наименование обязательное")
    @Size(min = 2, max = 100, message = "Поле наименование должно содержать от 2 до 100 символов")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Поле автор обязательное")
    @Size(min = 2, max = 100, message = "Поле автор должно содержать от 2 до 100 символов")
    @Column(name = "author")
    private String author;

    @Min(value = 1900, message = "Значение года публикации должно быть больше 1900")
    @Column(name = "publicationYear")
    private int publication;

    @Column(name = "checkOutDate")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat( pattern = "dd.MM.yyyy hh:mm:ss")
    private Date checkOutDate;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "personId", referencedColumnName = "id")
    private Person owner;

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @Transient
    private int personId;

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public String OverdueClass()
    {
        if(checkOutDate == null)
            return "norm";
        Duration duration = Duration.between(checkOutDate.toInstant(), Instant.now());
        if(duration.toDays() > DAYS_TO_READ)
            return "overdue";
        return "norm";
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public String getPersonName() {
        if(owner==null)
            return "";
        return owner.getName();
    }


    public Book(String name, String author, int publication) {
        this.name = name;
        this.author = author;
        this.publication = publication;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublication() {
        return publication;
    }

    public void setPublication(int publication) {
        this.publication = publication;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", publication=" + publication +
                ", checkOutDate=" + checkOutDate +
                '}';
    }
}
