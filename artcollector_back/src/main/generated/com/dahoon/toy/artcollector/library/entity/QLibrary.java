package com.dahoon.toy.artcollector.library.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLibrary is a Querydsl query type for Library
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLibrary extends EntityPathBase<Library> {

    private static final long serialVersionUID = 1025851321L;

    public static final QLibrary library = new QLibrary("library");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QLibrary(String variable) {
        super(Library.class, forVariable(variable));
    }

    public QLibrary(Path<? extends Library> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLibrary(PathMetadata metadata) {
        super(Library.class, metadata);
    }

}

