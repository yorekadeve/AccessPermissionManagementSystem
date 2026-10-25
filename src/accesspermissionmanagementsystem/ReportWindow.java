/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accesspermissionmanagementsystem;

/**
 *
 * @author yorekadev
 */

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class ReportWindow extends JFrame {

    private final String reportTitle;
    private final String reportDescription;
    private final String sql;

    private final JLabel totalValue = new JLabel("0");
    private final JLabel generatedValue = new JLabel("-");
    private final JLabel reportTypeValue = new JLabel("Database Report");
    private final JLabel recordCountLabel = new JLabel("Records: 0");

    private final JTextField searchField = new JTextField(22);
    private final JTable reportTable = new JTable();
    private final DefaultTableModel tableModel =
            new DefaultTableModel();
    private TableRowSorter<DefaultTableModel> sorter;

    public ReportWindow(String reportTitle,
                        String reportDescription,
                        String sql) {

        this.reportTitle = reportTitle;
        this.reportDescription = reportDescription;
        this.sql = sql;

        setTitle(reportTitle);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(950, 600));
        setSize(1150, 720);
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout(0, 16));
        main.setBackground(new Color(245, 248, 252));
        main.setBorder(BorderFactory.createEmptyBorder(20, 22, 20, 22));

        JPanel heading = new JPanel(new BorderLayout(5, 6));
        heading.setOpaque(false);

        JLabel systemName = new JLabel(
                "ACCESS & PERMISSION MANAGEMENT SYSTEM");
        systemName.setFont(new Font("SansSerif", Font.BOLD, 12));
        systemName.setForeground(new Color(44, 91, 145));

        JLabel title = new JLabel(reportTitle);
        title.setFont(new Font("SansSerif", Font.BOLD, 25));
        title.setForeground(new Color(20, 43, 75));

        JLabel description = new JLabel(reportDescription);
        description.setFont(new Font("SansSerif", Font.PLAIN, 13));
        description.setForeground(new Color(85, 101, 122));

        JPanel titleGroup = new JPanel(new GridLayout(3, 1, 0, 5));
        titleGroup.setOpaque(false);
        titleGroup.add(systemName);
        titleGroup.add(title);
        titleGroup.add(description);

        heading.add(titleGroup, BorderLayout.CENTER);
        main.add(heading, BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(0, 14));
        body.setOpaque(false);

        JPanel summary = new JPanel(new GridLayout(1, 3, 14, 0));
        summary.setOpaque(false);
        summary.add(createSummaryCard(
                "TOTAL RECORDS", totalValue, new Color(30, 105, 190)));
        summary.add(createSummaryCard(
                "REPORT TYPE", reportTypeValue, new Color(112, 75, 190)));
        summary.add(createSummaryCard(
                "LAST REFRESHED", generatedValue, new Color(26, 143, 112)));

        JPanel tools = new JPanel(new BorderLayout(10, 0));
        tools.setOpaque(false);

        JPanel searchPanel = new JPanel(new BorderLayout(8, 0));
        searchPanel.setOpaque(false);
        JLabel searchLabel = new JLabel("Search report:");
        searchLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        JButton refreshButton = createButton("Refresh");
        JButton exportButton = createButton("Export PDF");
        JButton closeButton = createButton("Close");

        JPanel actions = new JPanel(new java.awt.FlowLayout(
                java.awt.FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
        actions.add(refreshButton);
        actions.add(exportButton);
        actions.add(closeButton);

        tools.add(searchPanel, BorderLayout.CENTER);
        tools.add(actions, BorderLayout.EAST);

        reportTable.setModel(tableModel);
        reportTable.setRowHeight(27);
        reportTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        reportTable.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 12));
        reportTable.getTableHeader().setBackground(
                new Color(225, 235, 247));
        reportTable.getTableHeader().setForeground(
                new Color(25, 48, 78));
        reportTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        reportTable.setFillsViewportHeight(true);
        reportTable.setSelectionBackground(new Color(216, 233, 252));

        JScrollPane scrollPane = new JScrollPane(reportTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(
                new Color(218, 226, 237)));

        JPanel tablePanel = new JPanel(new BorderLayout(0, 8));
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 226, 237)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        recordCountLabel.setFont(
                new Font("SansSerif", Font.BOLD, 12));
        recordCountLabel.setForeground(new Color(67, 82, 103));
        tablePanel.add(recordCountLabel, BorderLayout.SOUTH);

        JPanel upper = new JPanel(new BorderLayout(0, 14));
        upper.setOpaque(false);
        upper.add(summary, BorderLayout.NORTH);
        upper.add(tools, BorderLayout.SOUTH);

        body.add(upper, BorderLayout.NORTH);
        body.add(tablePanel, BorderLayout.CENTER);

        main.add(body, BorderLayout.CENTER);
        setContentPane(main);

        refreshButton.addActionListener(e -> loadData());
        closeButton.addActionListener(e -> dispose());
        exportButton.addActionListener(e -> exportPdf());

        searchField.addActionListener(e -> applySearch());

        searchField.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(
                    javax.swing.event.DocumentEvent e) {
                applySearch();
            }

            @Override
            public void removeUpdate(
                    javax.swing.event.DocumentEvent e) {
                applySearch();
            }

            @Override
            public void changedUpdate(
                    javax.swing.event.DocumentEvent e) {
                applySearch();
            }
        });

        loadData();
    }

    private JPanel createSummaryCard(
            String heading, JLabel value, Color accent) {

        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(3, 0, 0, 0, accent),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(222, 229, 238)),
                        BorderFactory.createEmptyBorder(13, 15, 13, 15))));

        JLabel label = new JLabel(heading);
        label.setFont(new Font("SansSerif", Font.BOLD, 11));
        label.setForeground(new Color(90, 104, 123));

        value.setFont(new Font("SansSerif", Font.BOLD, 20));
        value.setForeground(new Color(24, 47, 80));

        card.add(label, BorderLayout.NORTH);
        card.add(value, BorderLayout.CENTER);

        return card;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBackground(new Color(30, 103, 190));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(
                10, 15, 10, 15));
        return button;
    }

    private void loadData() {
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            ResultSetMetaData metadata = result.getMetaData();
            int columns = metadata.getColumnCount();

            for (int i = 1; i <= columns; i++) {
                tableModel.addColumn(metadata.getColumnLabel(i));
            }

            while (result.next()) {
                Object[] row = new Object[columns];

                for (int i = 1; i <= columns; i++) {
                    row[i - 1] = result.getObject(i);
                }

                tableModel.addRow(row);
            }

            sorter = new TableRowSorter<>(tableModel);
            reportTable.setRowSorter(sorter);

            totalValue.setText(
                    String.valueOf(tableModel.getRowCount()));
            generatedValue.setText(
                    new SimpleDateFormat("HH:mm:ss").format(new Date()));
            recordCountLabel.setText(
                    "Records: " + tableModel.getRowCount());

            applySearch();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load " + reportTitle + ":\n"
                            + ex.getMessage(),
                    "Report Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void applySearch() {
        if (sorter == null) {
            return;
        }

        String text = searchField.getText().trim();

        if (text.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(
                    RowFilter.regexFilter(
                            "(?i)" + java.util.regex.Pattern.quote(text)));
        }

        recordCountLabel.setText(
                "Showing " + reportTable.getRowCount()
                + " of " + tableModel.getRowCount() + " records");
    }

    private void exportPdf() {
        if (reportTable.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "There are no visible records to export.",
                    "Export PDF",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        javax.swing.JFileChooser chooser =
                new javax.swing.JFileChooser();

        String fileName = reportTitle.replaceAll(
                "[^a-zA-Z0-9]", "_") + ".pdf";

        chooser.setSelectedFile(new java.io.File(fileName));

        if (chooser.showSaveDialog(this)
                != javax.swing.JFileChooser.APPROVE_OPTION) {
            return;
        }

        Document document = new Document(
                com.itextpdf.text.PageSize.A4.rotate());

        try {
            PdfWriter.getInstance(
                    document,
                    new FileOutputStream(chooser.getSelectedFile()));

            document.open();

            document.add(new Paragraph(
                    "ACCESS & PERMISSION MANAGEMENT SYSTEM"));
            document.add(new Paragraph(reportTitle));
            document.add(new Paragraph(reportDescription));
            document.add(new Paragraph("Generated: "
                    + new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss").format(new Date())));
            document.add(new Paragraph(" "));
            
            PdfPTable pdfTable =
                    new PdfPTable(tableModel.getColumnCount());
            pdfTable.setWidthPercentage(100);

            for (int c = 0; c < tableModel.getColumnCount(); c++) {
                pdfTable.addCell(tableModel.getColumnName(c));
            }

            for (int r = 0; r < reportTable.getRowCount(); r++) {
                int modelRow = reportTable.convertRowIndexToModel(r);

                for (int c = 0; c < tableModel.getColumnCount(); c++) {
                    Object value = tableModel.getValueAt(modelRow, c);

                    pdfTable.addCell(
                            value == null ? "" : value.toString());
                }
            }

            document.add(pdfTable);
            document.close();

            JOptionPane.showMessageDialog(
                    this,
                    "PDF exported successfully.",
                    "Export Complete",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            if (document.isOpen()) {
                document.close();
            }

            JOptionPane.showMessageDialog(
                    this,
                    "PDF export failed:\n" + ex.getMessage(),
                    "Export Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

