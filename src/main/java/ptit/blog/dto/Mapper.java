package ptit.blog.dto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ptit.blog.dto.entity.UserDto;
import ptit.blog.dto.response.user.CreateUserResp;
import ptit.blog.model.user.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class Mapper {


    public static CreateUserResp responseUserFromModel(User user){
        if (user == null) {
            return null;
        }
        return CreateUserResp.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public static UserDto responseUserDtoFromModel(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .groups(user.getGroups())
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }


}
