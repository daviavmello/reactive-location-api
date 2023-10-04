package com.davimello.reactivelocationservice.web;

import com.davimello.reactivelocationservice.api.PlaceRequest;
import com.davimello.reactivelocationservice.api.PlaceResponse;
import com.davimello.reactivelocationservice.domain.Place;
import org.springframework.util.StringUtils;

public class PlaceMapper {
    public static PlaceResponse toResponse(Place place) {
        return new PlaceResponse(place.name(), place.state(), place.city(), place.slug(), place.createdAt(), place.updatedAt());
    }

    public static Place updatePlaceFromDTO(PlaceRequest placeRequest, Place place) {
        final String name = StringUtils.hasText(placeRequest.name()) ? placeRequest.name() : place.name();
        final String state = StringUtils.hasText(placeRequest.state()) ? placeRequest.state() : place.state();
        final String city = StringUtils.hasText(placeRequest.city()) ? placeRequest.city() : place.city();
        return new Place(place.id(), name, place.slug(), state, city, place.createdAt(), place.updatedAt());
    }
}
