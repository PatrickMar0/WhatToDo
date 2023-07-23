package com.example.whattodo



import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.whattodo.databinding.FragmentEventBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.fragment_login.*
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.whattodo.data.DataSource
import com.example.whattodo.databinding.FragmentLoginBinding


class LoginActivity : Fragment() {

    companion object {
        private const val RC_SIGN_IN = 120
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var btn: SignInButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // configure google sign in
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("965196178303-ot0q6jhf7oi798s6m3ng63dbc0avpc1s.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        //init firebase auth
        auth = FirebaseAuth.getInstance()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentLoginBinding.inflate(inflater, container, false).root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn = requireView().findViewById(com.example.whattodo.R.id.sign_in_btn);
        btn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //result returned from launching the intent from googlesigninapi.getsigninintent
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    // google sign in was successful, authenticate with firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)

                } catch (e: ApiException) {
                    Log.w("SignInActivity", "Google sign in failed", e)
                }
            } else {
                Log.w("SignInActivity", exception.toString())
            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d("SignInAcitvity", "signInWithCredential:success")
                    createUser()
                    val fragNavController = findNavController(this)
                    fragNavController.navigate(com.example.whattodo.R.id.action_global_content)
                } else {
                    Log.w("SignInActivity", "signInWithCredential:failure", task.exception)

                }
            }
    }

    fun createUser() {
        //this method will add the user to firestore, so we can store certain things like, name, and favorites
        var user = auth.currentUser
        // add user if not in database, otherwise, no issues
        DataSource().checkUser(user)
    }

}
