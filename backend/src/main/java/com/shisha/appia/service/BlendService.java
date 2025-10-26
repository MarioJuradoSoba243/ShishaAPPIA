package com.shisha.appia.service;

import com.shisha.appia.api.dto.BlendRequest;
import com.shisha.appia.api.dto.BlendResponse;
import com.shisha.appia.api.dto.TobaccoResponse;
import com.shisha.appia.model.Blend;
import com.shisha.appia.model.Tobacco;
import com.shisha.appia.repository.BlendRepository;
import com.shisha.appia.repository.TobaccoRepository;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Business logic for managing blends composed of tobaccos.
 */
@Service
public class BlendService {

    private final BlendRepository blendRepository;
    private final TobaccoRepository tobaccoRepository;

    public BlendService(BlendRepository blendRepository, TobaccoRepository tobaccoRepository) {
        this.blendRepository = blendRepository;
        this.tobaccoRepository = tobaccoRepository;
    }

    /**
     * Retrieves all blends registered in the catalogue.
     *
     * @return list of blend DTOs
     */
    @Transactional(readOnly = true)
    public List<BlendResponse> findAll() {
        return blendRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new blend ensuring all referenced tobaccos exist.
     *
     * @param request payload describing the new blend
     * @return persisted blend as DTO
     */
    @Transactional
    public BlendResponse create(BlendRequest request) {
        Blend blend = new Blend(request.getName(), request.getDescription());
        blend.setTobaccos(loadTobaccos(request.getTobaccoIds()));
        Blend saved = blendRepository.save(blend);
        return toResponse(saved);
    }

    private Set<Tobacco> loadTobaccos(List<Long> tobaccoIds) {
        Set<Tobacco> tobaccos = new LinkedHashSet<>();
        for (Long tobaccoId : tobaccoIds) {
            Tobacco tobacco = tobaccoRepository.findById(tobaccoId)
                    .orElseThrow(() -> new ResponseStatusException(
                            NOT_FOUND,
                            "Tabaco con id " + tobaccoId + " no encontrado"
                    ));
            tobaccos.add(tobacco);
        }
        return tobaccos;
    }

    private BlendResponse toResponse(Blend blend) {
        List<TobaccoResponse> tobaccos = blend.getTobaccos()
                .stream()
                .map(tobacco -> new TobaccoResponse(tobacco.getId(), tobacco.getName(), tobacco.getBrand()))
                .collect(Collectors.toList());
        return new BlendResponse(blend.getId(), blend.getName(), blend.getDescription(), tobaccos);
    }
}
