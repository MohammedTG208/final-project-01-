package com.example.jumlacycle.Repository;

import com.example.jumlacycle.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AuthRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
    User findUserById(Integer id);
}
