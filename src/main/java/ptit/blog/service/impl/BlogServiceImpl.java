package ptit.blog.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ptit.blog.dto.Mapper;
import ptit.blog.dto.entity.BlogListDto;
import ptit.blog.dto.entity.UserDto;
import ptit.blog.dto.request.blog.SearchBlog;
import ptit.blog.dto.response.blog.BlogDetailsResp;
import ptit.blog.exception.user.UserException;
import ptit.blog.model.Blog;
import ptit.blog.model.user.User;
import ptit.blog.repository.BlogRepo;
import ptit.blog.response.ResponseObject;
import ptit.blog.response.ResponsePagination;
import ptit.blog.response.ResponseStatus;
import ptit.blog.service.BlogService;
import ptit.blog.utilservice.PaginationCustom;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepo blogRepo;
    private final PaginationCustom paginationCustom;


    @Override
    public ResponseObject<List<BlogListDto>> getList() {
        ResponseObject<List<BlogListDto>> res = new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL);
        List<Blog> blogs = blogRepo.findAll();
        log.info(blogs.get(0).getUser().getUsername());
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
        res.setData(Mapper.responseBlogDetailsFromModel(blog));
        return res;
    }
}
