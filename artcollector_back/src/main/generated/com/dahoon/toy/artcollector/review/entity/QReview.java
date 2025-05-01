package com.dahoon.toy.artcollector.review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = 1925009891L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final com.dahoon.toy.artcollector.common.QBaseEntity _super = new com.dahoon.toy.artcollector.common.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final StringPath gameId = createString("gameId");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final ListPath<ReviewLike, QReviewLike> likes = this.<ReviewLike, QReviewLike>createList("likes", ReviewLike.class, QReviewLike.class, PathInits.DIRECT2);

    public final NumberPath<Integer> rating = createNumber("rating", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedTime = _super.updatedTime;

    public final com.dahoon.toy.artcollector.user.QUser user;

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.dahoon.toy.artcollector.user.QUser(forProperty("user")) : null;
    }

}

