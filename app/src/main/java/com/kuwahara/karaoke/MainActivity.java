package com.kuwahara.karaoke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private TestOpenHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DB作成
        helper = new TestOpenHelper(getApplicationContext());

        Button readButton = findViewById(R.id.button);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData();
            }
        });
        textView = findViewById(R.id.text_view);
    }

    private void readData(){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(
                "testdb",
                new String[] { "items", "price" },
                null,
                null,
                null,
                null,
                null
        );

        // FIXME:↑ ここまででデータを読み取ってcursorを返すメソッドに切り出せない？.

        cursor.moveToFirst();

        // FIXME: mapでmap<db型(今回はitems,priceを持つ>を定義して管理できない?.
        // ↑Listの方がいいのか？そのあたりは要検討.
        // イメージ
//        Map<String, itemInfo> map = new HashMap<>();

        StringBuilder sbuilder = new StringBuilder();

        for (int i = 0; i < cursor.getCount(); i++) {
            sbuilder.append(cursor.getString(0));
            sbuilder.append(":    ");
            sbuilder.append(cursor.getInt(1));
            sbuilder.append("yen\n\n");
            cursor.moveToNext();
        }

        cursor.close();

        textView.setText(sbuilder.toString());
    }
}