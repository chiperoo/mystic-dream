/**
 *  This class is used for Katharsis so that it can publish API operations
 */
package dream.mystic.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dream.mystic.domain.Customer;
import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryBase;
import io.katharsis.resource.list.ResourceList;

@Component
public class CustomerResourceRepository extends ResourceRepositoryBase<Customer,Long> {

	@Autowired
	private CustomerRepository customerRepository;
	
	public CustomerResourceRepository() {
		super(Customer.class);
	}

	@Override
    public synchronized <S extends Customer> S save(S customer) {
            customerRepository.save(customer);
            return customer;
	}
	
	@Override
	public Customer findOne(Long customerId, QuerySpec arg0) {
		return customerRepository.findOne(customerId);
	}
	
	@Override
	public ResourceList<Customer> findAll(QuerySpec arg0) {
		return arg0.apply(customerRepository.findAll());
	}
}
