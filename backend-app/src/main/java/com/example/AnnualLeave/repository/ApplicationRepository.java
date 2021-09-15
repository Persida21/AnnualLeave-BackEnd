package com.example.AnnualLeave.repository;
import com.example.AnnualLeave.model.Application;
import com.example.AnnualLeave.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

@EnableJpaRepositories
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @Query(value = "SELECT * FROM Application t where t.user = :id" , nativeQuery = true )
    Iterable<Application> findByUser_id(@Param("id") Long id);



}
