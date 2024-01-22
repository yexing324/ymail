package org.ymail.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ymail.mapper.REmailMapper;
import org.ymail.service.REmailService;

@RequiredArgsConstructor
@Service
public class REmailServiceImpl implements REmailService {
    private final REmailMapper REmailMapper;

}
