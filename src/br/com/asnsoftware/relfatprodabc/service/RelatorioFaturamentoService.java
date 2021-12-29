package br.com.asnsoftware.relfatprodabc.service;

import br.com.asnsoftware.relfatprodabc.exceptions.PersistenciaException;
import br.com.asnsoftware.relfatprodabc.dao.RelatorioFaturamentoDAO;
import br.com.asnsoftware.relfatprodabc.pojo.RelatorioFaturamento;

public abstract class RelatorioFaturamentoService {
    
    public static RelatorioFaturamento gerar(String dataInicial, String dataFinal) {
        
        RelatorioFaturamento relatorioFaturamento = null;
        
        RelatorioFaturamentoDAO rfDAO = new RelatorioFaturamentoDAO();
        
        try {
            relatorioFaturamento = rfDAO.buscar(dataInicial, dataFinal);
        } catch (PersistenciaException e) {
            System.out.println(e.getMessage());
        }
        
        return relatorioFaturamento;
    }
    
}
