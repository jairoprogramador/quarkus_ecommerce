package com.jairogalvez.application.order;

import java.util.Date;
import java.util.List;

public record OrderDTO(
        String id,
        Double total,
        Date created_at,
        List<ItemDTO> items
){}
