/**
 *  This class is used for Katharsis so that it can publish API operations
 */
package dream.mystic.repository;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dream.mystic.domain.Customer;
import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryBase;
import io.katharsis.resource.list.ResourceList;

@Component
public class CustomerResourceRepository extends ResourceRepositoryBase<Customer,Long> {
	
	private static final AtomicLong ID_GENERATOR = new AtomicLong();
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public CustomerResourceRepository() {
		super(Customer.class);
	}

	@Override
    public synchronized <S extends Customer> S save(S customer) {
//		if(customer.getId() == null) {
//			long count = customerRepository.count();
//			if(count > ID_GENERATOR.get()) {
//				ID_GENERATOR.set(customerRepository.count() + 1);
//			}
//			customer.setId(ID_GENERATOR.getAndIncrement());
//		}
		customerRepository.save(customer);
		return customer;
	}
	
	@Override
	public synchronized Customer findOne(Long customerId, QuerySpec arg0) {
		return customerRepository.findOne(customerId);
	}
	
	@Override
	public synchronized ResourceList<Customer> findAll(QuerySpec arg0) {
		return arg0.apply(customerRepository.findAll());
	}
}
