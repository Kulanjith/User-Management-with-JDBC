package com.hms.usermgnt.api.converter;

import com.hms.usermgnt.api.model.User;
import com.hms.usermgnt.api.model.dto.UserDto;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public UserDto entityToDto(User user){

        ModelMapper mapper = new ModelMapper();
        UserDto map = mapper.map(user,UserDto.class);
        return map;
    }

    public List<UserDto> entityToDto(List<User> user){

        return user.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public User dtoToEntity(UserDto dto){

        ModelMapper mapper = new ModelMapper();
        User map = mapper.map(dto, User.class);
        return map;
    }

    public List<User> dtoToEntity(List<UserDto> dto){

        return dto.stream().map(x ->dtoToEntity(x)).collect(Collectors.toList());
    }


}
