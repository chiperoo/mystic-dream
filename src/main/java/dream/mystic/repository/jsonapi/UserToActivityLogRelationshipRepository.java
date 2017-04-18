/**
 *  This class is used for Katharsis so that it can publish API operations
 *  
 *  Relationships need to be explicitly defined.
 *  
 */
package dream.mystic.repository.jsonapi;

import org.springframework.stereotype.Component;

import dream.mystic.domain.ActivityLog;
import dream.mystic.domain.User;
import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class UserToActivityLogRelationshipRepository extends RelationshipRepositoryBase<User, Long, ActivityLog, Long> {

	public UserToActivityLogRelationshipRepository() {
		super(User.class, ActivityLog.class);
	}

}
