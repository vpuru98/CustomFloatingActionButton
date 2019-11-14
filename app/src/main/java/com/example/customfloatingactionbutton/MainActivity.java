package com.example.customfloatingactionbutton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.purusharth.customfloatingactionbutton.CustomFloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomFloatingActionButton customFAB = findViewById(R.id.customFAB);
        customFAB.setParentImageResource(R.drawable.share);

        for(int i = 0;i < customFAB.getChildrenCount();i ++) {
            final int finalI = i;
            customFAB.addChildClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Child " + (finalI + 1),
                            Toast.LENGTH_SHORT).show();
                }
            }, i);
        }

    }
}
