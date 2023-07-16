package com.example.userregistration.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userregistration.db.entity.UserEntity

import com.example.userregistration.ultils.Constant
import com.example.userregistration.model.ValidationErrorModel
import com.example.userregistration.model.repository.UserRepository
import com.example.userregistration.model.repository.UserRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RegisterViewModel:ViewModel() {

    val firstName = MutableLiveData<String>()

    val lastName = MutableLiveData<String>()

    val gender = MutableLiveData<String>()

    val email = MutableLiveData<String>()

    val mobile = MutableLiveData<String>()

    val userName = MutableLiveData<String>()

    val password = MutableLiveData<String>()

    private val _userInsertResult = MutableLiveData<Long>()
    val userInsertResult:LiveData<Long> by lazy { _userInsertResult }

    private val _loginResult = MutableLiveData<UserEntity?>()
    val loginResult:LiveData<UserEntity?> by lazy { _loginResult }

    private val _validationError = MutableLiveData<ValidationErrorModel>()
    val validationError:LiveData<ValidationErrorModel> by lazy { _validationError }


    fun validateFirstName():Boolean{
        if((firstName.value?:"").isEmpty()){
            _validationError.postValue(ValidationErrorModel(Constant.FIRST_NAME, "FirstName field should not empty" ))
            return false
        }
        return true
    }

    fun validateLastName():Boolean{
        if((lastName.value?:"").isEmpty()){
            _validationError.postValue(ValidationErrorModel(Constant.LAST_NAME, "Last name field should not empty" ))
            return false
        }
        return true
    }

    fun validateGender():Boolean{
        if((gender.value?:"").isEmpty()){
            _validationError.postValue(ValidationErrorModel(Constant.GENDER, "Gender field should not empty" ))
            return false
        }else if(!gender.value?.lowercase().equals("male") && !gender.value?.lowercase().equals("female") ){
            Log.d("------",""+gender)
            _validationError.postValue(ValidationErrorModel(Constant.GENDER, "Enter valid gender" ))
            return false
        }

        return true
    }

    fun validateEmail():Boolean{
        val emailPattern = Regex(Constant.EMAIL_REGEX)
        if (!emailPattern.matches(email.value?:"")){
            _validationError.postValue(ValidationErrorModel(Constant.EMAIL, "Enter valid email" ))
            return false
        }
        return true
    }

    fun validateMobile():Boolean{
        if (mobile.value?.length?:0 <10){
            _validationError.postValue(ValidationErrorModel(Constant.MOBILE, "Enter valid mobile" ))
            return false
        }
        return true
    }

    fun validateUserName():Boolean{
        if((userName.value?:"").isEmpty()){
            _validationError.postValue(ValidationErrorModel(Constant.USERNAME, "Enter valid username" ))
            return false
        }
        return true
    }

    fun validatePassword():Boolean{
        if((password.value?:"").isEmpty()){
            _validationError.postValue(ValidationErrorModel(Constant.PASSWORD, "Password should not empty" ))
            return false
        }else if(password.value?.length?:0 <8){
            _validationError.postValue(ValidationErrorModel(Constant.PASSWORD, "Password length should be at least 8" ))
            return false
        }
        return true
    }

    fun finalValidation():Boolean{

       return ( validateFirstName() &&
                validateLastName() &&
                validateGender() &&
                validateEmail() &&
                validateMobile() &&
                validateUserName() &&
                validatePassword())

    }

    fun registerUser(context: Context){
        viewModelScope.launch(Dispatchers.IO){
            val result = UserRepositoryImpl(context).insertUser(
                UserEntity(
                    0,
                    firstName = firstName.value!!,
                    lastName = lastName.value!!,
                    gender = gender.value!!,
                    email = email.value!!,
                    mobile = mobile.value!!,
                    userName = userName.value!!,
                    password = password.value!!
                )
            )

            _userInsertResult.postValue(result)
        }
    }

    fun loginUser(context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            val result =
                async {
                    UserRepositoryImpl(context).userLogin(
                        userName = userName.value?:"",
                        password = password.value?:""
                    )
                }
            _loginResult.postValue(result.await())

        }

    }
}