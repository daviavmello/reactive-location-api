package com.davimello.reactivelocationservice.domain;

import com.davimello.reactivelocationservice.api.PlaceRequest;
import com.davimello.reactivelocationservice.util.QueryBuilder;
import com.davimello.reactivelocationservice.web.PlaceMapper;
import com.github.slugify.Slugify;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PlaceService {
    private final PlaceRepository placeRepository;
    private final Slugify slg;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
        this.slg = Slugify.builder().build();
    }

    public Mono<Place> create(PlaceRequest placeRequest) {
        var place = new Place(null, placeRequest.name(), placeRequest.state(), placeRequest.city(), slg.slugify(placeRequest.name()), null, null);
        return placeRepository.save(place);
    }

    public Mono<Place> read(Long id) {
        return placeRepository.findById(id);
    }

    public Flux<Place> readAll(String name) {
        var place = new Place(null, name, null,
                null, null, null, null);
        Example<Place> query = QueryBuilder.makeQuery(place);
        return placeRepository.findAll(query, Sort.by("name").ascending());
    }

    public Mono<Place> update(Long id, PlaceRequest placeRequest) {
        return placeRepository.findById(id)
                .map(place -> PlaceMapper.updatePlaceFromDTO(placeRequest, place))
                .map(place -> place.withSlug(slg.slugify(place.name())))
                .flatMap(placeRepository::save);
    }

    public Mono<Boolean> delete(Long id) {
        return placeRepository.deleteById(id)
                .then(Mono.just(true))
                .onErrorResume(EmptyResultDataAccessException.class, ex -> Mono.just(false));
    }
}
