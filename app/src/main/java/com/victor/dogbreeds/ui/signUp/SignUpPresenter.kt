package com.victor.dogbreeds.ui.signUp

import com.google.firebase.firestore.FirebaseFirestore
import com.victor.dogbreeds.business.FirestoreRefs
import com.victor.dogbreeds.util.Validators
import com.victor.dogbreeds.util.base.IValidators

class SignUpPresenter(
    private val view: SignUpContract.View
) : SignUpContract.Presenter,
    IValidators by Validators() {

    private var validFullname = false
    private var validEmail = false
    private var validPassword = false
    private var validBirthdate = false

    private val docRef = FirebaseFirestore.getInstance().collection(FirestoreRefs.usersCollection)

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

    override fun createUser(fullName: String, email: String, birthdate: String) {
        if (validFullname &&
            validEmail &&
            validPassword &&
            validBirthdate
        ) {
            val dataToSave = hashMapOf<String, Any>()

            dataToSave[FirestoreRefs.userFullname] = fullName
            dataToSave[FirestoreRefs.userEmail] = email
            dataToSave[FirestoreRefs.userBirthdate] = birthdate

            docRef.add(dataToSave).addOnSuccessListener {
                view.displaySignUpSuccessfullyToast()
                view.openHomeActivity()
            }.addOnFailureListener {
                view.displayCouldNotCreateAccountToast()
            }
        } else {
            view.displayFillFieldsToast()
        }
    }
}