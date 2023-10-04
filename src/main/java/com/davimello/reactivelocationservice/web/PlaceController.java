package com.davimello.reactivelocationservice.web;

import com.davimello.reactivelocationservice.api.PlaceRequest;
import com.davimello.reactivelocationservice.api.PlaceResponse;
import com.davimello.reactivelocationservice.domain.PlaceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/places")
public class PlaceController {
    @Autowired
    private PlaceService placeService;

    @PostMapping
    public ResponseEntity<Mono<PlaceResponse>> create(@Valid @RequestBody PlaceRequest request) {
        var placeResponse = placeService.create(request).map(PlaceMapper::toResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(placeResponse);
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<PlaceResponse>> read(@PathVariable("id") Long id) {
        return placeService.read(id)
                .map(place -> ResponseEntity.ok(PlaceMapper.toResponse(place)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<PlaceResponse> readAll(@RequestParam(required = false) String name) {
        return placeService.readAll(name).map(PlaceMapper::toResponse);
    }

    @PatchMapping("{id}")
    public Mono<PlaceResponse> update(@PathVariable("id") Long id, @RequestBody PlaceRequest placeRequest) {
        return placeService.update(id, placeRequest)
                .map(PlaceMapper::toResponse);
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") Long id) {
        return placeService.delete(id).map(deleted -> {
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        });
    }
}
