package com.example.contentms;

import jakarta.persistence.*;


@Entity
@Table(name = "contents")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String tag;

    public Content() {
    }
}
