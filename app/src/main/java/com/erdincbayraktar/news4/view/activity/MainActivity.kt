package com.erdincbayraktar.news4.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.erdincbayraktar.news4.R
import com.erdincbayraktar.news4.databinding.ActivityMainBinding
import com.erdincbayraktar.news4.view.factory.NewsFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject lateinit var newsFragmentFactory: NewsFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = newsFragmentFactory
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavBar.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.feedFragment,
                R.id.searchFragment,
                R.id.bookmarkFragment
            )
        )
        setupActionBarWithNavController(navController,appBarConfiguration)
    }

    // ?
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}