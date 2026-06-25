package com.ocs.portal.svc.role.utils;

import com.ocs.portal.svc.role.dto.request.common.PagingRequestDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageUtils {

    public Pageable getPageAble(PagingRequestDto pagingRequest) {
        int page = Math.max(pagingRequest.getPage() - 1, 0);
        Sort sort = Sort.by(Sort.Direction.fromString(pagingRequest.getSortDirection()), pagingRequest.getSortBy());
        return PageRequest.of(page, pagingRequest.getSize(), sort);
    }


}
