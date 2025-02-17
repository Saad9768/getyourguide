package com.getourguide.interview.repository;

import com.getourguide.interview.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
	Page<Supplier> findByNameContainingOrAddressContainingOrZipContainingOrCityContainingOrCountryContaining(
			String name, String address, String zip, String city, String country, Pageable pageable);
}
