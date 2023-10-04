package com.davimello.reactivelocationservice.web;

import com.davimello.reactivelocationservice.api.PlaceResponse;
import com.davimello.reactivelocationservice.domain.Place;

public class PlaceMapper {
    public static PlaceResponse fromPlaceToResponse(Place place) {
        return new PlaceResponse(place.name(), place.state(), place.slug(), place.createdAt(), place.updatedAt());
    }
}
