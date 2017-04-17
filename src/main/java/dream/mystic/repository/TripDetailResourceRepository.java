/**
 *  This class is used for Katharsis so that it can publish API operations
 */
package dream.mystic.repository;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dream.mystic.domain.TripDetail;
import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryBase;
import io.katharsis.resource.list.ResourceList;

@Component
public class TripDetailResourceRepository extends ResourceRepositoryBase<TripDetail,Long> {

	private static final AtomicLong ID_GENERATOR = new AtomicLong();
	
	@Autowired
	private TripDetailRepository tripDetailRepository;
	
	public TripDetailResourceRepository() {
		super(TripDetail.class);
	}

	@Override
    public synchronized <S extends TripDetail> S save(S tripDetail) {
		if(tripDetail.getId() == null) {
			long count = tripDetailRepository.count();
			if(count > ID_GENERATOR.get()) {
				ID_GENERATOR.set(tripDetailRepository.count() + 1);
			}
			tripDetail.setId(ID_GENERATOR.getAndIncrement());
		}
            tripDetailRepository.save(tripDetail);
            return tripDetail;
	}
	
	@Override
	public TripDetail findOne(Long tripDetailId, QuerySpec arg0) {
		return tripDetailRepository.findOne(tripDetailId);
	}
	
	@Override
	public ResourceList<TripDetail> findAll(QuerySpec arg0) {
		return arg0.apply(tripDetailRepository.findAll());
	}

}
