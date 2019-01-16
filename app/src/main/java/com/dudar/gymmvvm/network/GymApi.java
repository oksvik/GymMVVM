package com.dudar.gymmvvm.network;

import com.dudar.gymmvvm.model.EquipmentResponse;
import com.dudar.gymmvvm.model.ExerciseCategoryResponse;
import com.dudar.gymmvvm.model.ExerciseInfo;
import com.dudar.gymmvvm.model.ExerciseResponse;
import com.dudar.gymmvvm.model.ImageResponse;
import com.dudar.gymmvvm.model.MuscleResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GymApi {

    @GET("exercise")
    Single<ExerciseResponse> getAllExercises();

    @GET("exercise?status=2")
    Single<ExerciseResponse> getApprovedExercises(@Query("page") int page);

    @GET("exercise?status=2")
    Single<ExerciseResponse> getApprovedExercises();

    @GET("exerciseinfo/{id}")
    Single<ExerciseInfo> getExerciseInfo(@Path("id") int exerciseId);

    @GET("exerciseimage")
    Observable<ImageResponse> getExerciseImages(@Query("exercise") int exerciseId);

    @GET("exerciseimage")
    Single<ImageResponse> getExerciseDetailImages(@Query("exercise") int exerciseId);

    @GET("exercisecategory")
    Single<ExerciseCategoryResponse> getAllCategories();

    @GET("muscle")
    Single<MuscleResponse> getAllMuscles();

    @GET("equipment")
    Single<EquipmentResponse> getAllEquipment();

}
