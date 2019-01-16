package com.dudar.gymmvvm.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.dudar.gymmvvm.model.Equipment;
import com.dudar.gymmvvm.model.Exercise;
import com.dudar.gymmvvm.model.ExerciseCategory;
import com.dudar.gymmvvm.model.ExerciseWithImage;
import com.dudar.gymmvvm.model.Muscle;
import com.dudar.gymmvvm.network.GymApi;
import com.dudar.gymmvvm.repository.GymRepository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class ExerciseListViewModel extends ViewModel {
    private GymApi gymApi;
    private CompositeDisposable disposable = new CompositeDisposable();
    private GymRepository repository;

    public ExerciseListViewModel(){
        Log.d("ExerciseListViewModel","IN MODEL CONSTRUCTOR");
        //page=new MutableLiveData<>();
        //page.setValue(1);

        repository = new GymRepository();

        isLoading = repository.isLoading;
        isLoaded = repository.isLoaded;
    }

    public LiveData<List<Muscle>> muscleList;
    public LiveData<List<ExerciseCategory>> categories;
    public LiveData<List<Equipment>> equipment;

    /*public LiveData<List<Muscle>> getMuscles (){
        if (muscleList == null){
            muscleList = new MutableLiveData<>();

        }
        return muscleList;
    }*/

    private MutableLiveData<Integer> page = new MutableLiveData<>();
    private LiveData<List<Exercise>> exesLiveData = Transformations.switchMap(page, page-> repository.getApprovedExercises(page));

    public LiveData<List<ExerciseWithImage>> exesWithImages = Transformations.switchMap(exesLiveData,exesLiveData->repository.getApprovedExercisesFull(exesLiveData));

    public LiveData<Integer> isLoading;// = repository.isLoading;
    public LiveData<Integer> isLoaded;// = repository.isLoaded;




    public void loadExercises(int newPage){
        page.setValue(newPage);
    }

    public LiveData<Integer> getLoadingStatus(){
        return repository.isLoading;
    }

    public void initData() {
        page.setValue(1);
        Log.d("INSIDE INIT DATA", page.getValue().toString());

        muscleList = repository.getMuscles();
        categories = repository.getCategories();
        equipment = repository.getEquipment();
        //Log.d("INSIDE INIT DATA", exesWithImages.getValue().toString());
    }
}
