
// Jpa repository are defined for method of storing , updating,and extracting data stored from java application to backend
package com.example.bookProjectTekSystem.repository;

import com.example.bookProjectTekSystem.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
