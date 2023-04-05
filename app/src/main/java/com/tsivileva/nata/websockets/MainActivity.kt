package com.tsivileva.nata.websockets

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tsivileva.nata.websockets.databinding.ActivityMainBinding
import com.tsivileva.nata.websockets.viewPager.MainViewPagerFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = supportFragmentManager.fragments.any { it.tag == MainViewPagerFragment::class.java.name }

        if (!fragment) {
            supportFragmentManager.beginTransaction().add(
                binding.container.id,
                MainViewPagerFragment(),
                MainViewPagerFragment::class.java.name
            ).commit()
        }
    }
}