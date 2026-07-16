package com.example.contentclientms;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

@HttpExchange
public interface PostClient {

    @GetExchange("/posts/{id}")
    Post getPost(@PathVariable("id") Long id);

    @GetExchange("/posts")
    List<Post> getPosts();

    @PostExchange("/posts")
    void createPost(@RequestBody Post post);
}
