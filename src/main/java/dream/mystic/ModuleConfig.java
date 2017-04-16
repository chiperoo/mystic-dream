package dream.mystic;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.katharsis.core.internal.boot.TransactionRunner;
import io.katharsis.jpa.JpaModule;
import io.katharsis.jpa.JpaRepositoryConfig;
import io.katharsis.jpa.query.criteria.JpaCriteriaExpressionFactory;
import io.katharsis.jpa.query.criteria.JpaCriteriaQueryFactory;
import io.katharsis.validation.ValidationModule;

@Configuration
public class ModuleConfig {

	@Autowired
	private EntityManager em;

	@Autowired
	private TransactionRunner transactionRunner;

	/**
	 * Bean Validation
	 * @return module
	 */
	@Bean
	public ValidationModule validationModule() {
		return ValidationModule.newInstance();
	}
	
	/**
	 * Expose JPA entities as repositories.
	 * @return module
	 */
//	@Bean
//	public JpaModule jpaModule() {
//		JpaModule module = JpaModule.newServerModule(em, transactionRunner);
//
//		// directly expose entity
//		module.addRepository(JpaRepositoryConfig.builder(ScheduleEntity.class).build());
//
//		// additionally expose entity as a mapped dto
//		module.addRepository(JpaRepositoryConfig.builder(ScheduleEntity.class, ScheduleDto.class, new ScheduleMapper()).build());
//		JpaCriteriaQueryFactory queryFactory = (JpaCriteriaQueryFactory) module.getQueryFactory();
//
//		// register a computed a attribute
//		// you may consider QueryDSL or generating the Criteria query objects.
//		queryFactory.registerComputedAttribute(ScheduleEntity.class, "upperName", String.class,
//				new JpaCriteriaExpressionFactory<From<?, ScheduleEntity>>() {
//
//					@SuppressWarnings({ "rawtypes", "unchecked" })
//					@Override
//					public Expression<String> getExpression(From<?, ScheduleEntity> entity, CriteriaQuery<?> query) {
//						CriteriaBuilder builder = em.getCriteriaBuilder();
//						return builder.upper((Expression) entity.get("name"));
//					}
//				});
//		return module;
//	}
	

}
