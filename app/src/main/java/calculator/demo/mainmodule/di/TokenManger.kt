package calculator.demo.mainmodule.di

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import java.util.*

data class TokenManger (
    var context: Context,
    var firstName:String? =null,
    var lastName :String?=null,
    var mfToken: String?=null,

    var secretToken:String?= null,

    var authToken: String? = null,

    // var encodedAuthToken: String? = null,

    var guardKey: String? = null,

    var pushDeviceToken: String? = null,

    //After login api added for digital gold
    var userToken: String? = null,
    //After login api added for digital gold
    var userGuardKey: String? = null,

    //Login token recieved form SSO api for Emi Store
    var emiStoreLoginToken: String? = null,

    var firstLogin:Boolean = false,
    var deliveryAddress: String? = null,

    val notifyLoginStatus: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
    var mfCustomerUserId:String?=null,
    var isMutualFundSdkInitialized:Boolean?=false,// for MutualFundHandler
    val navigateToHomeLob: MutableLiveData<Bundle> = MutableLiveData(),
    var timeStamp:Long=System.currentTimeMillis(),
    var nativeJourneySessionToken:String? = null

)
{
    enum class State {
        /*
        When API requires no auth token, either system or user
         */
        STATE_NEW,
        /*
        When user token needs to be saved
         */
        STATE_SET_USER_TOKEN,
        /**
         * When system token needs to be saved
         */
        STATE_SET_SYSTEM_TOKEN,
        /**
         * When API token is already passed in the request
         */
        STATE_TOKEN_AVAILABLE,

        /**
         * When refresh token is available to set user auth token in header
         */
        STATE_RFR_TOKEN_AVAILABLE,
        /**
         * When refresh token is available to set system auth token in header
         */
        STATE_SYS_TOKEN_AVAILABLE,
        /**
         * When user tokens are available in API call, in this case System tokens are must be set in the request headers
         */
        STATE_USER_TOKEN_RECEIVE,
        /**
         * When system tokens are available in API call, in this case no tokens are  set in the request headers
         */
        STATE_SYSTEM_TOKEN_RECEIVE,
        /**
         * When system tokens and user token both are available in API call, in this case check if user is loggedin set user token,
         * if not set system tokentokens are  set in the request headers
         */
        STATE_SET_ANY_TOKEN

    }

    private fun convertGuardKeyToToken(guardKey: String?): String {
        val curTime = Calendar.getInstance().getTimeInMillis()
        val salt = "B!&1j"
        val guardToken = salt + "|" + curTime + "|" + guardKey
        return guardToken;
    }


    fun getEncodedGuardToken(): String {
        return getEncodedToken(convertGuardKeyToToken(guardKey))
    }

    fun getEncodedUserGuardKey(): String{
        return getEncodedToken(userGuardKey)
    }

    fun getEncodedUserToken(): String{
        return getEncodedToken(userToken)
    }

    fun getEncodedUserGuardToken(): String {
        return getEncodedToken(convertGuardKeyToToken(userGuardKey))
    }

    fun getGuardToken(): String {
        return convertGuardKeyToToken(guardKey)
    }

    fun getEncodedRefreshedGuardToken(guardKey: String): String {
        return getEncodedToken(convertGuardKeyToToken(guardKey))

    }

    private fun getEncodedToken(token: String?): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(token?.toByteArray())
            //   return android.util.Base64.encodeToString(token.toByteArray(),android.util.Base64.NO_WRAP)
        } else {
            //TODO: below 21 api need to do needful
            return android.util.Base64.encodeToString(token?.toByteArray(),android.util.Base64.NO_WRAP)
        }
    }
}
