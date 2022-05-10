package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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

    @Query(value = "select * from products p join brands b on p.brand_id = b.brand_id join categories c on c.category_id = p.category_id " +
            "where concat(c.name, b.name) REGEXP ?1", nativeQuery = true)
    public Page<Product> searchByNameCateBrand(String name,Pageable pageable);

    public Page<Product> findAll(Pageable pageable);

    public Page<Product> findByNameContaining(String name, Pageable pageable);

    @Query(value = "select * from products p join brands b on p.brand_id= b.brand_id join categories c on c.category_id = p.category_id " +
            "where (b.brand_id in ?2 " +
            "OR c.category_id in ?1" +
            "OR c.subcategory_id in ?1" +
            " OR (p.price >=?3 and p.price<=?4)) and p.name like %?5%", nativeQuery = true)
    public Page<Product> searchByNameCateBrand(List<Integer> cates,List<Integer> brands,double minPrice, double maxPrice,String name,Pageable pageable);

    @Query(value = "select distinct(p.product_id), p.* from products p join brands b on p.brand_id= b.brand_id join categories c \n" +
            "on c.category_id = p.category_id join product_reviews pr on pr.product_id=p.product_id\n" +
            " where (b.brand_id in (?2) \n" +
            "            OR c.category_id in (?1) \n" +
            "            OR c.subcategory_id in (?1) \n" +
            "            or pr.rating >= ?3" +
            " OR (p.price >=?4 and p.price<=?5)) and p.name like %?6%", nativeQuery = true)
    public Page<Product> searchByNameCateBrandRating(List<Integer> cates,List<Integer> brands, float rating,double minPrice, double maxPrice,String name,Pageable pageable);
//, sum(oi.quantity) as totalQuan
   /* @Query(value = "       select products,SUM(order_items.quantity) as totalQuan from order_items RIGHT JOIN  products on order_items.product_id=products.product_id  join brands on products.brand_id= brands.brand_id join categories on categories.category_id = products.category_id\n" +
            "            where (brands.brand_id in (null)  " +
            "            OR categories.subcategory_id in (null) " +
            "            OR (products.price >=0 and products.price<=99999999999999)) and products.name like '% %' " +
            "            group by products.product_id order by totalQuan desc", nativeQuery = true)*/
    @Query("SELECT p, sum(o.quantity) as totalQuan FROM Product p left join p.orderItems o inner join p.brand b inner join p.category c " +
            "where (b.brandId in ?2 " +
            "OR c.category in ?1 " +
            "OR (p.price >= ?3 and p.price <= ?4)) " +
            "and p.name like %?5%" +
            " group by p.productId order by  totalQuan" )
    public Page<Object[]> sortByOrderDetail(List<Integer> cates,List<Integer> brands,double minPrice, double maxPrice,String name,Pageable pageable);

    @Query("SELECT p, sum(o.quantity) as totalQuan FROM Product p left join p.orderItems o inner join p.brand b inner join p.category c join p.productReviews r" +
            " where (b.brandId in ?2 " +
            "OR c.category in ?1 " +
            "or r.rating >= ?3 " +
            "OR (p.price >= ?4 and p.price <= ?5)) " +
            "and p.name like %?6%" +
            " group by p.productId order by  totalQuan" )
    public Page<Object[]> sortByOrderDetailWhenHaveRating(List<Integer> cates,List<Integer> brands,int rating, double minPrice, double maxPrice,String name,Pageable pageable);



}
