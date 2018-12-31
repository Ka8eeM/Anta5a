package com.example.karim.anta5a.models;

public class LoginResponse {
    private long Id;
    private boolean Activated;

    public LoginResponse(long id, boolean activated) {
        Id = id;
        Activated = activated;
    }

    public long getId() {
        return Id;
    }

    public boolean isActivated() {
        return Activated;
    }
}
