package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.Category;
import com.metan.websalesecurityequipment.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, String> {
    public List<Product> findAllByOrderByNameAsc();

    @Query(value = "select p.*, sum(oi.quantity) as tong from products p inner join order_items oi on p.product_id = oi.product_id " +
            "group by p.product_id, p.discount_percent_base, p.quantity, p.created_at, " +
            " p.modified_at, p.brand_id, " +
            "p.long_desc, p.name, p.price, p.short_desc, p.slug, " +
            "p.thumbnail, p.title " +
            "order by tong desc limit 10", nativeQuery = true)
    public List<Product> findTopProduct();

    @Query(value = "SELECT product_id, created_at, discount_percent_base, long_desc, modified_at, name, price, quantity, short_desc, slug, thumbnail, title, brand_id FROM(SELECT p.*, @rownum \\:= @rownum + 1 AS rn FROM products p, (SELECT @rownum \\:= 0) T1) T2 ORDER BY rn DESC limit 10", nativeQuery = true)
    public List<Product> findProductsNew();

    @Query(value = "select p.* from products p join categories c on p.category_id = c.category_id join categories cp on c.parent_category_id = cp.category_id where cp.name = :name or cp.name = :other_name order by rand() limit :limit", nativeQuery = true)
    public List<Product> findProductByNameParentCategory(@Param("name") String name, @Param("other_name") String otherName, @Param("limit") int limit);

    @Query(value = "select p.* from products p join categories c on p.category_id = c.category_id left join categories cp on cp.category_id = c.parent_category_id where cp.category_id = :id order by rand()", nativeQuery = true)
    public List<Product> findProductsByCategory(@Param("id") long id);

    @Query(value = "SELECT * FROM products ORDER BY RAND() LIMIT ?1", nativeQuery = true)
    public List<Product> findTopNumberRandom(int top);

    public Product findBySlug(String slug);

    /**
     *
     * @param productId
     * @param userId
     * @return product if the user's purchased order is completed, not valid return null
     */
    @Query(value = "select p.product_id,oi.order_id,o.order_id,u.user_id from products p join order_items oi on p.product_id=oi.product_id join orders o on o.order_id= oi.order_id join users u on u.user_id= o.user_id\n" +
            "where p.product_id=?1 and u.user_id=?2 and o.order_status='completed'", nativeQuery = true)
    public Product checkBuyCompletedProductByUser(String productId, long userId);

    //Hao
    public List<Product> findByNameContaining(String name);

    public Page<Product> findAll(Pageable pageable);

    @Query(value = "select p.*, sum(order_items.quantity) as totalQuan from (order_items) right outer join (products p) on p.product_id =order_items.product_id join categories c on c.category_id = p.category_id join brands b on b.brand_id = p.brand_id " +
            "  where concat(p.title, c.name, b.name) like %?1% " +
            " group by p.product_id ", nativeQuery = true)
    public Page<Product> findByNameContaining(String name, Pageable pageable);

    @Query(value = "select p.*, sum(order_items.quantity) as totalQuan from (order_items) right outer join (products p) on p.product_id =order_items.product_id join product_reviews pr on pr.product_id=p.product_id " +
            "join categories c on c.category_id = p.category_id join brands b on b.brand_id = p.brand_id \n" +
            "            where pr.rating >= ?1\n" +
            "            and (p.price >=?2 and p.price<=?3) and concat(p.title, c.name, b.name) like %?4%\n" +
            " group by p.product_id ", nativeQuery = true)
    public Page<Product> searchByNameRating(int rating, double minPrice, double maxPrice, String name, Pageable pageable);

    @Query(value = "select p.*, sum(order_items.quantity) as totalQuan from (order_items) right outer join (products p) on p.product_id =order_items.product_id join (categories c) \n" +
            "on c.category_id = p.category_id join (product_reviews pr) on pr.product_id=p.product_id join brands b on b.brand_id = p.brand_id \n" +
            " where (p.brand_id in (?2) \n" +
            "            OR (c.parent_category_id in (?1) or c.category_id in (?1) )) \n" +
            "            and pr.rating >= ?3 " +
            " and (p.price >=?4 and p.price<=?5) and concat(p.title, c.name, b.name)  like %?6% " +
            " group by p.product_id ", nativeQuery = true)
    public Page<Product> searchByNameCateBrandRating(List<Integer> cates, List<Integer> brands, int rating, double minPrice, double maxPrice, String name, Pageable pageable);

    @Query(value = "select p.*, sum(order_items.quantity) as totalQuan from (order_items) right outer join (products p) on p.product_id =order_items.product_id join (categories c) \n" +
            "on c.category_id = p.category_id join brands b on b.brand_id = p.brand_id  \n" +
            " where (p.brand_id in ?2 \n" +
            "            OR( c.parent_category_id in ?1 or c.category_id in ?1)) \n" +
            " and (p.price >=?3 and p.price<=?4) and concat(p.title, c.name, b.name) like %?5% " +
            "group by p.product_id ", nativeQuery = true)
    public Page<Product> searchByNameCateBrand(List<Integer> cates, List<Integer> brands, double minPrice, double maxPrice, String name, Pageable pageable);

    @Query(value = "select products.*, sum(order_items.quantity) as totalQuan from (order_items) right outer join (products) on products.product_id =order_items.product_id join categories c on c.category_id = products.category_id join brands b on b.brand_id = products.brand_id \n" +
            "where (products.price >=?1 and products.price<=?2) and concat(products.title, c.name, b.name) like %?3% " +
            "group by products.product_id ", nativeQuery = true)
    public Page<Product> searchByPrice(double minPrice, double maxPrice, String name, Pageable pageable);

}
