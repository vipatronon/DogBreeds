package com.victor.dogbreeds.ui.home

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.widget.SearchView
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
import com.victor.dogbreeds.util.ConnectionType
import com.victor.dogbreeds.util.getConnectionType
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
    private lateinit var connectionType: ConnectionType

    companion object {
        fun newInstance(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun start() {
        connectionType = getConnectionType(this)
        auth = FirebaseAuth.getInstance()

        presenter.start(connectionType)
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

    private fun getPlayerId() {
        auth.currentUser?.let { user ->
            user.email?.let { email ->
                users.whereEqualTo(FirestoreRefs.userEmail, email)
                    .get()
                    .addOnSuccessListener { query ->
                        if (query.isEmpty) {
                            showErrorToastMessage()
                        } else {
                            if (query.documents.size > 1) {
                                showErrorToastMessage()
                                return@addOnSuccessListener
                            }

                            val firestoreModel =
                                query.documents[0].toObject(com.victor.dogbreeds.business.models.firestore.UserModel::class.java)!!

                            userModel = UserModel(
                                fullname = firestoreModel.fullname!!,
                                email = firestoreModel.email!!,
                                birthdate = firestoreModel.birthdate!!,
                                id = query.documents[0].id
                            )

                            presenter.getAllBreeds(userModel.id)
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