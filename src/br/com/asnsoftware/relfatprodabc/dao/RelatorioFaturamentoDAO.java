package br.com.asnsoftware.relfatprodabc.dao;

import br.com.asnsoftware.relfatprodabc.exceptions.PersistenciaException;
import br.com.asnsoftware.relfatprodabc.connection.ConnectionJDBC;
import br.com.asnsoftware.relfatprodabc.pojo.ItemFaturamento;
import br.com.asnsoftware.relfatprodabc.pojo.RelatorioFaturamento;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class RelatorioFaturamentoDAO {
    
    public RelatorioFaturamento buscar(String dataIn, String dataFin) throws PersistenciaException {
        
        RelatorioFaturamento relatorioFaturamento = new RelatorioFaturamento();
        
        try (Connection conex = ConnectionJDBC.getInstance().getConnection()) {
                
                PreparedStatement ps;
                ResultSet rs;
                
                //Buscando, na tabela configuracoes, os valores percentuais padões que foram definidos para cada curva:
                String sqlBuscaPadroesPercentuaisCurvas = "select percentual_curva_a, percentual_curva_b, percentual_curva_c from configuracoes";
                ps = conex.prepareStatement(sqlBuscaPadroesPercentuaisCurvas);
                rs = ps.executeQuery();

                while (rs.next()) {
                    //inserindo os valores percentuais padrões estabelecidos para cada curva:
                    relatorioFaturamento.inserirPercentuaisPadrao(new BigDecimal(rs.getString("percentual_curva_a")).setScale(2, RoundingMode.HALF_UP),
                                                                  new BigDecimal(rs.getString("percentual_curva_b")).setScale(2, RoundingMode.HALF_UP),
                                                                  new BigDecimal(rs.getString("percentual_curva_c")).setScale(2, RoundingMode.HALF_UP));
                }
                
                //Buscando os dados do relatório:
                String sql = "select iv.id_item, p.nome, sum((iv.valortotal + iv.acrescimo) - iv.desconto) as faturamento,\n" +
                             "(select sum(faturamento) from (\n" +
                             "	select iv.id_item, p.nome, sum((iv.valortotal + iv.acrescimo) - iv.desconto) as faturamento\n" +
                             "	from itensvenda as iv inner join produto as p on iv.id_item = p.codigo\n" +
                             "	where iv.datavenda between ? and ?\n" +
                             "	group by iv.id_item\n" +
                             ") as sub_query_a) as faturamento_geral\n" +
                             "from itensvenda as iv inner join produto as p on iv.id_item = p.codigo\n" +
                             "where iv.datavenda between ? and ?\n" +
                             "group by iv.id_item\n" +
                             "order by faturamento DESC";

                ps = conex.prepareStatement(sql);
                ps.setString(1, dataIn);
                ps.setString(2, dataFin);
                ps.setString(3, dataIn);
                ps.setString(4, dataFin);
                rs = ps.executeQuery();
                
                //Montando o relatório através da lógica implementada na classe RelatorioFaturamento:
                while(rs.next()) {
                    BigDecimal faturamentoGeral = new BigDecimal(rs.getString("faturamento_geral"));
                    
                    if (relatorioFaturamento.getValorGeralFaturamento() == null) relatorioFaturamento.setValorGeralFaturamento(faturamentoGeral);
                    
                    ItemFaturamento itemFaturamento = new ItemFaturamento(rs.getInt("id_item"),
                                                                          rs.getString("nome"),
                                                                          new BigDecimal(rs.getString("faturamento")),
                                                                          faturamentoGeral);
                    
                    relatorioFaturamento.inserirItem(itemFaturamento);
                }

        } catch (Exception e) {

                e.printStackTrace();
                throw new PersistenciaException(e.getMessage(), e);

        }

        return relatorioFaturamento;
    }
    
}
