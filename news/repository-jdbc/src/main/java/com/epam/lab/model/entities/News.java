package com.epam.lab.model.entities;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "news")
@NamedQueries({
        @NamedQuery(name = "News.findByAuthor", query = "SELECT n FROM News n where n.author = :author "),
        @NamedQuery(name = "News.findByCreationDate", query = "SELECT n FROM News n where n.creationDate = :creationDate ")
})
public class News implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @NotNull
    @Length(max = 50)
    private String title;

    @NotNull
    @Length(max = 100)
    private String shortText;

    @NotNull
    @Length(max = 1000)
    private String fullText;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String creationDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String modificationDate;

    @ManyToOne
    private Author author;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "news_tag",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return id == news.id &&
                Objects.equals(title, news.title) &&
                Objects.equals(shortText, news.shortText) &&
                Objects.equals(fullText, news.fullText) &&
                Objects.equals(creationDate, news.creationDate) &&
                Objects.equals(modificationDate, news.modificationDate) &&
                Objects.equals(tags, news.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, shortText, fullText, creationDate, modificationDate, tags);
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", shortText='" + shortText + '\'' +
                ", fullText='" + fullText + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", modificationDate='" + modificationDate + '\'' +
                ", author=" + author +
                ", tags=" + tags +
                '}';
    }
}
