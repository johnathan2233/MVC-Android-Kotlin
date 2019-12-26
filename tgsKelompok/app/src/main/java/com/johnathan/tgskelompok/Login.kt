package com.johnathan.tgskelompok

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.jakewharton.rxbinding2.widget.RxTextView
import com.johnathan.tgskelompok.MVC.MenuActivity
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.login.*
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import java.util.HashMap
import java.util.concurrent.TimeUnit

class Login : AppCompatActivity() {
    internal lateinit var pDialog: ProgressDialog
    internal lateinit var btn_login: Button
    internal lateinit var txt_nim: EditText
    internal lateinit var txt_nama: EditText
    internal lateinit var txt_password: EditText
    internal lateinit var intent: Intent
    internal lateinit var txt_regis: TextView
    internal var success: Int = 0
    internal lateinit var conMgr: ConnectivityManager
    private val url = Server.URL + "login.php"
    internal var tag_json_obj = "json_obj_req"
    internal lateinit var sharedpreferences: SharedPreferences
    internal var session: Boolean? = false
    internal var id: String? = null
    internal var nim: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)



        btn_login= findViewById(R.id.btn_login)
        val animation2=AnimationUtils.loadAnimation(this,R.anim.bounce)
        btn_login.startAnimation(animation2)

//REACTIVE
        txt_nim = findViewById(R.id.txt_nim)
        txt_password = findViewById(R.id.txt_password)
        btn_login = findViewById(R.id.btn_login)


        RxTextView.afterTextChangeEvents(txt_nim)
            .skipInitialValue()
            .map {
                nimError.error = null
                it.view().toString()
              }
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .compose(validateEmailAddress)
            .compose(retryWhenError {
                passwordError.error = it.message
            })
            .subscribe()

        RxTextView.afterTextChangeEvents(txt_password)
            .skipInitialValue()
            .map {
                passwordError.error = null
                it.view().toString()
            }
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .compose(validatePassword)
            .compose(retryWhenError {
                nimError.error = it.message
            })
            .subscribe()

        //Reactive
        RxTextView.afterTextChangeEvents(txt_nim)
            .skipInitialValue()
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Toast.makeText(this, "Anda telah mengetik Email 500 miliseconds yang lalu", Toast.LENGTH_SHORT).show()
            }

        RxTextView.afterTextChangeEvents(txt_password)
            .skipInitialValue()
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Toast.makeText(this, "Anda telah mengetik Password 500 miliseconds yang lalu", Toast.LENGTH_SHORT).show()
            }





        conMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        run {
            if (conMgr.activeNetworkInfo != null
                && conMgr.activeNetworkInfo.isAvailable
                && conMgr.activeNetworkInfo.isConnected) {
            } else {
                Toast.makeText(applicationContext, "No Internet Connection",
                    Toast.LENGTH_LONG).show()
            }
        }

        btn_login = findViewById<View>(R.id.btn_login) as Button
        txt_nim = findViewById<View>(R.id.txt_nim) as EditText
        txt_password = findViewById<View>(R.id.txt_password) as EditText
        txt_regis = findViewById<View>(R.id.txt_regis) as TextView


        // Cek session login jika TRUE maka langsung buka MainActivity
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE)
        session = sharedpreferences.getBoolean(session_status, false)
        id = sharedpreferences.getString(TAG_ID, null)
        nim = sharedpreferences.getString(TAG_NIM, null)

