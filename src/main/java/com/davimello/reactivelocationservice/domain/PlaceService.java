package com.davimello.reactivelocationservice.domain;

import com.davimello.reactivelocationservice.api.PlaceRequest;
import com.github.slugify.Slugify;
import reactor.core.publisher.Mono;

public class PlaceService {
    private final PlaceRepository placeRepository;
    private Slugify slg;
    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
        this.slg = Slugify.builder().build();
    }

    public Mono<Place> create(PlaceRequest placeRequest) {
        var place = new Place(null, placeRequest.name(), placeRequest.state(), slg.slugify(placeRequest.name()), null, null);
        return placeRepository.save(place);
    }
}
