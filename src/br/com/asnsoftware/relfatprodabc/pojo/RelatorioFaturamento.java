package br.com.asnsoftware.relfatprodabc.pojo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe responsável por organizar todos os dados do relatório. <br/> <br/>
 * 
 * Detalhe sobre os atributos: <br/>
 * - listaItens: armazena os itens do relatório; <br/>
 * - percentualAcumulado: controla o percentual acumulado geral; <br/>
 * - percentualSeparador: guarda o percentual final da curva anterior à curva atual; <br/>
 * - percentualFinalA: recebe o percentual final da curva A; <br/>
 * - percentualFinalB: recebe o percentual final da curva B; <br/>
 * - percentualFinalC: recebe o percentual final da curva C; <br/>
 * - valorGeralFaturamento: guarda o valor do faturamento geral; <br/>
 * - curvaAtual: controla a curva que está sendo trabalhada; <br/>
 * - padraoPercentualCurvas: armazena os valores percentuais padrões estabelecidos para cada curva.
 */
public class RelatorioFaturamento {
    
    private ArrayList<ItemFaturamento> listaItens = new ArrayList<>();
    private BigDecimal percentualAcumulado = new BigDecimal("0.00");
    private BigDecimal percentualSeparador = new BigDecimal("0.00");
    private BigDecimal percentualFinalA;
    private BigDecimal percentualFinalB;
    private BigDecimal percentualFinalC = new BigDecimal("100.00");
    private BigDecimal valorGeralFaturamento;
    private char curvaAtual = 'A';
    private HashMap<Character, BigDecimal> padraoPercentualCurvas = new HashMap<>();
    
    /**
     * Insere os valores percentuais padroes estabelecidos para cada curva.
     * 
     * @param a
     * @param b
     * @param c 
     */
    public void inserirPercentuaisPadrao(BigDecimal a, BigDecimal b, BigDecimal c) {
        padraoPercentualCurvas.put('A', a.setScale(2, RoundingMode.HALF_UP));
        padraoPercentualCurvas.put('B', b.setScale(2, RoundingMode.HALF_UP));
        padraoPercentualCurvas.put('C', c.setScale(2, RoundingMode.HALF_UP));
    }
    
    /**
     * Insere os itens de acordo com as regras da Curva ABC. <br/> <br/>
     * 
     * Obs.: este método implementa a lógica que define em que curva cada item deverá ficar.
     * 
     * @param item 
     */
    public void inserirItem(ItemFaturamento item) {
        
        if (padraoPercentualCurvas.size() < 1) {
            padraoPercentualCurvas.put('A', new BigDecimal("80.00"));
            padraoPercentualCurvas.put('B', new BigDecimal("15.00"));
            padraoPercentualCurvas.put('C', new BigDecimal("5.00"));
        }
        
        if (listaItens.size() == 0 || (percentualAcumulado.add(item.getPercentual())).compareTo(padraoPercentualCurvas.get(curvaAtual).add(percentualSeparador)) < 1) {
            
            item.setCurva(curvaAtual);
            listaItens.add(item);
            percentualAcumulado = percentualAcumulado.add(item.getPercentual()).setScale(2, RoundingMode.HALF_UP);
            
        } else {
            if (curvaAtual < 66) {
                curvaAtual += 1;
                percentualSeparador = percentualAcumulado;
                percentualFinalA = percentualAcumulado;
            } else if (curvaAtual < 67) {
                curvaAtual += 1;
                percentualSeparador = percentualAcumulado;
                percentualFinalB = percentualAcumulado;
            }
            
            item.setCurva(curvaAtual);
            listaItens.add(item);
            percentualAcumulado = percentualAcumulado.add(item.getPercentual()).setScale(2, RoundingMode.HALF_UP);
        }
    }
    
    public ArrayList<ItemFaturamento> getListaItens() {
        return listaItens;
    }

    public HashMap<Character, BigDecimal> getPadraoPercentualCurvas() {
        return padraoPercentualCurvas;
    }
    
    public BigDecimal getPercentualFinalA() {
        return percentualFinalA;
    }

    public BigDecimal getPercentualFinalB() {
        return percentualFinalB;
    }

    public BigDecimal getPercentualFinalC() {
        return percentualFinalC;
    }
    
    public BigDecimal getValorGeralFaturamento() {
        return valorGeralFaturamento;
    }
    
    public void setValorGeralFaturamento(BigDecimal valorGeralFaturamento) {
        this.valorGeralFaturamento = valorGeralFaturamento;
    }
    
}