//        if (session!!) {
//            val intent = Intent(this@Login, MainActivity::class.java)
//            intent.putExtra(TAG_ID, id)
//            intent.putExtra(TAG_NIM, nim)
//            finish()
//            startActivity(intent)
//        }


        btn_login.setOnClickListener {
            // TODO Auto-generated method stub
            val nim = txt_nim.text.toString()
            val password = txt_password.text.toString()

            // mengecek kolom yang kosong
            if (nim.trim { it <= ' ' }.length > 0 && password.trim { it <= ' ' }.length > 0) {
                if (conMgr.activeNetworkInfo != null
                    && conMgr.activeNetworkInfo.isAvailable
                    && conMgr.activeNetworkInfo.isConnected) {
                    checkLogin(nim, password)
                } else {
                    Toast.makeText(applicationContext, "No Internet Connection", Toast.LENGTH_LONG).show()
                }
            } else {
                // Prompt user to enter credentials
                Toast.makeText(applicationContext, "Kolom tidak boleh kosong", Toast.LENGTH_LONG).show()
            }
        }

        txt_regis.setOnClickListener {
            // TODO Auto-generated method stub
            intent = Intent(this@Login, Register::class.java)
            finish()
            startActivity(intent)
        }

    }

    private fun checkLogin(nim: String, password: String) {
        pDialog = ProgressDialog(this)
        pDialog.setCancelable(false)
        pDialog.setMessage("Logging in ...")
        showDialog()

        val strReq = object : StringRequest(Request.Method.POST, url, Response.Listener { response ->
            Log.e(TAG, "Login Response: $response")
            hideDialog()

            try {
                val jObj = JSONObject(response)
                success = jObj.getInt(TAG_SUCCESS)

                // Check for error node in json
                if (success == 1) {
                    val nim = jObj.getString(TAG_NIM)
                    val id = jObj.getString(TAG_ID)

                    Log.e("Successfully Login!", jObj.toString())

                    Toast.makeText(applicationContext, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show()

                    // menyimpan login ke session
                    val editor = sharedpreferences.edit()
                    editor.putBoolean(session_status, true)
                    editor.putString(TAG_ID, id)
                    editor.putString(TAG_NIM, nim)
                    editor.commit()

                    // Memanggil main activity
                    val intent = Intent(this@Login, MenuActivity::class.java)
                    intent.putExtra(TAG_ID, id)
                    intent.putExtra(TAG_NIM, nim)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext,
                        jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show()

                }
            } catch (e: JSONException) {
                // JSON error
                e.printStackTrace()
            }
        }, Response.ErrorListener { error ->
            Log.e(TAG, "Login Error: " + error.message)
            Toast.makeText(applicationContext,
                error.message, Toast.LENGTH_LONG).show()

            hideDialog()
        }) {

            override fun getParams(): Map<String, String> {
                // Posting parameters to login url
                val params = HashMap<String, String>()
                params["nim"] = nim
                params["password"] = password

                return params
            }

        }

        // Adding request to request queue
        AppController.instance?.addToRequestQueue(strReq, tag_json_obj)
    }

    private fun showDialog() {
        if (!pDialog.isShowing)
            pDialog.show()
    }

    private fun hideDialog() {
        if (pDialog.isShowing)
            pDialog.dismiss()
    }

    companion object {

        private val TAG = Login::class.java!!.getSimpleName()

        private val TAG_SUCCESS = "success"
        private val TAG_MESSAGE = "message"

        val TAG_NIM = "nim"
        val TAG_ID = "id"
        val my_shared_preferences = "my_shared_preferences"
        val session_status = "session_status"
    }


    private inline fun retryWhenError(crossinline onError :
            (ex: Throwable)-> Unit) : ObservableTransformer<String, String>
            = ObservableTransformer{

            observable -> observable.retryWhen {
            errors -> errors.flatMap {
        onError(it)
        Observable.just("")
    }
    }
    }

    private val validateEmailAddress = ObservableTransformer<String, String> {
            observable -> observable.flatMap {
        Observable.just(it).map { it.trim() }
            //memasukkan operator untuk membenarkan email address
            .filter {
                Patterns.EMAIL_ADDRESS.matcher(it).matches()
            }
            .singleOrError()
            .onErrorResumeNext {
                if(it is NoSuchElementException){
                    Single.error(Exception("masukkin password yang benar"))
                }else{
                    Single.error(it)
                }
            }
            .toObservable()
    }
    }

    private val validatePassword = ObservableTransformer<String, String> {
            observable -> observable.flatMap {
        Observable.just(it).map { it.trim() }
            //memasukkan operator untuk membenarkan email address
            .filter {
                it.length > 8
            }
            .singleOrError()
            .onErrorResumeNext {
                if(it is NoSuchElementException){
                    Single.error(Exception("Masukan password yg benar"))
                }else{
                    Single.error(it)
                }
            }
            .toObservable()
    }
    }


}