package com.example.jumlacycle.Repository;

import com.example.jumlacycle.Model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends JpaRepository<Facility,Integer> {
    Facility findFacilityById(Integer id);
}
