package com.irede.eatburguer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.irede.eatburguer.databinding.ActivitySheetBinding

class sheetActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySheetBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}