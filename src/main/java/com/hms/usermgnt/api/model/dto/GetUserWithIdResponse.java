package com.hms.usermgnt.api.model.dto;

public class GetUserWithIdResponse {
    private UserDto userDto;
    private Status status;


    public GetUserWithIdResponse(UserDto userDto, Status status) {
        this.userDto = userDto;
        this.status = status;
    }

    public GetUserWithIdResponse( Status status) {
        this.status = status;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public Status getStatus() {
        return status;
    }
}
