package com.dahoon.toy.artcollector.common.enumeration;

import jakarta.persistence.AttributeConverter;

import java.util.List;

public class GameListConverter implements AttributeConverter<List<GameGenre>, String> {
    @Override
    public String convertToDatabaseColumn(List<GameGenre> gameGenres) {
        return null;
    }

    @Override
    public List<GameGenre> convertToEntityAttribute(String s) {
        return null;
    }
}
