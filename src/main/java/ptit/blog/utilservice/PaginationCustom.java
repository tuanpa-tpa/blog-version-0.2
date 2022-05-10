package ptit.blog.utilservice;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@Service
public class PaginationCustom {
    private int page;
    private int size;
    private String[] sort;

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    public Pageable createPaginationCustom(int page, int size, String[] sort) {
        this.page = page;
        this.size = size;
        this.sort= sort;
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        Pageable pagingSort= PageRequest.of(page, size, Sort.by(orders));
        return pagingSort;
    }
}
