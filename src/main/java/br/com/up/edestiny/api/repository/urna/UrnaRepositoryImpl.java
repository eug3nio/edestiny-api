package br.com.up.edestiny.api.repository.urna;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.up.edestiny.api.model.Urna;
import br.com.up.edestiny.api.model.enums.TipoMedida;
import br.com.up.edestiny.api.repository.dto.UrnaDTO;
import br.com.up.edestiny.api.repository.filter.UrnaFilter;

@SuppressWarnings("unchecked")
public class UrnaRepositoryImpl implements UrnaRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Urna> filtrar(UrnaFilter filter, Pageable pageable) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT u FROM Urna u ");
		sql.append(" WHERE 1=1 ").append(getWhere(filter));

		Query q = manager.createQuery(sql.toString());

		setParameter(q, filter);

		adicionarRestricoesDePaginacao(q, pageable);

		return new PageImpl<>(q.getResultList(), pageable, total(filter));
	}

	@Override
	public Page<UrnaDTO> resumir(UrnaFilter filter, Pageable pageable) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT new br.com.up.edestiny.api.repository.dto.UrnaDTO(u) FROM Urna u ");
		sql.append(" WHERE 1=1 ").append(getWhere(filter));

		Query q = manager.createQuery(sql.toString());

		setParameter(q, filter);

		adicionarRestricoesDePaginacao(q, pageable);

		return new PageImpl<>(q.getResultList(), pageable, total(filter));
	}

	/**
	 * 
	 * @param filter
	 * @return
	 */
	private String getWhere(UrnaFilter filter) {
		StringBuilder where = new StringBuilder();

		if (!StringUtils.isEmpty(filter.getDetalhamento())) {
			where.append(" AND LOWER(u.detalhamento) LIKE :detalhamento ");
		}

		if (!StringUtils.isEmpty(filter.getTipoMedida())) {
			where.append(" AND u.tipoMedida = :tipoMedida ");
		}

		return where.toString();
	}

	/**
	 * 
	 * @param q
	 * @param filter
	 */
	private void setParameter(Query q, UrnaFilter filter) {
		if (!StringUtils.isEmpty(filter.getDetalhamento())) {
			q.setParameter("detalhamento", filter.getDetalhamento() + "%");
		}

		if (!StringUtils.isEmpty(filter.getTipoMedida())) {
			TipoMedida tm = TipoMedida.findByDescricao(filter.getTipoMedida());
			q.setParameter("tipoMedida", tm);
		}

	}

	/**
	 * 
	 * @param q
	 * @param pageable
	 */
	private void adicionarRestricoesDePaginacao(Query q, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		q.setFirstResult(primeiroRegistroDaPagina);
		q.setMaxResults(totalRegistrosPorPagina);

	}

	/**
	 * 
	 * @param filter
	 * @return
	 */
	private Long total(UrnaFilter filter) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT COUNT(u) FROM Urna u ");
		sql.append(" WHERE 1=1 ").append(getWhere(filter));

		Query q = manager.createQuery(sql.toString());

		setParameter(q, filter);

		Number result = q.getFirstResult();

		return result.longValue();
	}
}
