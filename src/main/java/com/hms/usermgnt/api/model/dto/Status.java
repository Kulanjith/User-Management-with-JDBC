package com.hms.usermgnt.api.model.dto;

public class Status {
    private int statusCode;
    private String description;

      public static int DB_ERROR = 1001;
      public static int SUCCESS = 1000;

    public Status(int statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getDescription() {
        return description;
    }
}
