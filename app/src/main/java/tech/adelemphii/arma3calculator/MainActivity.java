package tech.adelemphii.arma3calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "tech.adelemphii.arma3calculator.MESSAGE";
    public static final String DISPLAY_MESSAGE = "tech.adelemphii.arma3calculator.DISPLAY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Called when button pressed
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = findViewById(R.id.editTextTextPersonName2);
        String message = editText.getText().toString();

        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void openArtilleryComputer(View view) {
        Intent intent = new Intent(this, ArtilleryComputerActivity.class);
        startActivity(intent);
    }
}