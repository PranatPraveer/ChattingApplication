package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var edtEmail: EditText
    private lateinit var mAuth: FirebaseAuth
    private lateinit var edtName: EditText
    private lateinit var edtPassword: EditText
    private lateinit var BtnSignUp: Button
    private lateinit var BtnLoginScreen: TextView
    private lateinit var mDbRef:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        edtEmail=findViewById(R.id.edt_email)
        edtPassword=findViewById(R.id.edt_password)
        BtnSignUp=findViewById(R.id.btnSignUp)
        BtnLoginScreen=findViewById(R.id.BtnLoginScreen)
        mAuth= FirebaseAuth.getInstance()
        edtName=findViewById(R.id.edt_name)
        BtnLoginScreen.setOnClickListener{
            val intent= Intent(this,Login::class.java)
            startActivity(intent)
            finish()
        }
        BtnSignUp.setOnClickListener {
            val email=edtEmail.text.toString()
            val name=edtName.text.toString()
            val password=edtPassword.text.toString()

            signUp(name,email,password)
        }
    }

    private fun signUp(name:String,email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent=Intent(this@SignUp,MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignUp, "Some Error Occured", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun addUserToDatabase(name: String, email: String, uid: String?) {

        mDbRef=FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid!!).setValue(User(name, email, uid))
    }
}