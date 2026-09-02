package com.example.hr.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.hr.document.EmployeeDocument;

public interface EmployeeDocumentRepository extends MongoRepository<EmployeeDocument, String>{
	List<EmployeeDocument> findByBirthYearBetweenAndJobStyle(int fromYear,int toYear,String jobStyle);
    @Query("""
            {
              "birthYear": { "$gte": ?0, "$lte": ?1 },
              "jobStyle": ?2
            }
            """)
	List<EmployeeDocument> bulGetir(int fromYear,int toYear,String jobStyle);
	
}
