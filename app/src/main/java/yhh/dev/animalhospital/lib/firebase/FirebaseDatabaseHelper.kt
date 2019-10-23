package yhh.dev.animalhospital.lib.firebase

import com.google.firebase.database.FirebaseDatabase

class FirebaseDatabaseHelper {

    private val database = FirebaseDatabase.getInstance()

    val mainReference = database.getReference("main")

    val commentsReference = mainReference.child("comments")
}