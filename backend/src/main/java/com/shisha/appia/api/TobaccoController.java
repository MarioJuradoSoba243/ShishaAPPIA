package com.shisha.appia.api;

import com.shisha.appia.api.dto.TobaccoRequest;
import com.shisha.appia.api.dto.TobaccoResponse;
import com.shisha.appia.service.TobaccoService;
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
 * REST controller exposing operations for managing tobaccos.
 */
@RestController
@RequestMapping("/api/tobaccos")
public class TobaccoController {

    private final TobaccoService tobaccoService;

    public TobaccoController(TobaccoService tobaccoService) {
        this.tobaccoService = tobaccoService;
    }

    /**
     * Returns all tobaccos stored in the system.
     *
     * @return a list of tobacco DTOs
     */
    @GetMapping
    public List<TobaccoResponse> getAll() {
        return tobaccoService.findAll();
    }

    /**
     * Creates a new tobacco entry.
     *
     * @param request validated request payload
     * @return the persisted tobacco
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TobaccoResponse create(@Valid @RequestBody TobaccoRequest request) {
        return tobaccoService.create(request);
    }
}
