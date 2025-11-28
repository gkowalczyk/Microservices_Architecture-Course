package com.example.contentms;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/content")
public class ContentController {

    private final ContentRepo contentRepo;

    public ContentController(ContentRepo contentRepo) {
        this.contentRepo = contentRepo;
    }

    @GetMapping
    public List<Content> getContent() {
        return contentRepo.findAll();
    }

   /* private List<Map<String, String>> mapList =
            List.of(
                    Map.of("id", "1", "tag", "AkamediaSpring"),
                    Map.of("id", "2", "tag", "AkamediaSpring2"),
                    Map.of("id", "3", "tag", "AkamediaSpring3")
            );*/

    @GetMapping("/{tag}")
    public List<Content> get(@PathVariable String tag) {
        return contentRepo.findByTagIgnoreCase(tag);

   /* @GetMapping("/{tag}")
    public List<Map<String, String>> get(@PathVariable String tag) {
        return mapList.stream()
                .filter(content -> content.get("tag").equalsIgnoreCase(tag))
                .collect(Collectors.toList());
    }*/
    }
}