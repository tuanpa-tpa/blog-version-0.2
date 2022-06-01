package ptit.blog.dto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ptit.blog.dto.entity.BlogListDto;
import ptit.blog.dto.entity.CommentDto;
import ptit.blog.dto.entity.UserDto;
import ptit.blog.dto.response.blog.BlogCreateResp;
import ptit.blog.dto.response.blog.BlogDetailsResp;
import ptit.blog.dto.response.category.CategoryListDto;
import ptit.blog.dto.response.user.CreateUserResp;
import ptit.blog.model.Blog;
import ptit.blog.model.Category;
import ptit.blog.model.Comment;
import ptit.blog.model.user.User;
import ptit.blog.repository.UserRepo;

@Slf4j
@Service
@RequiredArgsConstructor
public class Mapper {
    private final UserRepo userRepo;

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
                .avatar(user.getAvatar())
                .roles(user.getRoles())
                .name(user.getName())
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }


    public static BlogListDto responseBlogListDtoFromModel(Blog blog) {
        return BlogListDto.builder()
                .id(blog.getBlogId())
                .img(blog.getImg().getId())
                .title(blog.getTitle())
                .blogPosted(blog.getCreatedAt())
                .tags(blog.getCategories())
                .blogText(blog.getContent())
                .comment((long) blog.getComments().size())
                .avatar(blog.getUser().getAvatar())
                .username(blog.getUser().getUsername())
                .build();
    }

    public static BlogCreateResp responseBlogCreateRespFromModel(Blog blog) {
        return BlogCreateResp.builder()
                .featuredImageId(blog.getImg().getId())
                .blogTitle(blog.getTitle())
                .postedAt(blog.getCreatedAt())
                .blogCategories(blog.getCategories())
                .blogText(blog.getContent())
                .avatar(blog.getUser().getAvatar())
                .username(blog.getUser().getUsername())
                .build();
    }

    public static BlogDetailsResp responseBlogDetailsFromModel(Blog blog) {
        return BlogDetailsResp.builder()
                .img(blog.getImg().getId())
                .title(blog.getTitle())
                .postedAt(blog.getCreatedAt())
                .tags(blog.getCategories())
                .content(blog.getContent())
                .comments(blog.getComments() == null? 0 : (long) blog.getComments().size())
                .bookmarked(0L)
                .avatar(blog.getUser().getAvatar())
                .username(blog.getUser().getUsername())
                .build();
    }

    public static CommentDto responseCommentDtoFromModel(Comment comment) {
        return CommentDto.builder()
                .avatar(comment.getUser().getAvatar())
                .username(comment.getUser().getUsername())
                .commentText(comment.getContent())
                .commentedAt(comment.getCreatedAt())
                .build();
    }

    public static CategoryListDto responseCategoryListFromModel(Category category) {
        return CategoryListDto.builder()
                .category(category.getCategoryName())
                .icon(category.getIcon())
                .build();
    }
}
