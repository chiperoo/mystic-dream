/**
 *  This class is used for Katharsis so that it can publish API operations
 */
package dream.mystic.repository.jsonapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dream.mystic.domain.User;
import dream.mystic.repository.UserRepository;
import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryBase;
import io.katharsis.resource.list.ResourceList;

@Component
public class UserResourceRepository extends ResourceRepositoryBase<User,Long> {
	
	@Autowired
	private UserRepository userRepository;
	
	public UserResourceRepository() {
		super(User.class);
	}

	@Override
    public synchronized <S extends User> S save(S user) {
		userRepository.save(user);
		return user;
	}
	
	@Override
	public synchronized User findOne(Long customerId, QuerySpec arg0) {
		return userRepository.findOne(customerId);
	}
	
	@Override
	public synchronized ResourceList<User> findAll(QuerySpec arg0) {
		return arg0.apply(userRepository.findAll());
	}

}
