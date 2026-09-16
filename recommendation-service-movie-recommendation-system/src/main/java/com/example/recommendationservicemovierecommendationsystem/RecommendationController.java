package com.example.recommendationservicemovierecommendationsystem;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping
    public List<MovieResponse> recommended(
            @RequestParam String genre,
            @RequestParam Double minimumRating,
            @RequestParam Integer limit
    ) {
        return recommendationService
                .recommend(genre,
                        minimumRating,
                        limit);
    }
}
