package br.com.asnsoftware.relfatprodabc.pojo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class ItemFaturamento {
    
    private int id;;
    private String nome;
    private BigDecimal valorFaturamento;
    private BigDecimal percentual;
    private char curva;
    
    /**
     * Instacia um objeto do tipo ItemFaturamento. <br/> <br/>
     * 
     * Obs.: este construtor calcula e insere o percentual do produto baseado no faturamento deste e
     * no faturamento geral.
     * 
     * @param id Código do item (produto).
     * @param nome Nome do item (produto).
     * @param valorFaturamento Valor (R$) do faturamento do item (produto).
     * @param valorGeralFaturamento Valor (R$) do faturamento geral.
     */
    public ItemFaturamento(int id, String nome, BigDecimal valorFaturamento, BigDecimal valorGeralFaturamento) {
        this.id = id;
        this.nome = nome;
        this.valorFaturamento = valorFaturamento;
        
        this.percentual = (valorFaturamento.divide(valorGeralFaturamento, 4, RoundingMode.HALF_UP))
                            .multiply(new BigDecimal("100.00"))
                                .setScale(2, RoundingMode.HALF_UP);
    }
    
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValorFaturamento() {
        return valorFaturamento;
    }

    public BigDecimal getPercentual() {
        return percentual;
    }

    public char getCurva() {
        return curva;
    }

    public void setCurva(char curva) {
        this.curva = curva;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + this.id;
        hash = 61 * hash + Objects.hashCode(this.nome);
        hash = 61 * hash + Objects.hashCode(this.valorFaturamento);
        hash = 61 * hash + Objects.hashCode(this.percentual);
        hash = 61 * hash + this.curva;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ItemFaturamento other = (ItemFaturamento) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.curva != other.curva) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.valorFaturamento, other.valorFaturamento)) {
            return false;
        }
        if (!Objects.equals(this.percentual, other.percentual)) {
            return false;
        }
        return true;
    }
}
