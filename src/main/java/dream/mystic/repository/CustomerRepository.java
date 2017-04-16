package dream.mystic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dream.mystic.domain.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findById(Long id);
}
