package com.shisha.appia.service;

import com.shisha.appia.api.dto.TobaccoRequest;
import com.shisha.appia.api.dto.TobaccoResponse;
import com.shisha.appia.model.Tobacco;
import com.shisha.appia.repository.TobaccoRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Business logic for managing tobaccos.
 */
@Service
public class TobaccoService {

    private final TobaccoRepository tobaccoRepository;

    public TobaccoService(TobaccoRepository tobaccoRepository) {
        this.tobaccoRepository = tobaccoRepository;
    }

    /**
     * Retrieves all tobaccos available in the catalogue.
     *
     * @return a list of DTOs representing the tobaccos
     */
    @Transactional(readOnly = true)
    public List<TobaccoResponse> findAll() {
        return tobaccoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Registers a new tobacco in the catalogue.
     *
     * @param request payload with the new tobacco data
     * @return the persisted tobacco as DTO
     */
    @Transactional
    public TobaccoResponse create(TobaccoRequest request) {
        Tobacco entity = new Tobacco(request.getName(), request.getBrand());
        Tobacco saved = tobaccoRepository.save(entity);
        return toResponse(saved);
    }

    private TobaccoResponse toResponse(Tobacco tobacco) {
        return new TobaccoResponse(tobacco.getId(), tobacco.getName(), tobacco.getBrand());
    }
}
