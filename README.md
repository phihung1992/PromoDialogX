# PromoDialogX

Step 1. Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.phihung1992:PromoDialogX:1.1'
	}

Dependencies

	implementation 'androidx.appcompat:appcompat:1.1.0'
        implementation 'com.squareup.retrofit2:retrofit:2.3.0'
        implementation 'com.google.code.gson:gson:2.8.5'
        implementation 'com.squareup.retrofit2:converter-gson:2.1.0'
        implementation 'com.github.bumptech.glide:glide:4.9.0'
