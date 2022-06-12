package ptit.blog.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ptit.blog.dto.request.blog.BlogPostReq;
import ptit.blog.dto.Mapper;
import ptit.blog.dto.entity.BlogListDto;
import ptit.blog.dto.entity.UserDto;
import ptit.blog.dto.request.blog.SearchBlog;
import ptit.blog.dto.request.blog.UpdateBlog;
import ptit.blog.dto.response.blog.BlogCreateResp;
import ptit.blog.dto.response.blog.BlogDetailsResp;
import ptit.blog.exception.blog.BlogException;
import ptit.blog.exception.user.UserException;
import ptit.blog.model.Blog;
import ptit.blog.model.Category;
import ptit.blog.model.Comment;
import ptit.blog.model.Image;
import ptit.blog.model.user.User;
import ptit.blog.repository.*;
import ptit.blog.response.ResponseObject;
import ptit.blog.response.ResponsePagination;
import ptit.blog.response.ResponseStatus;
import ptit.blog.service.BlogService;
import ptit.blog.util.ImageUtility;
import ptit.blog.utilservice.PaginationCustom;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BlogServiceImpl implements BlogService {
    private final CommentRepo commentRepo;
    private final CategoryRepo categoryRepo;
    private final UserRepo userRepo;
    private final BlogRepo blogRepo;
    private final PaginationCustom paginationCustom;
    private final ImageRepository imageRepository;

    @Override
    public ResponseObject<List<BlogListDto>> getList() {
        ResponseObject<List<BlogListDto>> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
        List<Blog> blogs = blogRepo.findAll();
        List<BlogListDto> result = blogs.stream().map(Mapper::responseBlogListDtoFromModel).collect(Collectors.toList());
        res.setData(result);
        return res;
    }

    @Override
    public ResponseObject<ResponsePagination<Object>> search(SearchBlog req) {
        ResponseObject<ResponsePagination<Object>> res = new ResponseObject<>(true,
                ResponseStatus.DO_SERVICE_SUCCESSFUL);
        // if page or size is null then set page = 0 and size = 20
        int page = (req.getPage() == null) ? 0 : req.getPage();
        int size = (req.getSize() == null) ? 20 : req.getSize();
        // get data from request and check if its data is null
        String contains = ((req.getContains() == null) || (req.getContains().equals(""))) ? null
                : req.getContains().trim();
        // if there are more than one space between each word then replace with one
        // space only
        if (contains != null) {
            String[] words = contains.split("\\s+");
            contains = String.join(" ", words);
        }
        // if sort array is null then set defaut value is organizationId, asc
        String[] sort = ((req.getSort() == null) || (req.getSort().length == 0)) ? new String[] { "blogId,asc" }
                : req.getSort();
        // Convert fromDate and toDate String to Date
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date fromDate = null, toDate = null;
        try {
            // Create Pagable
            Pageable pageable = this.paginationCustom.createPaginationCustom(page, size, sort);
            // set default value for fromDate and toDate
            if (req.getFromDate() != null && !req.getFromDate().equals(""))
                fromDate = sf.parse(req.getFromDate() + " 00:00:00");
            if (req.getToDate() != null && !req.getToDate().equals(""))
                toDate = sf.parse(req.getToDate() + " 23:59:59");
            // Get list staffModel from search method in repo
            Page<Blog> pageUser = null;
            pageUser = blogRepo.search(contains, fromDate, toDate, pageable);
            res.setData(ResponsePagination.builder()
                    .data(pageUser.stream().map(Mapper::responseBlogListDtoFromModel).collect(Collectors.toList()))
                    .size(pageUser.getSize())
                    .currentPage(pageUser.getNumber())
                    .totalPages(pageUser.getTotalPages())
                    .totalItems(pageUser.getTotalElements())
                    .build());
        } catch (Exception e) {
            throw new UserException(e.getMessage());
        }
        return res;
    }

    @Override
    public ResponseObject<BlogDetailsResp> getDetails(Long id) {
        ResponseObject<BlogDetailsResp> res = new ResponseObject<>(true,
                ResponseStatus.DO_SERVICE_SUCCESSFUL);
        Blog blog = blogRepo.findById(id).orElseThrow(() -> new RuntimeException("not found blog id"));
        log.info(blog.getCategories().size()+"");
        res.setData(Mapper.responseBlogDetailsFromModel(blog));
        return res;
    }

    @Override
    @Transactional
    public ResponseObject<Boolean> deleteBlog(Long id) {
        ResponseObject<Boolean> res = new ResponseObject<>(true,
                ResponseStatus.DO_SERVICE_SUCCESSFUL);
        try {
            Blog blog = blogRepo.findById(id)
                    .orElseThrow(() -> new BlogException("not found by id"));

            this.imageRepository.deleteById(blog.getImg().getId());
            Set<Comment> list = blog.getComments();
            list.forEach((c) -> commentRepo.deleteById(c.getCommentId()));

            this.blogRepo.deleteById(blog.getBlogId());

            res.setData(true);
        } catch (Exception e) {
            res.setData(false);
            throw new BlogException("khong the xoa");
        }
        return res;
    }

    @Override
    public ResponseObject<BlogDetailsResp> update(UpdateBlog req) {
        ResponseObject<BlogDetailsResp> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
        boolean check = false;

        Image img = null;
        try {
            if (req.getImg() != null) {
                img = Image.builder()
                        .name(req.getImg().getOriginalFilename())
                        .type(req.getImg().getContentType())
                        .image(ImageUtility.compressImage(req.getImg().getBytes())).build();
                imageRepository.save(img);
                check = true;
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        try {
            Set<Category> categories = new HashSet<>();
            for (String name: req.getCategories()) {
                try {
                    categories.add(categoryRepo.findByCategoryName(name));
                } catch (Exception e) {
                    log.info(e.getMessage());
                }
            }
            Blog blog = blogRepo.findById(req.getId()).orElseThrow(() -> new BlogException("not found"));
            blog.setCategories(categories);
            blog.setContent(req.getContent());
            blog.setTitle(req.getTitle());
            if (check) blog.setImg(img);
            blogRepo.save(blog);
            res.setData(Mapper.responseBlogDetailsFromModel(blog));
        } catch (Exception e) {
            throw new BlogException("loi update");
        }
        return res;
    }

    @Override
    public ResponseObject<BlogCreateResp> postBlog(UserDto userDto, BlogPostReq req) throws IOException {
        ResponseObject<BlogCreateResp> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
        Image img = null;
        try {
            img = Image.builder()
                    .name(req.getImg().getOriginalFilename())
                    .type(req.getImg().getContentType())
                    .image(ImageUtility.compressImage(req.getImg().getBytes())).build();
            imageRepository.save(img);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        try {
            Set<Category> categories = new HashSet<>();
            for (String name: req.getCategories()) {
                try {
                    categories.add(categoryRepo.findByCategoryName(name));
                } catch (Exception e) {
                    log.info(e.getMessage());
                }
            }

            User user = userRepo.findByUsername(userDto.getUsername());
            Blog blog = blogRepo.save(Blog.builder()
                    .user(user)
                    .img(img)
                    .categories(categories)
                    .title(req.getTitle())
                    .content(req.getContent())
                    .updatedAt(new Date())
                    .createdAt(new Date())
                    .build());
            res.setData(Mapper.responseBlogCreateRespFromModel(blog));
        } catch (Exception e) {
            throw new BlogException("loi post");
        }
        return res;
    }
}
