package com.example.recomendationms;


import jakarta.persistence.*;

@Entity
@Table(name = "spring_cloud_db.contents")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String tag;

    public Content() {
    }
}
