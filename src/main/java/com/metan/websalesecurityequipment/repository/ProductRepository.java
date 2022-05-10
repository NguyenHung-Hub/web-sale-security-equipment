package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, String> {
    public List<Product> findAllByOrderByNameAsc();

    @Query(value = "select p.*, sum(oi.quantity) as tong from products p inner join order_items oi on p.product_id = oi.product_id " +
            "group by p.product_id, p.quantity, p.created_at, " +
            " p.modified_at, p.brand_id, p.category_id, p.discount_id, " +
            "p.long_desc, p.name, p.price, p.short_desc, p.slug, " +
            "p.thumbnail, p.title " +
            "order by tong desc limit 4", nativeQuery = true)
    public List<Product> findTopProduct();

    @Query(value = "SELECT * FROM products ORDER BY RAND() LIMIT ?1", nativeQuery = true)
    public List<Product> findTopNumberRandom(int top);

    public Product findBySlug(String slug);
    //Hao
    public List<Product> findByNameContaining(String name);

    public Page<Product> findAll(Pageable pageable);

    @Query(value = "select p.*, sum(order_items.quantity) as totalQuan from (order_items) right outer join (products p) on p.product_id =order_items.product_id " +
            "  where p.name like %?1% " +
            " group by p.product_id ", nativeQuery = true)
    public Page<Product> findByNameContaining(String name, Pageable pageable);

    @Query(value = "select p.*, sum(order_items.quantity) as totalQuan from (order_items) right outer join (products p) on p.product_id =order_items.product_id join product_reviews pr on pr.product_id=p.product_id\n" +
            "            where pr.rating >= ?1\n" +
            "            and (p.price >=?2 and p.price<=?3) and p.name like %?4%\n" +
            " group by p.product_id ", nativeQuery = true)
    public Page<Product> searchByNameRating(int rating ,double minPrice, double maxPrice,String name,Pageable pageable);

    @Query(value = "select distinct(p.product_id), p.*, sum(order_items.quantity) as totalQuan from (order_items) right outer join (products p) on p.product_id =order_items.product_id join (categories c) \n" +
            "on c.category_id = p.category_id join product_reviews pr on pr.product_id=p.product_id \n" +
            " where (p.brand_id in (?2) \n" +
            "            OR (c.subcategory_id in (?1) or c.category_id in ?1)) \n" +
            "            and pr.rating >= ?3 " +
            " and (p.price >=?4 and p.price<=?5) and p.name like %?6% " +
            " group by p.product_id ", nativeQuery = true)
    public Page<Product> searchByNameCateBrandRating(List<Integer> cates,List<Integer> brands, int rating,double minPrice, double maxPrice,String name,Pageable pageable);
/*?3 <= (select avg(rating) from product_reviews r where r.product_id=p.product_id)*/
    @Query(value = "select p.*, sum(order_items.quantity) as totalQuan from (order_items) right outer join (products p) on p.product_id =order_items.product_id join (categories c) \n" +
            "on c.category_id = p.category_id \n" +
            " where (p.brand_id in ?2 \n" +
            "            OR( c.subcategory_id in ?1 or c.category_id in ?1)) \n" +
            " and (p.price >=?3 and p.price<=?4) and p.name like %?5% " +
            "group by p.product_id ", nativeQuery = true)
    public Page<Product> searchByNameCateBrand(List<Integer> cates,List<Integer> brands,double minPrice, double maxPrice,String name,Pageable pageable);

    @Query(value = "select products.*, sum(order_items.quantity) as totalQuan from (order_items) right outer join (products) on products.product_id =order_items.product_id \n" +
            "where (products.price >=?1 and products.price<=?2) and products.name like %?3% " +
            "group by products.product_id ", nativeQuery = true)
    public Page<Product> searchByPrice(double minPrice, double maxPrice, String name, Pageable pageable);

}
