package datamahasiswa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControllerMahasiswa {
    ArrayList<ModelMahasiswa> dataArrayList;
    DefaultTableModel labelList;
    private Connection conn;

    public ControllerMahasiswa() {
        conn = Koneksi.getKoneksi();
        dataArrayList = new ArrayList<>();
        labelList = new DefaultTableModel(new String[]{"NPM", "Nama", "Tinggi", "Pindahan"}, 0);
    }

    public void insertData(String npm, String nama, int tinggi, boolean pindahan) {
        ModelMahasiswa mhs = new ModelMahasiswa(npm, nama, tinggi, pindahan);
        dataArrayList.add(mhs);
        insertDataToDatabase(mhs);
    }

    public void insertDataToDatabase(ModelMahasiswa mhs) {
        String sql = "INSERT INTO mahasiswa (npm, nama, tinggi, pindahan) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, mhs.getNPM()); // Ubah ke setInt
            pst.setString(2, mhs.getNama());
            pst.setInt(3, mhs.getTinggi());
            pst.setBoolean(4, mhs.isPindahan());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan ke database!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data ke database: " + e.getMessage());
        }
    }

    public DefaultTableModel showData() {
        labelList.setRowCount(0);
        for (ModelMahasiswa mhs : dataArrayList) {
            labelList.addRow(new Object[]{
                mhs.getNPM(), 
                mhs.getNama(), 
                mhs.getTinggi(), 
                mhs.isPindahan() ? "Ya" : "Tidak"
            });
        }
        return labelList;
    }
}
