package com.ref.listtypes.utils


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ref.listtypes.expandable.models.StateCapital
import com.ref.myreferenceproject.ReferenceApplication
import java.lang.reflect.Type


object  JSONHelper {



    fun fetchParsedJSONInfoForStateCapitals() : StateCapital{
        val jsonfile: String = ReferenceApplication.instance.applicationContext.assets.open("countrystateinfo.json").bufferedReader().use {it.readText()}
        println("Parsed JSON file is.... $jsonfile")

        val gson = Gson()
        val stateCapitalModelType: Type = object : TypeToken<StateCapital>() {}.type
        var stateCapital = gson.fromJson<StateCapital>(jsonfile,stateCapitalModelType)
        println("OpenShelfSequenceModel in String ${stateCapital.toString()}")
        return  stateCapital

    }




}