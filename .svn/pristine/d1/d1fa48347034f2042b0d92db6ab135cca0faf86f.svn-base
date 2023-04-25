package com.sunmnet.mediaroom.brand.api.service;

import com.sunmnet.mediaroom.brand.bean.response.ExamDetailResponse;
import com.sunmnet.mediaroom.brand.bean.response.QueryExamRoomResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InformationService {

    /**
     * 获取考试时间表的考试数据，包括考生数据
     */
    @GET("information-svr/api/examDetailList")
    Observable<ExamDetailResponse> getExamDetailList(@Query("examId") String examId, @Query("classroomCode") String classroomCode);

    /**
     * 查找考场信息
     * @param registrationNumber 准考证号
     * @param idNumber 证件号
     * @return
     */
    @GET("information-svr/api/findUserExam")
    Observable<QueryExamRoomResponse> findUserExamInfo(@Query("registrationNumber") String registrationNumber, @Query("idNumber") String idNumber);
}
