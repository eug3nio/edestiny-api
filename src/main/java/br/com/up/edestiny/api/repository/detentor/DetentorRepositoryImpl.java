package br.com.up.edestiny.api.repository.detentor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.up.edestiny.api.model.Detentor;

public class DetentorRepositoryImpl implements DetentorRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Detentor findByEmail(String email) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT d FROM Detentor d WHERE d.email = :email ");

		Query q = entityManager.createQuery(sql.toString());

		q.setParameter("email", email);
		return (Detentor) q.getSingleResult();
	}
}
