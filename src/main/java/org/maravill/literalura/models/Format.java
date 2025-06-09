package org.maravill.literalura.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "formats")
public class Format {

    @Id
    @GeneratedValue
    private Long id;

    private String mimeType;
    private String url;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    public Format() {
    }

    public Format(String mimeType, String url) {
        this.mimeType = mimeType;
        this.url = url;
    }

    public Format(Long id, String mimeType, String url) {
        this.id = id;
        this.mimeType = mimeType;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
