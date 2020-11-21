package br.com.up.edestiny.api.repository.coleta;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.up.edestiny.api.model.Coleta;
import br.com.up.edestiny.api.model.enums.SituacaoColeta;
import br.com.up.edestiny.api.repository.dto.ColetaDTO;
import br.com.up.edestiny.api.repository.filter.ColetaFilter;

@SuppressWarnings("unchecked")
public class ColetaRepositoryImpl implements ColetaRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Coleta> filtrar(ColetaFilter filter, Pageable pageable) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT c FROM Coleta c ");
		sql.append(getWhere(filter));

		Query q = manager.createQuery(sql.toString());

		setParameter(q, filter);

		adicionarRestricoesDePaginacao(q, pageable);

		return new PageImpl<>(q.getResultList(), pageable, total(filter));
	}

	@Override
	public Page<ColetaDTO> resumir(ColetaFilter filter, Pageable pageable) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT new br.com.up.edestiny.api.repository.dto.ColetaDTO(c) FROM Coleta c ");
		sql.append(getWhere(filter));

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
	private String getWhere(ColetaFilter filter) {
		StringBuilder where = new StringBuilder();

		where.append(" WHERE c.coletor.id = :coletor ");

		if (filter.getDataInicio() != null) {
			where.append(" AND c.dtPrevistaColeta >= :dtInicio ");
		}

		if (filter.getDataFim() != null) {
			where.append(" AND c.dtPrevistaColeta <= :dtFim ");
		}

		if (filter.getSituacoes() != null && !filter.getSituacoes().isEmpty()) {
			where.append(" AND c.situacao IN (:situacoes) ");
		}

		return where.toString();
	}

	/**
	 * 
	 * @param q
	 * @param filter
	 */
	private void setParameter(Query q, ColetaFilter filter) {
		q.setParameter("coletor", Long.parseLong(filter.getColetorId()));

		if (filter.getDataInicio() != null) {
			q.setParameter("dtInicio", filter.getDataInicio());
		}

		if (filter.getDataFim() != null) {
			q.setParameter("dtFim", filter.getDataFim());
		}

		if (filter.getSituacoes() != null && !filter.getSituacoes().isEmpty()) {
			List<SituacaoColeta> situacoes = new ArrayList<>();
			for (String item : filter.getSituacoes()) {
				SituacaoColeta sc = SituacaoColeta.findByDescricao(item);
				if (sc != null) {
					situacoes.add(sc);
				}
			}

			q.setParameter("situacoes", situacoes);
		}
	}

	/**
	 * 
	 * @param filter
	 * @return
	 */
	private Long total(ColetaFilter filter) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT COUNT(c) FROM Coleta c ");
		sql.append(getWhere(filter));

		Query q = manager.createQuery(sql.toString());

		setParameter(q, filter);

		Number result = q.getFirstResult();

		return result.longValue();
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
}
