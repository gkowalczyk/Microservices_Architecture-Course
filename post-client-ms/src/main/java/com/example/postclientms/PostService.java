package com.example.postclientms;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostClient postClient;
    private Collection<Post> lastGoodPostList = new ArrayList<>();
    private final CacheManager cacheManager;

    @CircuitBreaker(name = "postApi", fallbackMethod = "fallbackPosts")
    public Collection<Post> getPosts() {
        Collection<Post> posts = postClient.getPosts();
        cacheManager.getCache("posts").put("lastGoodPostList", posts);
        return posts;
    }

    public Collection<Post> fallbackPosts(Throwable throwable) {
        log.info("API nie działa, zwaracam dane " + throwable.getClass().getSimpleName());

        Cache.ValueWrapper wrapper =
                cacheManager
                        .getCache("posts")
                        .get("lastGoodPostList");
        Collection<Post> posts = (Collection<Post>) wrapper.get();
        log.info("Cache zawiera {} postów", posts.size());

        return wrapper != null ? (Collection<Post>) wrapper.get() : List.of();
    }
}

