package navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.faseapp.faseapp.R;

import java.util.ArrayList;
import java.util.List;

import model.CardModel;
import model.RecyclerAdapter;


/**
 * Created by shashank on 10/22/2016.
 */

public class CardActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private List<CardModel> cardList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        cardList=new ArrayList<>();
        cardList=initializeList();
        adapter=new RecyclerAdapter(this,cardList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(adapter);
    }

    private List<CardModel> initializeList() {
        List<CardModel> cardModel=new ArrayList<>();
        for(int i=1;i<=6;i++)
        {
            CardModel cardM=new CardModel("Fake-Name","1500","21-10-2016 18:23:21");
            cardModel.add(cardM);
        }
        return  cardModel;
    }


}