package br.com.up.edestiny.api.repository.solicitacao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.up.edestiny.api.model.Solicitacao;
import br.com.up.edestiny.api.model.enums.SituacaoSolicitacao;
import br.com.up.edestiny.api.repository.dto.SolicitacaoDTO;
import br.com.up.edestiny.api.repository.filter.SolicitacaoFilter;

@SuppressWarnings("unchecked")
public class SolicitacaoRepositoryImpl implements SolicitacaoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Solicitacao> filtrar(SolicitacaoFilter filter, Pageable pageable) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT s FROM Solicitacao s ");
		sql.append(getWhere(filter));

		Query q = manager.createQuery(sql.toString());

		setParameter(q, filter);

		adicionarRestricoesDePaginacao(q, pageable);

		return new PageImpl<>(q.getResultList(), pageable, total(filter));
	}

	@Override
	public Page<SolicitacaoDTO> resumir(SolicitacaoFilter filter, Pageable pageable) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT new br.com.up.edestiny.api.repository.dto.SolicitacaoDTO(s) FROM Solicitacao s ");
		sql.append(getWhere(filter));

		Query q = manager.createQuery(sql.toString());

		setParameter(q, filter);

		adicionarRestricoesDePaginacao(q, pageable);

		return new PageImpl<>(q.getResultList(), pageable, total(filter));
	}

	/**
	 * 
	 * @return
	 */
	private String getWhere(SolicitacaoFilter filter) {
		StringBuilder where = new StringBuilder();

		if (filter.getIdColeta() != null) {
			where.append(" WHERE (s.coleta.id = :idColeta ");
			where.append(" OR s.situacao = :situacao ) ");
		} else {
			where.append(" WHERE s.situacao = :situacao ");
		}

		return where.toString();
	}

	/**
	 * 
	 * @param q
	 */
	private void setParameter(Query q, SolicitacaoFilter filter) {
		if (filter.getIdColeta() != null) {
			q.setParameter("idColeta", Long.parseLong(filter.getIdColeta()));
		}

		q.setParameter("situacao", SituacaoSolicitacao.ABERTA);

	}

	/**
	 * 
	 * @param filter
	 * @return
	 */
	private Long total(SolicitacaoFilter filter) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT COUNT(s) FROM Solicitacao s ");
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
