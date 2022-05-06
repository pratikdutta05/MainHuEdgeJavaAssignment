package com.hashedin.hu.repository;

import com.hashedin.hu.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    //saveAll(List<Category>);
}
