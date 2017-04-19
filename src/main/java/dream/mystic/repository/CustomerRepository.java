package dream.mystic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import dream.mystic.domain.Customer;

//@Transactional(readOnly = true)
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findById(Long id);
}
