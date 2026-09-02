package com.example.hr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.hr.document.EmployeeDocument;
import com.example.hr.entity.EmployeeEntity;

public interface EmployeeEntityRepository extends JpaRepository<EmployeeEntity, String>{
	List<EmployeeDocument> findByBirthYearBetweenAndJobStyle(int fromYear,int toYear,String jobStyle);
    @Query(value="""
           select e
           from EmployeeEntity e
           where e.birthYear >= :fromYear 
           and e.birthYear <= :toYear
           and e.jobStyle = :jobStyle
            """)
	List<EmployeeDocument> bulGetir(int fromYear,int toYear,String jobStyle);
    @Query(value="""
           select e
           from employees e
           where e.byear >= :fromYear 
           and e.byear <= :toYear
           and e.JOBSTYLE = :jobStyle
            """,nativeQuery = true)
    List<EmployeeDocument> araGetir(int fromYear,int toYear,String jobStyle);
	
}
