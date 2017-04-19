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

import dream.mystic.domain.User;
import dream.mystic.repository.UserRepository;
import io.katharsis.errorhandling.exception.ForbiddenException;
import io.katharsis.errorhandling.exception.ResourceNotFoundException;
import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryBase;
import io.katharsis.resource.list.ResourceList;

@Component
public class UserResourceRepositoryImpl extends ResourceRepositoryBase<User,Long> 
										implements UserResourceRepository {
	
	@Autowired
	private UserRepository userRepository;
	
	public UserResourceRepositoryImpl() {
		super(User.class);
	}

	@Override
    public synchronized <S extends User> S save(S user) {
		userRepository.save(user);
		return user;
	}
	
	@Override
	public synchronized User findOne(Long customerId, QuerySpec arg0) {
		User user = userRepository.findOne(customerId);
		if(user == null) {
			throw new ResourceNotFoundException("Activity Log record not found");
		}
		return user;
	}
	
//	@Override
//	public ResourceList<User> findAll(QuerySpec arg0) {
//		return arg0.apply(userRepository.findAll());
//	}
	@Override
	public synchronized UserList findAll(QuerySpec arg0) {
		UserList list = new UserList();
		list.setMeta(new UserListMeta());
		list.setLinks(new UserListLinks());
		arg0.apply(userRepository.findAll(), list);
		return list;
	}

	@Override
	public void delete(Long userId) {
		throw new ForbiddenException("Delete is not allowed");
	}
}
