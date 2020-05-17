package com.victor.dogbreeds.ui.editProfile

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.victor.dogbreeds.business.FirestoreRefs
import com.victor.dogbreeds.business.models.UserModel
import com.victor.dogbreeds.util.Validators
import com.victor.dogbreeds.util.base.IValidators

class EditProfilePresenter(
    private val view: EditProfileContract.View
) : EditProfileContract.Presenter,
    IValidators by Validators() {

    private var validFullname = false
    private var validEmail = false
    private var validPassword = true
    private var validBirthdate = false
    private val users = FirebaseFirestore.getInstance().collection(FirestoreRefs.usersCollection)

    private lateinit var auth: FirebaseAuth
    private lateinit var userModel: UserModel
    private lateinit var docRef: DocumentReference

    override fun start(userModel: UserModel) {
        auth = FirebaseAuth.getInstance()
        this.userModel = userModel
        docRef = users.document(userModel.id)
    }

    override fun onFinishEditFullname(textToValidate: String) {
        validFullname = validateTextLength(textToValidate, 5)

        if (validFullname) {
            view.showFullnameNormalInput()
        } else {
            view.showFullnameInputError()
        }
    }

    override fun onFinishEditEmail(textToValidate: String) {
        validEmail = validateTextEmail(textToValidate)

        if (validEmail) {
            view.showEmailNormalInput()
        } else {
            view.showEmailInputError()
        }
    }

    override fun onFinishEditPassword(textToValidate: String) {
        if (textToValidate.isEmpty()) return
        validPassword = validateTextLength(textToValidate, 5)

        if (validPassword) {
            view.showPasswordNormalInput()
        } else {
            view.showPasswordInputError()
        }
    }

    override fun onFinishEditBirthdate(textToValidate: String) {
        validBirthdate = validateTextBirthDate(textToValidate)

        if (validBirthdate) {
            view.showBirthdateNormalInput()
        } else {
            view.showBirthdateInputError()
        }
    }

    override fun updateUser(fullName: String, email: String, password: String, birthdate: String) {

        onFinishEditFullname(fullName)
        onFinishEditPassword(password)
        onFinishEditEmail(email)
        onFinishEditBirthdate(birthdate)

        if (!validEmail || !validPassword || !validBirthdate || !validFullname){
            view.displayCorrectInfosToast()
            return
        }

        view.hideButton()
        view.showShimmer()

        updateFullname(fullName) {
            updatebirthdate(birthdate) {
                updatePassword(password) {
                    if (userModel.email != email) {
                        docRef.update(FirestoreRefs.userEmail, email)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    auth.currentUser?.updateEmail(email)
                                        ?.addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                view.requestLogout()
                                            }
                                        }
                                        ?.addOnFailureListener {
                                            view.requestLogoutToChangeUserName()
                                        }
                                }
                            }

                    } else {
                        view.displayUpdatedSuccessfullyToast()
                        view.openHomeActivity()
                    }
                }
            }
        }
    }

    private fun updatePassword(password: String, callback: () -> Unit) {
        if (password.isNotEmpty()) {
            auth.currentUser?.updatePassword(password)
                ?.addOnCompleteListener {
                    if (it.isSuccessful) callback()
                }
        } else {
            callback()
        }
    }

    private fun updateFullname(fullname: String, callback: () -> Unit) {
        if (userModel.fullname != fullname) {
            docRef.update(FirestoreRefs.userFullname, fullname)
                .addOnSuccessListener {
                    callback()
                }
        } else {
            callback()
        }
    }

    private fun updatebirthdate(birthdate: String, callback: () -> Unit) {
        if (userModel.birthdate != birthdate) {
            docRef.update(FirestoreRefs.userBirthdate, birthdate)
                .addOnSuccessListener {
                    callback()
                }
        } else {
            callback()
        }
    }
}