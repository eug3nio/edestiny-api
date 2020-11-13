package br.com.up.edestiny.api.repository.empresa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.up.edestiny.api.model.Empresa;
import br.com.up.edestiny.api.repository.dto.EmpresaDTO;
import br.com.up.edestiny.api.repository.filter.EmpresaFilter;

@SuppressWarnings("unchecked")
public class EmpresaRepositoryImpl implements EmpresaRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Empresa> filtrar(EmpresaFilter filter, Pageable pageable) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT e FROM Empresa e ");
		sql.append(" WHERE 1=1 ").append(getWhere(filter));

		Query q = manager.createQuery(sql.toString());

		setParameter(q, filter);

		adicionarRestricoesDePaginacao(q, pageable);

		return new PageImpl<>(q.getResultList(), pageable, total(filter));
	}

	@Override
	public Page<EmpresaDTO> resumir(EmpresaFilter filter, Pageable pageable) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT new br.com.up.edestiny.api.repository.dto.EmpresaDTO(e) FROM Empresa e ");
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
	private String getWhere(EmpresaFilter filter) {
		StringBuilder where = new StringBuilder();

		if (!StringUtils.isEmpty(filter.getCnpj())) {
			where.append(" AND LOWER(e.cnpj) LIKE :cnpj ");
		}

		if (!StringUtils.isEmpty(filter.getEmail())) {
			where.append(" AND LOWER(e.email) LIKE :email ");
		}

		if (!StringUtils.isEmpty(filter.getNomeFantasia())) {
			where.append(" AND LOWER(e.nomeFantasia) LIKE :nomeFantasia ");
		}

		if (!StringUtils.isEmpty(filter.getRazaoSocial())) {
			where.append(" AND LOWER(e.razaoSocial) LIKE :razaoSocial ");
		}

		return where.toString();
	}

	/**
	 * 
	 * @param q
	 * @param filter
	 */
	private void setParameter(Query q, EmpresaFilter filter) {
		if (!StringUtils.isEmpty(filter.getCnpj())) {
			q.setParameter("cnpj", "%" + filter.getCnpj().toLowerCase() + "%");
		}

		if (!StringUtils.isEmpty(filter.getEmail())) {
			q.setParameter("email", "%" + filter.getEmail().toLowerCase() + "%");
		}

		if (!StringUtils.isEmpty(filter.getNomeFantasia())) {
			q.setParameter("nomeFantasia", "%" + filter.getNomeFantasia().toLowerCase() + "%");
		}

		if (!StringUtils.isEmpty(filter.getRazaoSocial())) {
			q.setParameter("razaoSocial", "%" + filter.getRazaoSocial().toLowerCase() + "%");
		}

	}

	/**
	 * 
	 * @param filter
	 * @return
	 */
	private Long total(EmpresaFilter filter) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT COUNT(e) FROM Empresa e ");
		sql.append(" WHERE 1=1 ").append(getWhere(filter));

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

	@Override
	public Empresa findByUsuarioId(Long id) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT * FROM empresa e ");
		sql.append(" INNER JOIN empresa_usuario eu ON e.id = eu.empresa_id ");
		sql.append(" WHERE eu.usuario_id = :id ");

		Query q = manager.createNativeQuery(sql.toString(), Empresa.class);

		q.setParameter("id", id);

		return (Empresa) q.getSingleResult();
	}

}
