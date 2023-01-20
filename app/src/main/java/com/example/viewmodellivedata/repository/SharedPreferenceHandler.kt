package com.example.viewmodellivedata.repository

import android.content.Context
import android.content.SharedPreferences.Editor
import android.util.Log
import com.example.viewmodellivedata.model.Article
import com.example.viewmodellivedata.utils.Constants.Companion.SHARED_PREFERENCE_NAME
import com.example.viewmodellivedata.utils.Constants.Companion.USER_NAME
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferenceHandler(context: Context) {

    private val sharedPreference =
        context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    private val editor: Editor = sharedPreference.edit()

    fun setSaveItme(item: Article) {
        val getUserList = getSavedList()
        val userModel = getUserList?.find { item.title==it.title }
        if (userModel==null){
            item.let { getUserList?.add(it) }
            Log.e("savedList",getUserList.toString())
            val list = Gson().toJson(getUserList)
            editor.putString(USER_NAME, list)?.apply()
        }
    }
    fun setRemoveItem(item : Article) {
        val getUserList = getSavedList()
        val userModel = getUserList?.find { it.title == item.title }
        if (userModel!=null){
            item.let { getUserList.remove(it) }
            Log.e("remove",getUserList.toString())
            val list = Gson().toJson(getUserList)
            editor.putString(USER_NAME, list)?.apply()
        }
    }
    fun getSavedList(): MutableList<Article>? {

        var savedList:List<Article>?= arrayListOf()
        val user = sharedPreference.getString(USER_NAME, null)
        if(user?.isNotEmpty()==true) {
            val type = object: TypeToken<ArrayList<Article>>(){}.type
            savedList = Gson().fromJson<ArrayList<Article>>(user,type)

        }
        return savedList?.toMutableList()


//        val savedlist =  sharedPreference.getString(USER_NAME, null)
//        val type = object : TypeToken<List<Article>>() {}.type
//        allSavedList = Gson().fromJson<MutableList<Article>>(savedlist,type)
//        return allSavedList
    }
}