package com.foodmarket.foodmarket.repository;

import com.foodmarket.foodmarket.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long>, JpaSpecificationExecutor<Food> {

    @Transactional
    @Query(value = "SELECT f.id, f.name, f.description, f.picture_path, f.price, f.rate, f.types \n" +
            "FROM food f\n" +
            "WHERE \n" +
            "    (f.name LIKE '%:name%' OR f.types LIKE '%:types%') \n" +
            "    AND (f.price IS NULL OR f.price = :price) \n" +
            "ORDER BY f.rate DESC \n" +
            "LIMIT 10\n" +
            "OFFSET 10",
            nativeQuery = true)
    List<Object[]> getDataFood(@Param("name") String name,
                               @Param("types") String types,
                               @Param("price") Integer price);
}
