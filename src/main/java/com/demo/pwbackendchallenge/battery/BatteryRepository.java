package com.demo.pwbackendchallenge.battery;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatteryRepository extends CrudRepository<Battery, Long> {
    /**
     * Retrieve all batteries between minPostcode and maxPostcode, inclusive
     */
    List<Battery> findByPostcodeBetweenOrderByNameAsc(int minPostcode, int maxPostcode);
}
