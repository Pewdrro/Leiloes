import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/** Tela de consulta dos produtos com status "Vendido". */
public class vendasVIEW extends JFrame {

    private final JTable tabelaVendas = new JTable();
    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"ID", "Nome", "Valor", "Status"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public vendasVIEW() {
        setTitle("Produtos vendidos");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(12, 12));

        tabelaVendas.setModel(modelo);
        add(new JScrollPane(tabelaVendas), BorderLayout.CENTER);

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton atualizar = new JButton("Atualizar");
        JButton voltar = new JButton("Voltar");
        rodape.add(new JLabel("Itens vendidos"));
        rodape.add(atualizar);
        rodape.add(voltar);
        add(rodape, BorderLayout.SOUTH);

        atualizar.addActionListener(evento -> listarProdutosVendidos());
        voltar.addActionListener(evento -> dispose());

        setSize(620, 360);
        setLocationRelativeTo(null);
        listarProdutosVendidos();
    }

    private void listarProdutosVendidos() {
        try {
            modelo.setRowCount(0);
            ArrayList<ProdutosDTO> vendidos = new ProdutosDAO().listarProdutosVendidos();
            for (ProdutosDTO produto : vendidos) {
                modelo.addRow(new Object[]{
                    produto.getId(),
                    produto.getNome(),
                    produto.getValor(),
                    produto.getStatus()
                });
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this,
                    "Não foi possível carregar as vendas: " + erro.getMessage(),
                    "Erro ao consultar vendas",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
