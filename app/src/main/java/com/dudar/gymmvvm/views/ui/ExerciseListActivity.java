package com.dudar.gymmvvm.views.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dudar.gymmvvm.R;
import com.dudar.gymmvvm.databinding.ActivityExerciseListBinding;
import com.dudar.gymmvvm.model.ExerciseWithImage;
import com.dudar.gymmvvm.viewmodels.ExerciseListViewModel;
import com.dudar.gymmvvm.views.adapter.ExerciseAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExerciseListActivity extends AppCompatActivity {

    private ExerciseListViewModel viewModel;
    ActivityExerciseListBinding binding;
    ExerciseAdapter adapter;
    RecyclerView recyclerView;
    List<ExerciseWithImage> allExes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_exercise_list);
        //setContentView(R.layout.activity_exercise_list);

        //ExerciseListViewModel viewModel = ViewModelProviders.of(this).get(ExerciseListViewModel.class);
        ExerciseListViewModel viewModel = new ExerciseListViewModel();
        viewModel.initData();

        binding.setExListViewModel(viewModel);
        binding.setLifecycleOwner(this);

        recyclerView = findViewById(R.id.recyclerViewExercises);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new ExerciseAdapter(allExes,recyclerView);
        recyclerView.setAdapter(adapter);

        viewModel.categories.observe(this, cList -> adapter.setCategories(cList));
        viewModel.muscleList.observe(this, mList-> adapter.setMuscles(mList));
        viewModel.equipment.observe(this, eList -> adapter.setEquipment(eList));

        /*viewModel.isLoaded.observe(this,
                isLoaded -> viewModel.exesWithImages.observe(this, exList -> {
                    if (!allExes.isEmpty())
                        allExes.clear();
                    allExes.addAll(exList);
                    adapter.notifyDataSetChanged();
                })
        );*/

        viewModel.exesWithImages.observe(this, exList -> {
            if (!allExes.isEmpty())
                allExes.clear();
            allExes.addAll(exList);
            adapter.notifyDataSetChanged();
        });

    }


    /*@BindingAdapter({"viewVisibility"})
    public static void setVisibility (View view, Boolean visibility){
        view.setVisibility(visibility? View.VISIBLE: View.GONE);
    }*/
}
