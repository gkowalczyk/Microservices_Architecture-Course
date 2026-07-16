package com.example.contentclientms;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class Start {

    private final PostClient postClient;

    public Start(PostClient postClient) {
        this.postClient = postClient;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void get() {
        System.out.println(postClient.getPost(1l));
    }
}
