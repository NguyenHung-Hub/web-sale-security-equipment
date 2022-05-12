package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
@Transactional
public interface DiscountRepository extends JpaRepository<Discount, String> {
    @Modifying
    @Query(value = "insert into discounts (name, created_at, end_date, modified_at)\n" +
            "    values (:name, :create_at, :end_date, :modified_at);\n" +
            "    create event :name\n" +
            "        on schedule at :end_date\n" +
            "        do begin\n" +
            "        delete from product_discounts where name_discount = :name;\n" +
            "        delete from discounts where name = :name;\n" +
            "        drop event :name;\n" +
            "    end;", nativeQuery = true)
    public void saveDiscountEvent(@Param("name") String name, @Param("create_at") Date createdAt, @Param("modified_at") Date modifiedAt, @Param("end_date") Date endDate);
}
