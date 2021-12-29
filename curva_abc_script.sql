use asn;

select * from itensvenda where id_item = 81;

select iv.id_item, p.nome, sum((iv.valortotal + iv.acrescimo) - iv.desconto) as faturamento
from itensvenda as iv inner join produto as p on iv.id_item = p.codigo
where iv.datavenda between '2020-07-01' and '2021-08-01'
group by iv.id_item
union
select '-1', 'FATURAMENTO TOTAL', sum(faturamento) from (
	select iv.id_item, p.nome, sum((iv.valortotal + iv.acrescimo) - iv.desconto) as faturamento
	from itensvenda as iv inner join produto as p on iv.id_item = p.codigo
	where iv.datavenda between '2020-07-01' and '2021-08-01'
	group by iv.id_item
) as sub_query
 order by faturamento DESC;


-- Query para Relatório de Curva ABC:
select iv.id_item, p.nome, sum((iv.valortotal + iv.acrescimo) - iv.desconto) as faturamento,
(select sum(faturamento) from (
	select iv.id_item, p.nome, sum((iv.valortotal + iv.acrescimo) - iv.desconto) as faturamento
	from itensvenda as iv inner join produto as p on iv.id_item = p.codigo
	where iv.datavenda between '2021-07-01' and '2021-08-01'
	group by iv.id_item
) as sub_query_a) as faturamento_geral
from itensvenda as iv inner join produto as p on iv.id_item = p.codigo
where iv.datavenda between '2021-07-01' and '2021-08-01'
group by iv.id_item
order by faturamento DESC;

-- CRIAR LÓGICA PARA NÃO PERMITIR INSERÇÃO DE VALORES PERCENTUAIS, AO SEREM SOMADOS, NÃO RESULTEM NO VALOR 100.

select percentual_curva_a, percentual_curva_b, percentual_curva_c from configuracoes;