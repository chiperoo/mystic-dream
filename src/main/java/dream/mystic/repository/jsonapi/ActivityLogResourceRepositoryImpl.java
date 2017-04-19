/**
 *  This class is used for Katharsis so that it can publish API operations
 */
package dream.mystic.repository.jsonapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dream.mystic.domain.ActivityLog;
import dream.mystic.repository.ActivityLogRepository;
import io.katharsis.errorhandling.exception.ForbiddenException;
import io.katharsis.errorhandling.exception.ResourceNotFoundException;
import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryBase;
import io.katharsis.resource.list.ResourceList;

@Component
public class ActivityLogResourceRepositoryImpl extends ResourceRepositoryBase<ActivityLog,Long> {

	@Autowired
	private ActivityLogRepository activityLogRepository;
	
	public ActivityLogResourceRepositoryImpl() {
		super(ActivityLog.class);
	}

	@Override
    public synchronized <S extends ActivityLog> S save(S tripDetail) {
            activityLogRepository.save(tripDetail);
            return tripDetail;
	}
	
	@Override
	public ActivityLog findOne(Long tripDetailId, QuerySpec arg0) {
		ActivityLog activityLog = activityLogRepository.findOne(tripDetailId);
		if(activityLog == null) {
			throw new ResourceNotFoundException("Activity Log record not found");
		}
		return activityLog;
	}
	
	@Override
	public ResourceList<ActivityLog> findAll(QuerySpec arg0) {
		return arg0.apply(activityLogRepository.findAll());
	}
	
	@Override
	public void delete(Long activityLogId) {
		throw new ForbiddenException("Delete is not allowed");
	}

}
