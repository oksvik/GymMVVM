package com.dudar.gymmvvm.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.view.View;

import com.dudar.gymmvvm.model.Equipment;
import com.dudar.gymmvvm.model.EquipmentResponse;
import com.dudar.gymmvvm.model.Exercise;
import com.dudar.gymmvvm.model.ExerciseCategory;
import com.dudar.gymmvvm.model.ExerciseCategoryResponse;
import com.dudar.gymmvvm.model.ExerciseResponse;
import com.dudar.gymmvvm.model.ExerciseWithImage;
import com.dudar.gymmvvm.model.Muscle;
import com.dudar.gymmvvm.model.MuscleResponse;
import com.dudar.gymmvvm.network.ApiService;
import com.dudar.gymmvvm.network.GymApi;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class GymRepository {
    private String TAG = GymRepository.class.getSimpleName();

    private static int VIEW_VISIBLE = View.VISIBLE;
    private static int VIEW_INVISIBLE = View.GONE;
    private GymApi gymApi;// = ApiService.getInstance().getGymApi();
    private CompositeDisposable disposable = new CompositeDisposable();


    public  MutableLiveData<Integer> isLoading;
    public  MutableLiveData<Integer> isLoaded;

//    public MutableLiveData<List<ExerciseCategory>> categories;
//    public MutableLiveData<List<Equipment>> equipment;
//    public MutableLiveData<List<Muscle>> muscles;

    public  MutableLiveData<Integer> getIsLoading() {
        if (isLoading == null)
        {
            isLoading = new MutableLiveData<>();
            isLoading.setValue(VIEW_INVISIBLE);
        }
        return isLoading;
    }

    public  MutableLiveData<Integer> getIsLoaded() {
        if (isLoaded == null)
        {
            isLoaded = new MutableLiveData<>();
            isLoaded.setValue(VIEW_INVISIBLE);
        }
        return isLoaded;
    }

    public GymRepository() {
        isLoading = new MutableLiveData<>();
        isLoading.setValue(VIEW_INVISIBLE);

        isLoaded = new MutableLiveData<>();
        isLoaded.setValue(VIEW_INVISIBLE);

        gymApi = ApiService.getInstance().getGymApi();
    }

    public LiveData<List<Exercise>> getApprovedExercises(int page) {
        Log.d(TAG,"loading exercises");
        isLoading.setValue(VIEW_VISIBLE);
        isLoaded.setValue(VIEW_INVISIBLE);
        final MutableLiveData<List<Exercise>> exesListLiveData = new MutableLiveData<>() ;
        disposable.add(
                gymApi.getApprovedExercises(page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<ExerciseResponse>() {
                            @Override
                            public void onSuccess(ExerciseResponse exerciseResponse) {
                                //getApprovedExercisesFull(exerciseResponse, GymConst.LOAD_TYPE_DEFAULT);
                                exesListLiveData.postValue(exerciseResponse.getExercisesList());
                                isLoading.postValue(VIEW_INVISIBLE);
                                //isLoaded.setValue(true);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "onError: " + e.getMessage());
                                isLoading.setValue(VIEW_INVISIBLE);
                            }
                        })
        );
        return exesListLiveData;
    }

    /*
    private void getApprovedExercisesFull(ExerciseResponse exerciseResponse, int loadType) {

        Observable<Exercise> exerciseObservable = Observable.fromIterable(exerciseResponse.getExercisesList());


        disposable.add(exerciseObservable
                .flatMap(exercise -> gymApi.getExerciseImages(exercise.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(imgResp -> new ExerciseWithImage(exercise, imgResp.getImageList())))
                .toList()
                .subscribeWith(new DisposableSingleObserver<List<ExerciseWithImage>>() {
                    @Override
                    public void onSuccess(List<ExerciseWithImage> exerciseWithImages) {
                        if (loadType == GymConst.LOAD_TYPE_DEFAULT) {
                            exListView.displayExercisesList(exerciseResponse.getNextPage(), exerciseWithImages);
                        } else if (loadType == GymConst.LOAD_TYPE_MORE) {
                            exListView.displayMoreExerciseList(exerciseResponse.getNextPage(), exerciseWithImages);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }
                })
        );
    }
     */

    public LiveData<List<ExerciseWithImage>> getApprovedExercisesFull(List<Exercise> exercises) {
        Log.d(TAG,"loading full exercises");
        final MutableLiveData<List<ExerciseWithImage>> exesWithImg = new MutableLiveData<>();

        isLoading.setValue(VIEW_VISIBLE);
        //isLoaded.setValue(false);
        Observable<Exercise> exerciseObservable = Observable.fromIterable(exercises);

        disposable.add(exerciseObservable
                .flatMap(exercise -> gymApi.getExerciseImages(exercise.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(imgResp -> new ExerciseWithImage(exercise, imgResp.getImageList())))
                .toList()
                .subscribeWith(new DisposableSingleObserver<List<ExerciseWithImage>>() {
                    @Override
                    public void onSuccess(List<ExerciseWithImage> exerciseWithImages) {
                        /*if (loadType == GymConst.LOAD_TYPE_DEFAULT) {
                            exListView.displayExercisesList(exerciseResponse.getNextPage(), exerciseWithImages);
                        } else if (loadType == GymConst.LOAD_TYPE_MORE) {
                            exListView.displayMoreExerciseList(exerciseResponse.getNextPage(), exerciseWithImages);
                        }*/
                        exesWithImg.postValue(exerciseWithImages);
                        isLoading.postValue(VIEW_INVISIBLE);
                        isLoaded.postValue(VIEW_VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                        isLoading.setValue(VIEW_INVISIBLE);
                    }
                })
        );
        return  exesWithImg;
    }


    public LiveData<List<ExerciseCategory>> getCategories() {
        MutableLiveData<List<ExerciseCategory>> categories = new MutableLiveData<>();
        disposable.add(
                gymApi.getAllCategories()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<ExerciseCategoryResponse>() {
                            @Override
                            public void onSuccess(ExerciseCategoryResponse exerciseCategoryResponse) {
                                categories.postValue(exerciseCategoryResponse.getCategoriesList());
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "onError: " + e.getMessage());
                            }
                        })
        );
        return categories;
    }

    public LiveData<List<Muscle>> getMuscles() {
        MutableLiveData<List<Muscle>> muscles = new MutableLiveData<>();
        disposable.add(
                gymApi.getAllMuscles()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<MuscleResponse>() {
                            @Override
                            public void onSuccess(MuscleResponse muscleResponse) {
                                muscles.postValue(muscleResponse.getMuscleList());
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "onError: " + e.getMessage());
                            }
                        })
        );
        return muscles;
    }

    public LiveData<List<Equipment>> getEquipment() {
        MutableLiveData<List<Equipment>> equipment = new MutableLiveData<>();
        disposable.add(
                gymApi.getAllEquipment()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<EquipmentResponse>() {
                            @Override
                            public void onSuccess(EquipmentResponse equipmentResponse) {
                                equipment.postValue(equipmentResponse.getEquipmentList());
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "onError: " + e.getMessage());
                            }
                        })
        );
        return equipment;
    }
}
