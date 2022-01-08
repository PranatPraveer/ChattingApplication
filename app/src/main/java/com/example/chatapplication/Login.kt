package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var mAuth:FirebaseAuth
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var BtnLogin: Button
    private lateinit var BtnSignUpScreen: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth= FirebaseAuth.getInstance()

        edtEmail=findViewById(R.id.edt_email)
        edtPassword=findViewById(R.id.edt_password)
        BtnLogin=findViewById(R.id.btnLogin)
        BtnSignUpScreen=findViewById(R.id.BtnSignUpScreen)
        if (mAuth.currentUser!= null){
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        BtnSignUpScreen.setOnClickListener{
            val intent= Intent(this, SignUp::class.java)
            startActivity(intent)
        }
        BtnLogin.setOnClickListener{
            val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()
            login(email,password)
        }

    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent=Intent(this@Login, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@Login,"Invalid Credentials", Toast.LENGTH_SHORT).show()
                }
            }

    }
}