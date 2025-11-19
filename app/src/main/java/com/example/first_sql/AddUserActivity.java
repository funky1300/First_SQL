package com.example.first_sql;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddUserActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword, editTextEmail;
    private Button buttonSave;
    private DatabaseHelper myDb; // אובייקט של ה-Helper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        // יצירת מופע של ה-Helper
        myDb = new DatabaseHelper(this);

        // קישור לממשק המשתמש
        editTextUsername = findViewById(R.id.editText_username);
        editTextPassword = findViewById(R.id.editText_password);
        editTextEmail = findViewById(R.id.editText_email);
        buttonSave = findViewById(R.id.button_save);

        // הוספת מאזין ללחיצה על כפתור השמירה
        buttonSave.setOnClickListener(v -> handleSaveClick());
    }

    private void handleSaveClick() {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        String email = editTextEmail.getText().toString();

        // בדיקה שהשדות אינם ריקים
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "אנא מלא את כל השדות", Toast.LENGTH_SHORT).show();
            return;
        }

        // שימוש בפעולה addUser שהגדרנו ב-Helper
        boolean isInserted = myDb.addUser(username, password, email);

        if (isInserted) {
            Toast.makeText(this, "המשתמש נוסף בהצלחה!", Toast.LENGTH_LONG).show();
            finish(); // סגירת המסך הנוכחי וחזרה למסך הראשי
        } else {
            Toast.makeText(this, "שגיאה: המשתמש לא נוסף", Toast.LENGTH_LONG).show();
        }
    }
}
