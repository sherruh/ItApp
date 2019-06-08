package com.rentapp.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.rentapp.R;
import com.rentapp.utils.Logger;
import com.rentapp.utils.Toaster;

public class LoginActivity extends AppCompatActivity {

    private final int RC_GOOGLE_SIGN_IN = 999;
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    SignInButton signInButton;
    LoginButton loginButton;
    Button button;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        final GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(this);

        callbackManager = CallbackManager.Factory.create();

        facebookSignInInit();

        if (googleAccount == null) googleSingInInit(mGoogleSignInClient);
        else Logger.message("Signed in yet: " + googleAccount.getDisplayName());
        button = findViewById(R.id.button2);
        button.setVisibility(View.INVISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInButton.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.VISIBLE);
                //TODO
            }
        });
    }

    private void facebookSignInInit() {
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Logger.message("Signed in facebook ");
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Logger.message("Sign in facebook canceled");
            }

            @Override
            public void onError(FacebookException exception) {
                Logger.message("Sign in facebook failed " + exception.toString());
            }
        });
    }

    private void googleSingInInit(final GoogleSignInClient googleSignInClient) {
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(task);
        }

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Logger.message("Signed in " + account.getDisplayName());
            firebaseAuthWithGoogle(account);

        } catch (ApiException e) {
            Logger.message("Google sign in failed code=" + e.getStatusCode());
            Toaster.message(this,"Sign in failed!");
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Logger.message("handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Logger.message( "signInWithCredential:success");
                            ImageView imageView = findViewById(R.id.imageView);
                            Glide.with(LoginActivity.this)
                                    .load(task.getResult().getUser().getPhotoUrl())
                                    .into(imageView);
                            loginButton.setVisibility(View.INVISIBLE);
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            Logger.message( "signInWithCredential:failure" + task.getException());
                            Toaster.message(LoginActivity.this,"Authentiation failure");
                        }
                    }
                });
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Logger.message("firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Logger.message( "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            ImageView imageView = findViewById(R.id.imageView2);
                            Glide.with(LoginActivity.this).load(task.getResult().getUser().getPhotoUrl())
                                    .into(imageView);
                            signInButton.setVisibility(View.INVISIBLE);
                        } else {
                            Logger.message("signInWithCredential:failure " + task.getException());
                            Snackbar.make(findViewById(R.id.login_button), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
