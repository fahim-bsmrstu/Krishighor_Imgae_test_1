package com.example.klayton.krishighor_imgae_test_1;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.klayton.krishighor_image_test_1.add_Product.Add_Product;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button b;
    TextView t;

    String name,user_name,user_pass;


    ListView lvProduct;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create default options which will be used for every
        //  displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config); // Do it on Application start

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        lvProduct = (ListView)findViewById(R.id.lvProduct);

/*
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JSONTask().execute("http://192.168.159.1/dt/json_data.php");
            }
        });
*/
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
     //   client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }




    public class JSONTask extends AsyncTask<String, String, List<ProductModel>> {


        @Override
        protected List<ProductModel> doInBackground(String... params) {

            BufferedReader reader = null;
            HttpURLConnection httpURLConnection = null;
            try {
                URL url = new URL(params[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer buffer = new StringBuffer();

                String line = "";

                while ((line = reader.readLine()) != null) {

                    buffer.append(line);
                }

                String finalJSON =  buffer.toString();

                JSONObject parentObj = new JSONObject(finalJSON);
                JSONArray parentArray = parentObj.getJSONArray("server_response");



                List<ProductModel> productModelList = new ArrayList<>();

                for(int i =0;i<parentArray.length();i++)
                {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    ProductModel productModel = new ProductModel();

                    productModel.setName(finalObject.getString("name"));
                    productModel.setUser_name(finalObject.getString("user_name"));
                    productModel.setUser_pass(finalObject.getString("user_pass"));
                    productModel.setImg_loc(finalObject.getString("img_loc"));


                    // adding the final object in the list
                    productModelList.add(productModel);

                }



                return productModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {

                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }


                try {
                    if (reader != null) {
                        reader.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<ProductModel> result) {


            super.onPostExecute(result);

            final ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(), R.layout.row, result);
            lvProduct.setAdapter(productAdapter);
            lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    ProductModel item = (ProductModel) productAdapter.getItem(position);



                    Intent i = new Intent(getApplicationContext(),ProductPage.class);
                    i.putExtra("name",item.getName());
                    i.putExtra("user_name",item.getUser_name());
                    i.putExtra("user_pass",item.getUser_pass());
                    i.putExtra("img_loc",item.getImg_loc());

                    startActivity(i);


                    //  Toast.makeText(getApplicationContext(),"Clicked "+position,Toast.LENGTH_LONG).show();


                }
            });

        }
    }


    public class ProductAdapter extends ArrayAdapter {

        public String u_name,u_user_name,u_user_pass,u_img_loc;

        private List<ProductModel> productList;
        private int resource;
        private LayoutInflater inflater;

        public ProductAdapter(Context context, int resource, List<ProductModel> objects) {
            super(context, resource, objects);

            productList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        public Object getItem(int position)
        {
            return productList.get(position);
        }

        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null)
            {
                convertView = inflater.inflate(resource,null);
            }

            ImageView im;

            TextView namey,user_name,user_pass,img_loc;

            //RatingBar rBar;


            im = (ImageView)convertView.findViewById(R.id.imageView);
            namey = (TextView)convertView.findViewById(R.id.name);
            user_name = (TextView)convertView.findViewById(R.id.user_name);
            user_pass = (TextView)convertView.findViewById(R.id.user_pass);
            img_loc = (TextView)convertView.findViewById(R.id.img_loc);


            // Then later, when you want to display image
            ImageLoader.getInstance().displayImage(productList.get(position).getImg_loc(), im); // Default options will be used

            u_name = productList.get(position).getName();
            u_user_name = productList.get(position).getUser_name();
            u_user_pass = productList.get(position).getUser_pass();
            u_img_loc = productList.get(position).getImg_loc();

            namey.setText(u_name);
            user_name.setText(u_user_name);
            user_pass.setText(u_user_pass);
            img_loc.setText(u_img_loc);


            return convertView;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {

            new JSONTask().execute("http://192.168.159.1/dt/json_data.php");

            return true;
        }

        else if (id == R.id.login) {

            Intent i = new Intent(getApplicationContext(),Add_Product.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}