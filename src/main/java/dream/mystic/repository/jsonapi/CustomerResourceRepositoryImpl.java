/**
 *  This class is used for Katharsis so that it can publish API operations.
 *  This class relies on the old way to publish meta information and pagination links via
 *  the UserResourceRepository interface.
 *  
 *  This is here for the unit tests; which needs to attach to an interface and not a class.
 *  
 *  For the Katharsis 3.0 way of publishing meta information and pagination links, look at the domain 
 *  objects and the @JsonApiMetaInformation and @JsonApiLinksInformation annotations.
 */
package dream.mystic.repository.jsonapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dream.mystic.domain.Customer;
import dream.mystic.repository.CustomerRepository;
import io.katharsis.errorhandling.exception.ForbiddenException;
import io.katharsis.errorhandling.exception.ResourceNotFoundException;
import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryBase;
import io.katharsis.resource.list.ResourceList;

@Component
public class CustomerResourceRepositoryImpl extends ResourceRepositoryBase<Customer,Long> 
											implements CustomerResourceRepository {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public CustomerResourceRepositoryImpl() {
		super(Customer.class);
	}

	@Override
    public synchronized <S extends Customer> S save(S customer) {
		customerRepository.save(customer);
		return customer;
	}
	
	@Override
	public synchronized Customer findOne(Long customerId, QuerySpec arg0) {
		Customer customer =  customerRepository.findOne(customerId);
		if(customer == null) {
			throw new ResourceNotFoundException("Customer record not found");
		}
		return customer;
	}
		
//	@Override
//	public synchronized ResourceList<Customer> findAll(QuerySpec arg0) {
//		return arg0.apply(customerRepository.findAll());
//	}
	@Override
	public synchronized CustomerList findAll(QuerySpec arg0) {
		CustomerList list = new CustomerList();
		list.setMeta(new CustomerListMeta());
		list.setLinks(new CustomerListLinks());
		arg0.apply(customerRepository.findAll(), list);
		return list;
	}
	
	@Override
	public void delete(Long customerId) {
		throw new ForbiddenException("Delete is not allowed");
	}
}
