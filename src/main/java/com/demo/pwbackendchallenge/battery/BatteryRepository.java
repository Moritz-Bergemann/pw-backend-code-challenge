package com.demo.pwbackendchallenge.battery;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatteryRepository extends CrudRepository<Battery, Long> {
    List<Battery> findByPostCodeBetweenOrderByNameAsc(int minPostCode, int maxPostCode);
}
