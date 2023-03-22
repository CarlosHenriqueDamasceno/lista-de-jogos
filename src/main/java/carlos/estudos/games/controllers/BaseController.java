package carlos.estudos.games.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public class BaseController {

    @Value("${app.default-page-size}")
    int defaultPageSize;

    protected Pageable parsePagination(Optional<Integer> page, Optional<Integer> pageSize){
        int s = pageSize.orElse(defaultPageSize);
        int p = page.isPresent() && page.get() > 0 ? page.get()-1 : 0;
        return PageRequest.of(p, s);
    }
}
