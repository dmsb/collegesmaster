package br.com.collegesmaster.model.challengeresponse.dataprovider.impl;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.collegesmaster.model.challengeresponse.dataprovider.RankingDataProvider;
import br.com.collegesmaster.model.challengeresponse.impl.RankingImpl;
import br.com.collegesmaster.model.challengeresponse.impl.RankingImpl_;
import br.com.collegesmaster.model.institute.Institute;
import br.com.collegesmaster.model.institute.impl.CourseImpl;
import br.com.collegesmaster.model.institute.impl.CourseImpl_;
import br.com.collegesmaster.model.institute.impl.DisciplineImpl_;
import br.com.collegesmaster.model.institute.impl.InstituteImpl;
import br.com.collegesmaster.model.institute.impl.InstituteImpl_;
import br.com.collegesmaster.model.security.User;
import br.com.collegesmaster.qualifiers.UserDatabase;

@Dependent
public class RankingDataProviderImpl implements RankingDataProvider {
	
	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;
	
	@Override
	public List<RankingImpl> findBestPositionsByPeriod(String semester, Institute userInstitute) {
		final CriteriaQuery<RankingImpl> criteriaQuery = cb.createQuery(RankingImpl.class);
		final Root<RankingImpl> rankingRoot = criteriaQuery.from(RankingImpl.class);
		
		final Join<CourseImpl, InstituteImpl> instituteJoin = rankingRoot
				.join(RankingImpl_.discipline)
				.join(DisciplineImpl_.course)
				.join(CourseImpl_.institute);
		
		final Predicate institutePredicate = cb.equal(instituteJoin, userInstitute);
		final Predicate semesterPredicate = cb.equal(instituteJoin.get(InstituteImpl_.semester), semester);		
		final Order bestPositionsPredicate = cb.desc(rankingRoot.get(RankingImpl_.totalPontuation));
		
		criteriaQuery
			.select(rankingRoot)
			.where(semesterPredicate, institutePredicate)
			.orderBy(bestPositionsPredicate);
		final TypedQuery<RankingImpl> typedQuery = em.createQuery(criteriaQuery)
				.setMaxResults(5);
		return typedQuery.getResultList();
	}

	@Override
	public RankingImpl findByUserAndPeriod(final User user, String semester) {
		final CriteriaQuery<RankingImpl> criteriaQuery = cb.createQuery(RankingImpl.class);
		final Root<RankingImpl> RankingRoot = criteriaQuery.from(RankingImpl.class);
		final Predicate existUserPredicate = cb.equal(RankingRoot.get(RankingImpl_.user), user);
		final Predicate currentPeriodPredicate = cb.equal(RankingRoot
				.join(RankingImpl_.discipline)
				.join(DisciplineImpl_.course)
				.join(CourseImpl_.institute)
				.get(InstituteImpl_.semester), semester);
		criteriaQuery
			.select(RankingRoot)
			.where(existUserPredicate, currentPeriodPredicate);
		final TypedQuery<RankingImpl> typedQuery = em.createQuery(criteriaQuery);
		try {
			return typedQuery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
