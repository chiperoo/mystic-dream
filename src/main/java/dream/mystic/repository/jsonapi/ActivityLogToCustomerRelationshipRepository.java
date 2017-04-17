/**
 *  This class is used for Katharsis so that it can publish API operations
 */
package dream.mystic.repository.jsonapi;

import org.springframework.stereotype.Component;

import dream.mystic.domain.ActivityLog;
import dream.mystic.domain.Customer;
import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class ActivityLogToCustomerRelationshipRepository extends RelationshipRepositoryBase<ActivityLog, Long, Customer, Long> {

	public ActivityLogToCustomerRelationshipRepository() {
		super(ActivityLog.class, Customer.class);
	}

}
