package com.example.mysupermarket;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mysupermarket.databinding.ActivityHomeBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private Adapter adapter;
    private Item selectedItem;
    private ArrayList<Item> items;
    private CardView selectedCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        items=  new ArrayList<>();
        loadItems();

        binding.srlRefresh.setOnRefreshListener(() -> {
            loadItems();
            binding.srlRefresh.setRefreshing(false);
        });

        binding.srlRefresh.setColorSchemeColors(Color.GREEN);

        adapter = new Adapter(new ClickableInterface() {
            @Override
            public void onClick(CardView cv, Item i) {
                cv.setOnClickListener(v -> {
                    if(selectedItem != null){
                        selectedCV.setCardBackgroundColor(Color.WHITE);
                    }
                    cv.setCardBackgroundColor(Color.LTGRAY);
                    selectedItem = i;
                    selectedCV = cv;
                });
            }
        },this);
        binding.rvItem.setLayoutManager(new GridLayoutManager(this,3));
        binding.rvItem.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        binding.fabDone.setOnClickListener(v -> {
            if(selectedCV != null){

                builder.setTitle("You want to buy "+selectedItem.getType()+" ?");
                builder.setPositiveButton("Yes",(dialog, which) -> {
                    Intent intent = new Intent(HomeActivity.this, FinalActivity.class );
                    intent.putExtra("item",selectedItem);
                    startActivity(intent);
                    finish();
                });
                builder.setNegativeButton("No",(dialog, which) -> {
                    Toast.makeText(getApplicationContext(),"Don't worry! Take your time to choose :)",Toast.LENGTH_LONG).show();
                });
                AlertDialog ad = builder.create();
                ad.show();
            }
        });
    }

    private void loadItems() {
        items.clear();
        String[] types = {"biryani","burger","butter-chicken","dessert","dosa","idly","pasta","pizza","rice","samosa"};
        for (int i = 0; i < 10 ; i++) {
            int idx = (int)(Math.random()*10);
            int price = ((int)(Math.random()*20)+10)*1000;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    "https://foodish-api.herokuapp.com/api/images/" + types[idx],
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String url = null;
                            try {
                                url = response.getString("image");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            items.add(new Item(types[idx], url,price));
                            adapter.updateItems(items);
                            System.out.println(url);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),"Error occured while fetching images",Toast.LENGTH_SHORT).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);

        }
    }
}