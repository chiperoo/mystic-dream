/**
 *  This interface is the old way Katharsis published meta information and links.
 *  As of April, 2017, the KatharsisClient (for unit tests) still needs to use an interface 
 *  and not a class.
 *  
 *  The Katharsis 3.0 way of publishing meta information and pagination links, look at the domain 
 *  objects and the @JsonApiMetaInformation and @JsonApiLinksInformation annotations.
 *  
 */
package dream.mystic.repository.jsonapi;

import dream.mystic.domain.User;
import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.links.PagedLinksInformation;
import io.katharsis.resource.list.ResourceListBase;
import io.katharsis.resource.meta.PagedMetaInformation;

public interface UserResourceRepository extends ResourceRepositoryV2<User, Long> {

	public class UserListMeta implements PagedMetaInformation {

		private Long totalResourceCount;

		@Override
		public Long getTotalResourceCount() {
			return totalResourceCount;
		}

		@Override
		public void setTotalResourceCount(Long totalResourceCount) {
			this.totalResourceCount = totalResourceCount;
		}
	}

	public class UserListLinks implements PagedLinksInformation {

		private String first;
		private String last;
		private String next;
		private String prev;

		@Override
		public String getFirst() {
			return first;
		}

		@Override
		public void setFirst(String first) {
			this.first = first;
		}

		@Override
		public String getLast() {
			return last;
		}

		@Override
		public void setLast(String last) {
			this.last = last;
		}

		@Override
		public String getNext() {
			return next;
		}

		@Override
		public void setNext(String next) {
			this.next = next;
		}

		@Override
		public String getPrev() {
			return prev;
		}

		@Override
		public void setPrev(String prev) {
			this.prev = prev;
		}

	}

	public class UserList extends ResourceListBase<User, UserListMeta, UserListLinks> {

	}

	@Override
	public UserList findAll(QuerySpec querySpec);

}
