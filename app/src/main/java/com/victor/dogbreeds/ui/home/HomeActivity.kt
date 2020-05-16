package com.victor.dogbreeds.ui.home

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.victor.dogbreeds.R
import com.victor.dogbreeds.business.FirestoreRefs
import com.victor.dogbreeds.business.models.BreedsModel
import com.victor.dogbreeds.business.models.UserModel
import com.victor.dogbreeds.ui.base.BaseActivity
import com.victor.dogbreeds.ui.breedDetails.BreedDetailsActivity
import com.victor.dogbreeds.ui.editProfile.EditProfileActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HomeActivity : BaseActivity(),
    HomeContract.View,
    ItemBreed.ItemBreedCallback {
    override val layoutResource: Int = R.layout.activity_home
    private val presenter: HomeContract.Presenter by inject { parametersOf(this) }
    private val users = FirebaseFirestore.getInstance().collection(FirestoreRefs.usersCollection)

    private lateinit var auth: FirebaseAuth
    private lateinit var userModel: UserModel

    companion object {
        fun newInstance(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun start() {
        auth = FirebaseAuth.getInstance()
        getPlayerId()
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
            adapter = BreedsAdapter(breeds, this@HomeActivity)
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun openBreedDetails(breed: BreedsModel) {
        startActivity(BreedDetailsActivity.newInstance(this, breed))
    }

    override fun favoriteBreed(breed: BreedsModel) {
        breed.isFavorite = !breed.isFavorite

        val dataToSave = hashMapOf<String, Any>()
        dataToSave[FirestoreRefs.breed] = breed

        users
            .document(userModel.id)
            .collection(FirestoreRefs.favoritesCollection)
            .add(dataToSave)
            .addOnSuccessListener {
                Toast.makeText(this, getString(R.string.home_addedFavorites), Toast.LENGTH_SHORT)
                    .show()
            }
            .addOnFailureListener {
                breed.isFavorite = !breed.isFavorite
                Toast.makeText(this, getString(R.string.home_errorAddFavorites), Toast.LENGTH_SHORT)
                    .show()
            }
    }

    private fun getPlayerId() {
        auth.currentUser?.let { user ->
            user.email?.let { email ->
                users.whereEqualTo(FirestoreRefs.userEmail, email)

                users.get()
                    .addOnSuccessListener { query ->
                        if (query.isEmpty) {
                            showErrorToastMessage()
                        } else {
                            if (query.documents.size > 1) showErrorToastMessage()

                            userModel = UserModel(
                                id = query.documents[0].id,
                                fullname = query.documents[0][FirestoreRefs.userFullname].toString(),
                                birthdate = query.documents[0][FirestoreRefs.userBirthdate].toString(),
                                email = query.documents[0][FirestoreRefs.userEmail].toString()
                            )

                            presenter.getAllBreeds()
                        }
                    }.addOnFailureListener {
                        showErrorToastMessage()
                    }
            }
        }
    }

    private fun showErrorToastMessage() {
        Toast.makeText(
            this,
            getString(R.string.home_errorRetrievingInfo),
            Toast.LENGTH_SHORT
        ).show()
    }
}