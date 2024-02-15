package com.irede.eatburguer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.irede.eatburguer.databinding.ActivityMainBinding
import com.irede.eatburguer.databinding.ActivitySheetBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonGotoSheet.setOnClickListener { showBottomSheetDialog() }
    }

    private fun showBottomSheetDialog(){
        val dialog = BottomSheetDialog(this)

        val sheetBinding: ActivitySheetBinding = ActivitySheetBinding.inflate(layoutInflater, null, false)

        sheetBinding.buttonGotoMain.setOnClickListener{
            val tableNumberText = sheetBinding.editTableNumber.text.toString()
            validateNumber(tableNumberText)
        }

        dialog.setContentView(sheetBinding.root)
        dialog.show()

    }

    private fun goToMainPage(){
        val navigateMainPage = Intent(this, MainPageActivity::class.java)
        startActivity(navigateMainPage)
    }

    private fun validateNumber(tableNumberText: String){
        if (tableNumberText.isEmpty()) {
            Toast.makeText(this, "Por favor, insira um número entre 1 e 10.", Toast.LENGTH_SHORT).show()
        } else {
            try {
                val tableNumber = tableNumberText.toInt()
                if (tableNumber < 1 || tableNumber > 10) {
                    Toast.makeText(this, "Número da mesa inválido!", Toast.LENGTH_SHORT).show()
                } else {
                    goToMainPage()
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Número inválido!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}