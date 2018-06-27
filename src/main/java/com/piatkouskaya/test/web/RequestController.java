package com.piatkouskaya.test.web;

import com.piatkouskaya.test.dto.RequestDto;
import com.piatkouskaya.test.dto.ViewRequestDto;
import com.piatkouskaya.test.entity.Request;
import com.piatkouskaya.test.service.RequestService;
import com.piatkouskaya.test.util.DateTimeUtil;
import com.piatkouskaya.test.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RequestController {

    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/testRequest")
    public String doGet(Model model) {
        Request testRequest = new Request();
        model.addAttribute("request", testRequest);

        List<Request> viewRequests = getRequests();
        model.addAttribute("requests", viewRequests);

        return "request_page";
    }

    @PostMapping("/testRequest")
    public String doPost(RequestDto testRequest, Model model,
                         @RequestParam(value = "delete", required = false) Long id) {
        Request newRequest = new Request();
        model.addAttribute("request", newRequest);

        if (id != null) {
            Request deletingRequest = requestService.getById(id);
            requestService.deleteRequest(deletingRequest);
        }

        if (!StringUtil.isEmpty(testRequest.getRequestLine())) {
            requestService.saveRequest(testRequest);
        }

        model.addAttribute("requests", getRequests());

        return "request_page";
    }

    private List<Request> getRequests() {
        List<Request> allDesc = requestService.getAllDesc();
        return allDesc.stream()
                .peek(it -> ViewRequestDto.builder()
                        .dateTime(DateTimeUtil.format(it.getDateTime()))
                        .requestLine(it.getRequestLine()))
                .collect(Collectors.toList());
    }
}
