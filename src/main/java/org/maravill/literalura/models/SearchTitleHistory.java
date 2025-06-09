package org.maravill.literalura.models;

import jakarta.persistence.*;

@Entity
@Table(name = "search_title_history")
public class SearchTitleHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    public SearchTitleHistory() {
    }

    public SearchTitleHistory(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public SearchTitleHistory(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
