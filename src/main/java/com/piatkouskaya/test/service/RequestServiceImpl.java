package com.piatkouskaya.test.service;

import com.google.common.collect.Lists;
import com.piatkouskaya.test.db.RequestRepository;
import com.piatkouskaya.test.dto.RequestDto;
import com.piatkouskaya.test.entity.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public List<Request> getAllDesc() {
        List<Request> requests = Lists.newArrayList(requestRepository.findAll());
        Collections.reverse(requests);

        return requests;
    }

    public Request saveRequest(RequestDto request) {
        Request savingRequest = Request.builder()
                .dateTime(LocalDateTime.now())
                .requestLine(request.getRequestLine())
                .build();

        return requestRepository.save(savingRequest);
    }

    public void deleteRequest(Request request) {
        requestRepository.delete(request);
    }

    @Override
    public Request getById(Long id) {
        return requestRepository.findById(id).orElse(null);
    }
}
