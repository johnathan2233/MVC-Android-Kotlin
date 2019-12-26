package com.johnathan.tgskelompok

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.login.passwordError
import kotlinx.android.synthetic.main.register.*
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import java.util.concurrent.TimeUnit


class Register : AppCompatActivity() {
    internal lateinit var pDialog: ProgressDialog
    internal lateinit var btn_register: Button
    internal var btn_login: Button? = null
    internal lateinit var txt_nama: EditText
    internal lateinit var txt_nim: EditText
    internal lateinit var txt_password: EditText
    internal lateinit var txt_confirm_password: EditText
    internal lateinit var intent: Intent
    internal lateinit var txt_kembali: TextView

    internal var success: Int = 0
    internal lateinit var conMgr: ConnectivityManager

    private val url = Server.URL + "register.php"

    internal var tag_json_obj = "json_obj_req"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        txt_nim = findViewById(R.id.txt_nim)
        txt_nama = findViewById(R.id.txt_nama)
        txt_password = findViewById(R.id.txt_password)
        txt_confirm_password = findViewById(R.id.txt_confirm_password)


      //  agar menampilkan berapa lam sudah mengetik
        RxTextView.afterTextChangeEvents(txt_nama)
            .skipInitialValue()
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Toast.makeText(this, "Anda telah mengetik Nama 500 miliseconds yang lalu", Toast.LENGTH_SHORT).show()
            }

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

        RxTextView.afterTextChangeEvents(txt_confirm_password)
            .skipInitialValue()
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Toast.makeText(this, "Anda telah mengetik Confirm Password 500 miliseconds yang lalu", Toast.LENGTH_SHORT).show()
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

        btn_register = findViewById<View>(R.id.btn_register) as Button
        txt_nama = findViewById<View>(R.id.txt_nama) as EditText
        txt_nim = findViewById<View>(R.id.txt_nim) as EditText
        txt_password = findViewById<View>(R.id.txt_password) as EditText
        txt_confirm_password = findViewById<View>(R.id.txt_confirm_password) as EditText
        txt_kembali = findViewById<View>(R.id.txt_kembali) as TextView

        txt_kembali.setOnClickListener {
            // TODO Auto-generated method stub
            intent = Intent(this@Register, Login::class.java)
            finish()
            startActivity(intent)
        }

        btn_register.setOnClickListener {
            // TODO Auto-generated method stub
            val nama = txt_nama.text.toString()
            val nim = txt_nim.text.toString()
            val password = txt_password.text.toString()
            val confirm_password = txt_confirm_password.text.toString()

            if (conMgr.activeNetworkInfo != null
                && conMgr.activeNetworkInfo.isAvailable
                && conMgr.activeNetworkInfo.isConnected) {
                checkRegister(nama, nim, password, confirm_password)
            } else {
                Toast.makeText(applicationContext, "No Internet Connection", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun checkRegister(nama: String, nim: String, password: String, confirm_password: String) {
        pDialog = ProgressDialog(this)
        pDialog.setCancelable(false)
        pDialog.setMessage("Register ...")
        showDialog()

        val strReq = object : StringRequest(Request.Method.POST, url, Response.Listener { response ->
            Log.e(TAG, "Register Response: $response")
            hideDialog()

            try {
                val jObj = JSONObject(response)
                success = jObj.getInt(TAG_SUCCESS)

                // Check for error node in json
                if (success == 1) {

                    Log.e("Successfully Register!", jObj.toString())

                    Toast.makeText(applicationContext,
                        jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show()

                    txt_nama.setText("")
                    txt_nim.setText("")
                    txt_password.setText("")
                    txt_confirm_password.setText("")

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
                params["nama"] = nama
                params["nim"] = nim
                params["password"] = password
                params["confirm_password"] = confirm_password

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

    override fun onBackPressed() {
        intent = Intent(this@Register, Login::class.java)
        finish()
        startActivity(intent)
    }

    companion object {

        private val TAG = Register::class.java!!.getSimpleName()

        private val TAG_SUCCESS = "success"
        private val TAG_MESSAGE = "message"
    }
}