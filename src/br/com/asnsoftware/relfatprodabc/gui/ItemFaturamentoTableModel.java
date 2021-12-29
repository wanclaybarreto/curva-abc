package br.com.asnsoftware.relfatprodabc.gui;

import br.com.asnsoftware.relfatprodabc.pojo.ItemFaturamento;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ItemFaturamentoTableModel extends AbstractTableModel {
    
    private List<ItemFaturamento> items = new ArrayList<>();
    private String[] columns = new String[]
        {"Código", "Nome", "Faturamento (valor)", "Faturamento (percentual)", "Curva"};
    
    private DecimalFormat df;
    private DecimalFormatSymbols dfs;
    
    public ItemFaturamentoTableModel() {
        super();
        df = new DecimalFormat("#,###,##0.00");
        
        dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator(',');
        dfs.setGroupingSeparator('.');
        
        df.setDecimalFormatSymbols(dfs);
    }
    
    @Override
    public int getRowCount() {
        return items.size();
    }
    
    @Override
    public int getColumnCount() {
        return columns.length;
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex > -1 && columnIndex < columns.length) {
            return columns[columnIndex];
        } else {
            return "-";
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemFaturamento itemFaturamento = items.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return itemFaturamento.getId();
            case 1:
                return itemFaturamento.getNome();
            case 2:
                return "R$ " + df.format(itemFaturamento.getValorFaturamento());
            case 3:
                return df.format(itemFaturamento.getPercentual()) + "%";
            case 4:
                return itemFaturamento.getCurva();
            default:
                return "Índice inválido";
        }
    }
    
    public void addItem(ItemFaturamento itemFaturamento) {
        items.add(itemFaturamento);
        fireTableRowsInserted(getRowCount() -1, getRowCount() -1);
    }
    
    public ItemFaturamento getItem(int rowIndex) {
        return items.get(rowIndex);
    }
    
    public void clear() {
        items.clear();
        fireTableDataChanged();
    }
    
}
