package com.byul.service;

import com.byul.domain.repository.ItemRepository;
import com.byul.web.dto.ItemParam;
import com.byul.web.dto.response.ItemListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public List<ItemListResponseDto> findAllComplex(ItemParam param) {
        return itemRepository.findAllComplex(param).stream()
                .map(ItemListResponseDto::new)
                .collect(Collectors.toList());
    }
}
