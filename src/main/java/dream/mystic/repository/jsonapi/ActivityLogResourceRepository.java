/**
 *  This class is used for Katharsis so that it can publish API operations
 */
package dream.mystic.repository.jsonapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dream.mystic.domain.ActivityLog;
import dream.mystic.repository.ActivityLogRepository;
import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryBase;
import io.katharsis.resource.list.ResourceList;

@Component
public class ActivityLogResourceRepository extends ResourceRepositoryBase<ActivityLog,Long> {

	@Autowired
	private ActivityLogRepository activityLogRepository;
	
	public ActivityLogResourceRepository() {
		super(ActivityLog.class);
	}

	@Override
    public synchronized <S extends ActivityLog> S save(S tripDetail) {
            activityLogRepository.save(tripDetail);
            return tripDetail;
	}
	
	@Override
	public ActivityLog findOne(Long tripDetailId, QuerySpec arg0) {
		return activityLogRepository.findOne(tripDetailId);
	}
	
	@Override
	public ResourceList<ActivityLog> findAll(QuerySpec arg0) {
		return arg0.apply(activityLogRepository.findAll());
	}

}
