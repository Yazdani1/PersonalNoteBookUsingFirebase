package yazdaniscodelab.personalnotebook;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import yazdaniscodelab.personalnotebook.Model.Categories;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;

//    Firebase

    private FirebaseAuth mAuth;
    private DatabaseReference mCatDatabase;

//    Floating Button

    private FloatingActionButton flotPlus;

//    Recycler view..

    private RecyclerView mRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar=findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Personal NoteBook");

        actionbutton();


//        Firebase
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser mid=mAuth.getCurrentUser();
        String uid=mid.getUid();
        mCatDatabase= FirebaseDatabase.getInstance().getReference().child("Categories").child(uid);
        mCatDatabase.keepSynced(true);

//        Recycler view..
        mRecycler=findViewById(R.id.catagory_data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(layoutManager);




//        Floating Button


    }



    public void actionbutton(){

        flotPlus=findViewById(R.id.fab_plus_xml);

        flotPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCategorydata();
            }
        });


    }



//    Retrive data from Firebase database

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Categories,ViewHolder>recyclerAdapter=new FirebaseRecyclerAdapter<Categories, ViewHolder>
                (
                        Categories.class,
                        R.layout.itemdata,
                        HomeActivity.ViewHolder.class,
                        mCatDatabase
                ) {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, Categories model, final int position) {

                final String post_key=getRef(position).getKey();

                viewHolder.setText(model.getCategoriesName());
                viewHolder.mDate(model.getmDate());

                viewHolder.myview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent=new Intent(getApplicationContext(),DetailsActivity.class);
                        intent.putExtra("key",post_key);
                        startActivity(intent);

                    }
                });




            }
        };

        mRecycler.setAdapter(recyclerAdapter);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        View myview;

        public ViewHolder(View itemView) {
            super(itemView);
            myview=itemView;
        }

        //Categories name Methods...

        public void setText(String catName){
            TextView nameOCt=myview.findViewById(R.id.cat_text_xml);
            nameOCt.setText(catName);
        }

        // Categories Date Methods..

        public void mDate(String date){
            TextView mydate=myview.findViewById(R.id.date_xml);
            mydate.setText(date);
        }
    }





//Add category data method;

    public void addCategorydata(){

//        Custom Alert...

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater inflater=LayoutInflater.from(this);
        View myview=inflater.inflate(R.layout.custom_dialog_forcategory,null);
        builder.setView(myview);
        final AlertDialog dialog=builder.create();

        final EditText catName=myview.findViewById(R.id.categoried_id_xml);
        Button btn=myview.findViewById(R.id.add_btn_xml);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nameCat=catName.getText().toString().trim();
                String mNoteDate= DateFormat.getDateInstance().format(new Date());

                if (TextUtils.isEmpty(nameCat)){
                    catName.setError("Required Field..");
                    return;
                }

                String id=mCatDatabase.push().getKey();
                Categories categories=new Categories(id,nameCat,mNoteDate);
                mCatDatabase.child(id).setValue(categories);
                Toast.makeText(getApplicationContext(),"Categories Added",Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });


        dialog.show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:
                mAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}
