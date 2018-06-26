package com.piatkouskaya.test.service;

import com.piatkouskaya.test.dto.RequestDto;
import com.piatkouskaya.test.entity.Request;

import java.util.List;

public interface RequestService {

    List<Request> getAllDesc();

    Request saveRequest(RequestDto request);

    void deleteRequest(Request request);

    Request getById(Long id);
}
