package com.byul.service;

import com.byul.domain.repository.ItemRepository;
import com.byul.web.dto.response.ItemListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    /**
     * 신 메뉴 가져오기
     */
    public List<ItemListResponseDto> findAllLatest() {
        return itemRepository.findLatest().stream()
                .map(ItemListResponseDto::new)
                .collect(Collectors.toList());
    }
}
