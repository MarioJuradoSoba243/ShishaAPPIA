package com.shisha.appia.api;

import com.shisha.appia.api.dto.BlendRequest;
import com.shisha.appia.api.dto.BlendResponse;
import com.shisha.appia.service.BlendService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller exposing operations for managing blends.
 */
@RestController
@RequestMapping("/api/blends")
public class BlendController {

    private final BlendService blendService;

    public BlendController(BlendService blendService) {
        this.blendService = blendService;
    }

    /**
     * Returns the list of blends currently registered in the system.
     *
     * @return list of blend DTOs
     */
    @GetMapping
    public List<BlendResponse> getAll() {
        return blendService.findAll();
    }

    /**
     * Creates a new blend.
     *
     * @param request validated blend payload
     * @return the persisted blend data
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlendResponse create(@Valid @RequestBody BlendRequest request) {
        return blendService.create(request);
    }
}
