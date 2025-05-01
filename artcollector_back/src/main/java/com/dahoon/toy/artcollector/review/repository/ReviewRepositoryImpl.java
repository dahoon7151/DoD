package com.dahoon.toy.artcollector.review.repository;

import com.dahoon.toy.artcollector.review.entity.QReview;
import com.dahoon.toy.artcollector.review.entity.Review;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ReviewRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
    @Override
    public Page<Review> findAllByGameIdAndRatingFilter(String gameId, Integer minRating, Pageable pageable) {
        QReview review = QReview.review;
        List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifiers(pageable.getSort());

        JPAQuery<Review> query = queryFactory
                .selectFrom(review)
                .where(
                        review.gameId.eq(gameId),
                        review.rating.goe(minRating)
                )
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<Review> results = query.fetch();

        Long totalResult = queryFactory
                .select(review.count())
                .from(review)
                .where(
                        review.gameId.eq(gameId),
                        review.rating.goe(minRating)
                )
                .fetchOne();

        long total = (totalResult != null) ? totalResult : 0L;

        return new PageImpl<>(results, pageable, total);
    }


    private List<OrderSpecifier<?>> getOrderSpecifiers(Sort sort) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        PathBuilder<Review> pathBuilder = new PathBuilder<>(Review.class, "review");

        for (Sort.Order order : sort) {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();
            switch (property) {
                case "rating", "likeCount":
                    orderSpecifiers.add(new OrderSpecifier<>(direction, pathBuilder.getNumber(property, Integer.class)));
                    break;
                default:
                    break;
            }
        }

        return orderSpecifiers;
    }
}
