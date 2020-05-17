package com.victor.dogbreeds.ui.home

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.victor.dogbreeds.R
import com.victor.dogbreeds.business.models.BreedsModel
import com.victor.dogbreeds.business.models.UserModel
import com.victor.dogbreeds.ui.base.BaseActivity
import com.victor.dogbreeds.ui.breedDetails.BreedDetailsActivity
import com.victor.dogbreeds.ui.editProfile.EditProfileActivity
import com.victor.dogbreeds.util.getConnectionType
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HomeActivity : BaseActivity(),
    HomeContract.View,
    ItemBreed.ItemBreedCallback {
    override val layoutResource: Int = R.layout.activity_home
    private val presenter: HomeContract.Presenter by inject { parametersOf(this) }

    private lateinit var userModel: UserModel

    companion object {
        fun newInstance(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun start() {
        presenter.start(getConnectionType(this))
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    override fun setEvents() {
        homeProfileButton.setOnClickListener {
            startActivity(EditProfileActivity.newInstance(this, userModel))
        }
    }

    override fun setBreeds(breeds: List<BreedsModel>) {
        homeBreedsList.apply {

            val breedsAdapter = BreedsAdapter(breeds, this@HomeActivity)

            adapter = breedsAdapter
            layoutManager = LinearLayoutManager(context)

            homeBreedsSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    breedsAdapter.filter.filter(newText)
                    return false
                }
            })
        }
    }

    override fun openBreedDetails(breed: BreedsModel) {
        startActivity(BreedDetailsActivity.newInstance(this, breed))
    }

    override fun favoriteBreed(breed: BreedsModel) {
        presenter.favoriteBreed(userModel.id, breed)
    }

    override fun showErrorToastMessage() {
        Toast.makeText(
            this,
            getString(R.string.home_errorRetrievingInfo),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun setUserModel(userModel: UserModel) {
        this.userModel = userModel
    }

    override fun startShimmer() {
        homeShimmer.visibility = View.VISIBLE
    }

    override fun stopShimmer() {
        homeShimmer.visibility = View.GONE
    }
}