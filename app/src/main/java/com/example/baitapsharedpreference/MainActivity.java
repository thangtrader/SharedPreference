package com.example.baitapsharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText edtTen;
    EditText edtNgaySinh;
    EditText edtMasv;
    Button btnSave;
    TextView tvHienThi;
    Button btnDelete;
    SharedPreferences shared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtTen = findViewById(R.id.edtTen);
        edtNgaySinh = findViewById(R.id.edtNgaySinh);
        edtMasv = findViewById(R.id.edtMasv);
        btnSave = findViewById(R.id.btnSave);
        tvHienThi = findViewById(R.id.tvHienThi);
        btnDelete = findViewById(R.id.btnDelete);
        shared = getSharedPreferences("Shared", Context.MODE_PRIVATE);

        // Lấy giá trị lưu trữ
        String saveMasv = shared.getString("Mã sinh viên", "");
        String saveTen = shared.getString("Họ và tên", "");
        String saveNgaySinh = shared.getString("Ngày sinh", "");

        // Hiển thị dữ liệu nếu có
        if (!saveTen.isEmpty() || !saveNgaySinh.isEmpty() || !saveMasv.isEmpty()) {
            tvHienThi.setText("Bạn là: "+ saveMasv + " " + saveTen + " " + saveNgaySinh);
        }

        // Hiển thị lại dữ liệu trên các EditText
        edtMasv.setText(saveMasv);
        edtTen.setText(saveTen);
        edtNgaySinh.setText(saveNgaySinh);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String masv = edtMasv.getText().toString();
                String ten = edtTen.getText().toString();
                String ngaySinh = edtNgaySinh.getText().toString();

                // Lưu dữ liệu vào SharedPreferences
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("Mã sinh viên", masv);
                editor.putString("Họ và tên", ten);
                editor.putString("Ngày sinh", ngaySinh);
                editor.apply();

                // Cập nhật TextView sau khi lưu
                tvHienThi.setText("Bạn là: "+ masv + " " + ten + " " + ngaySinh);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xóa dữ liệu khỏi SharedPreferences
                SharedPreferences.Editor editor = shared.edit();
                editor.clear();
                editor.apply();

                // Xóa dữ liệu trên giao diện
                edtMasv.setText("");
                edtTen.setText("");
                edtNgaySinh.setText("");
                tvHienThi.setText("");

                Toast.makeText(MainActivity.this, "Dữ liệu đã được xóa", Toast.LENGTH_SHORT).show();
            }
        });

    }
}