package com.amr.project.model.dto;

import com.amr.project.model.enums.Status;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Collection;

@Data
public class OrderDto {
    private Long id;
    private Collection<ItemDto> items;
    @Enumerated(EnumType.STRING)
    private Status status;
}
