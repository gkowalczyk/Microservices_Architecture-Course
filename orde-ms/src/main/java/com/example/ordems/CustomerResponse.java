package com.example.ordems;

public record CustomerResponse(Long id,
                               String firstName,
                               String lastName,
                               String email,
                               String phone) {
}