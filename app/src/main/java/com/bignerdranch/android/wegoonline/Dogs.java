package com.bignerdranch.android.wegoonline;

import androidx.annotation.NonNull;

public class Dogs {

    private String message;
    private String status;

    public Dogs(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Dogs{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
