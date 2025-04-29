package com.dahoon.toy.artcollector.library.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCollection is a Querydsl query type for Collection
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCollection extends EntityPathBase<Collection> {

    private static final long serialVersionUID = 1277020896L;

    public static final QCollection collection = new QCollection("collection");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QCollection(String variable) {
        super(Collection.class, forVariable(variable));
    }

    public QCollection(Path<? extends Collection> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCollection(PathMetadata metadata) {
        super(Collection.class, metadata);
    }

}

