package com.example.recomendationms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recommendation")
public class RecommedationController {

    @Autowired
    private RecommendationService recommendationService;


    @GetMapping("/{tag}")
    public List<Map<String, String>> getRecommendationByTag(@PathVariable String tag) {
        return recommendationService.getRecommendationByTag(tag);
    }
}
