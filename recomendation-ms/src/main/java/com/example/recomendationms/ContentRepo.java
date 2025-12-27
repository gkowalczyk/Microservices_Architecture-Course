package com.example.recomendationms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepo extends JpaRepository<Content, Long> {

    List<Content> findByTagIgnoreCase(String tag);

}
