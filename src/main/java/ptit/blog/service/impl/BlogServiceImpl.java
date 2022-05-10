package ptit.blog.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ptit.blog.dto.Mapper;
import ptit.blog.dto.entity.BlogListDto;
import ptit.blog.dto.entity.UserDto;
import ptit.blog.model.Blog;
import ptit.blog.repository.BlogRepo;
import ptit.blog.response.ResponseObject;
import ptit.blog.response.ResponseStatus;
import ptit.blog.service.BlogService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepo blogRepo;
    @Override
    public ResponseObject<List<BlogListDto>> getList() {
        ResponseObject<List<BlogListDto>> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
        List<Blog> blogs = blogRepo.findAll();
        log.info(blogs.get(0).getUser().getUsername());
        List<BlogListDto> result = blogs.stream().map(Mapper::responseBlogListDtoFromModel).collect(Collectors.toList());
        res.setData(result);
        return res;
    }
}
