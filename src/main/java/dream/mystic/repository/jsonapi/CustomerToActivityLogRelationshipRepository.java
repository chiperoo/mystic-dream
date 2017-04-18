/**
 *  This class is used for Katharsis so that it can publish API operations
 *  
 *  Relationships need to be explicitly defined
 *  
 */
package dream.mystic.repository.jsonapi;

import org.springframework.stereotype.Component;

import dream.mystic.domain.ActivityLog;
import dream.mystic.domain.Customer;
import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class CustomerToActivityLogRelationshipRepository extends RelationshipRepositoryBase<Customer, Long, ActivityLog, Long> {

	public CustomerToActivityLogRelationshipRepository() {
		super(Customer.class, ActivityLog.class);
	}
}
