package com.maxmarkovdev.springboot.service.impl.dto;

import com.maxmarkovdev.springboot.dao.interfaces.dto.PageDtoDao;
import com.maxmarkovdev.springboot.model.dto.PageDto;
import com.maxmarkovdev.springboot.service.interfaces.dto.PageDtoService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
@NoArgsConstructor
public class PageDtoServiceImpl<T> implements PageDtoService<T> {

    public Map<String, PageDtoDao<?>> pageDtoDaoMap;

    @Autowired
    public void setPageDtoDaoMap(Map<String, PageDtoDao<?>> pageDtoDaoMap) {
        this.pageDtoDaoMap = pageDtoDaoMap;
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public PageDto<T> getPage(int currentPageNumber, int itemsOnPage, Map<Object, Object> map) {

        PageDtoDao<T> pageDtoDao = (PageDtoDao<T>) pageDtoDaoMap.get((String) map.get("class"));

        map.put("currentPageNumber", currentPageNumber);
        map.put("itemsOnPage", itemsOnPage);

        long totalResultCount = pageDtoDao.getTotalResultCount(map);

        int totalPageCount = (int) Math.ceil((double) totalResultCount / itemsOnPage);

        return new PageDto<>(currentPageNumber, totalPageCount,
                itemsOnPage, totalResultCount, pageDtoDao.getItems(map));
    }
}
