package com.ref.listtypes.utils


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ref.listtypes.expandable.models.StateCapital
import com.ref.listtypes.infinite.model.InfiniteModel
import com.ref.myreferenceproject.ReferenceApplication
import retrofit2.Response
import java.lang.reflect.Type


object  JSONHelper {



    fun fetchParsedJSONInfoForStateCapitals() : StateCapital{
        val jsonfile: String = ReferenceApplication.instance.applicationContext.assets.open("countrystateinfo.json").bufferedReader().use {it.readText()}
        println("Parsed JSON file is.... $jsonfile")

        val gson = Gson()
        val stateCapitalModelType: Type = object : TypeToken<StateCapital>() {}.type
        var stateCapital = gson.fromJson<StateCapital>(jsonfile,stateCapitalModelType)
        println("Output in String ${stateCapital.toString()}")
        return  stateCapital

    }
    fun fetchParsedJSONInfoForInfiniteList() : Response<InfiniteModel> {
        val jsonfile: String = ReferenceApplication.instance.applicationContext.assets.open("infinitelist.json").bufferedReader().use {it.readText()}
        println("Parsed JSON file is.... $jsonfile")

        val gson = Gson()
        val infiniteListModelType: Type = object : TypeToken<InfiniteModel>() {}.type
        var infiniteModel = gson.fromJson<InfiniteModel>(jsonfile,infiniteListModelType)
        println("Output in String ${infiniteModel.toString()}")
        return Response.success(infiniteModel)
    }



}