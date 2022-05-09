package com.hashedin.hu.repository;

import com.hashedin.hu.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {

    @Query(nativeQuery = true,value = "SELECT * FROM cart c WHERE c.status=?1 and c.user_id=?2")
    List<Cart> searchByUserID( int status, int userId);

     //void deleteByIdAnduserId(Integer cartId, Integer userId);
}
